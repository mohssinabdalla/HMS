package model;

import exceptions.DataFileNotFoundException;
import exceptions.DuplicateUserException;
import exceptions.InvalidPhoneNumberException;
import exceptions.SelfActionRestrictionException;
import exceptions.UserNotFoundException;
import java.util.ArrayList;
import service.AuditLog;
import java.util.List;
import service.DataPaths;
import service.FileHelper;
import service.UserValidationHelper;
import exceptions.DuplicateRecordException;
import exceptions.InvalidCoverageException;
import exceptions.RecordNotFoundException;
import exceptions.InvalidAmountException;
import exceptions.InvalidCapacityException;
import exceptions.InvalidDateException;

public class Admin extends User {

    private String department;
    private AuditLog auditLog;
    public static final String ROLE_DOCTOR = "Doctor";
    public static final String ROLE_MANAGER = "MedicalManager";

    public Admin(String userId, String username, String password, String fullName,
            String email, String phoneNumber, String department) {
        super(userId, username, password, fullName, email, phoneNumber, "Admin");
        this.department = department;
        this.auditLog = new AuditLog();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Admin ' s only extra column is department (index 0 of whatever ' s left
     * after the 6 shared columns).
     */
    @Override
    public void loadExtraFields(String[] extraFields) {
        if (extraFields.length > 0) {
            this.department = extraFields[0];
        }
    }

    @Override
    public boolean updateOwnProfile(String fullName, String email, String phoneNumber) {
        try {
            service.UserValidationHelper.validatePhoneNumber(phoneNumber);

            String path = service.DataPaths.forRole(this.role);
            java.util.List<String> lines = service.FileHelper.readAllLines(path);

            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                String[] p = lines.get(i).split(",");
                if (p.length > 0 && p[0].equals(this.userId)) {
                    // Keep userId, username, password, department unchanged —
                    // only fullName, email, phone are this method's job.
                    String department = p.length > 6 ? p[6] : "";
                    lines.set(i, String.join(",", p[0], p[1], p[2], fullName, email, phoneNumber, department));
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
            if (!service.FileHelper.writeAllLines(path, lines)) {
                return false;
            }

            this.fullName = fullName;
            this.email = email;
            this.phoneNumber = phoneNumber;

            auditLog.logAction(this.userId, "UPDATE_OWN_PROFILE", this.userId, "Admin updated own profile");
            return true;

        } catch (exceptions.InvalidPhoneNumberException | exceptions.DataFileNotFoundException e) {
            return false;
        }
    }

    public void createUser(User newUser, String role)
            throws DuplicateUserException, InvalidPhoneNumberException, DataFileNotFoundException {
        service.UserValidationHelper.validatePhoneNumber(newUser.getPhoneNumber());
        String path = DataPaths.forRole(role);
        List<String> existing;
        try {
            existing = FileHelper.readAllLines(path);
        } catch (DataFileNotFoundException e) {
            existing = new ArrayList<>(); // no file yet, nothing to clash with
        }
        UserValidationHelper.validateUniqueUsername(path, newUser.getUsername(), "");

        if (!FileHelper.appendLine(path, newUser.toFileString())) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "CREATE_USER", newUser.getUserId(), "Created new " + role);
    }

    public void updateUser(User target, User requester) throws UserNotFoundException, SelfActionRestrictionException, InvalidPhoneNumberException, DuplicateUserException, DataFileNotFoundException {

        boolean isSelf = target.getUserId().equals(requester.getUserId());
        boolean requesterIsAdmin = requester instanceof Admin;

        if (!requesterIsAdmin && !isSelf) {
            throw new SelfActionRestrictionException("You can only edit your own profile.");
        }
        // Admin can edit ANY user, including other Admins — no restriction left here

        service.UserValidationHelper.validatePhoneNumber(target.getPhoneNumber());

        String path = DataPaths.forRole(target.getRole());
        service.UserValidationHelper.validateUniqueUsername(path, target.getUsername(), target.getUserId());

        List<String> lines = FileHelper.readAllLines(path);

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).split(",")[0].equals(target.getUserId())) {
                lines.set(i, target.toFileString());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new UserNotFoundException("No user with ID " + target.getUserId());
        }

        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(requester.getUserId(), "UPDATE_USER", target.getUserId(), "Profile updated");
    }

    public void deleteUser(String targetUserId, String role)
            throws UserNotFoundException, DataFileNotFoundException {

        String path = DataPaths.forRole(role);
        List<String> lines = FileHelper.readAllLines(path);

        boolean removed = lines.removeIf(l -> l.split(",")[0].equals(targetUserId));
        if (!removed) {
            throw new UserNotFoundException("No user with ID " + targetUserId);
        }

        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "DELETE_USER", targetUserId, "User deleted");
        if (role.equals(ROLE_DOCTOR) || role.equals(ROLE_MANAGER)) {
            endActiveAssignmentsForUser(targetUserId);
        }
    }

    private void endActiveAssignmentsForUser(String userId) {
        try {
            String path = DataPaths.forEntity("DoctorAssignment");
            List<String> lines = FileHelper.readAllLines(path);
            boolean changed = false;

            for (int i = 0; i < lines.size(); i++) {
                String[] p = lines.get(i).split(",");
                if (p.length >= 5
                        && (p[1].equals(userId) || p[2].equals(userId))
                        && DoctorAssignment.STATUS_ACTIVE.equals(p[4])) {
                    lines.set(i, String.join(",", p[0], p[1], p[2], p[3], DoctorAssignment.STATUS_ENDED));
                    changed = true;
                }
            }

            if (changed) {
                FileHelper.writeAllLines(path, lines);
                auditLog.logAction(this.userId, "END_ASSIGNMENT", userId,
                        "Assignment(s) auto-ended because " + userId + " was deleted");
            }
        } catch (DataFileNotFoundException e) {
            // No assignments file yet — nothing to end, and this must not block the deletion itself.
        }
    }

    // Doctor Assignment
    public void assignDoctorToManager(DoctorAssignment assignment)
            throws UserNotFoundException, DuplicateRecordException, DataFileNotFoundException {

        // both people must really exist
        requireUserExists(ROLE_DOCTOR, assignment.getDoctorId(), "doctor");
        requireUserExists(ROLE_MANAGER, assignment.getManagerId(), "medical manager");

        String path = DataPaths.forEntity("DoctorAssignment");
        List<String> lines;
        try {
            lines = FileHelper.readAllLines(path);
        } catch (DataFileNotFoundException e) {
            lines = new ArrayList<>(); // no file yet, nothing to clash with
        }

        boolean endedOld = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");
            if (p.length >= 5 && p[1].equals(assignment.getDoctorId())
                    && DoctorAssignment.STATUS_ACTIVE.equals(p[4])) {
                if (p[2].equals(assignment.getManagerId())) {
                    throw new DuplicateRecordException("Doctor " + assignment.getDoctorId()
                            + " is already assigned to manager " + assignment.getManagerId() + ".");
                }
                // doctor is moving to a new manager: keep the old record as history
                lines.set(i, String.join(",", p[0], p[1], p[2], p[3], DoctorAssignment.STATUS_ENDED));
                endedOld = true;
            }
        }
        lines.add(assignment.toFileString());

        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "ASSIGN_DOCTOR", assignment.getAssignmentId(),
                "Doctor " + assignment.getDoctorId() + " assigned to manager " + assignment.getManagerId()
                + (endedOld ? " (previous assignment ended)" : ""));
    }

    public List<DoctorAssignment> viewAllAssignments() throws DataFileNotFoundException {
        List<String> lines = FileHelper.readAllLines(DataPaths.forEntity("DoctorAssignment"));
        List<DoctorAssignment> assignments = new ArrayList<>();

        for (String line : lines) {
            try {
                assignments.add(DoctorAssignment.fromFileString(line));
            } catch (InvalidDateException | IllegalArgumentException e) {
                // skip malformed line instead of crashing the whole screen
            }
        }
        assignments.sort((a, b) -> a.getAssignmentId().compareTo(b.getAssignmentId()));
        return assignments;
    }

    public void endAssignment(String assignmentId) throws RecordNotFoundException, DataFileNotFoundException {
        String path = DataPaths.forEntity("DoctorAssignment");
        List<String> lines = FileHelper.readAllLines(path);

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");
            if (p.length >= 5 && p[0].equals(assignmentId)) {
                if (DoctorAssignment.STATUS_ENDED.equals(p[4])) {
                    throw new RecordNotFoundException("Assignment " + assignmentId + " is already ended.");
                }
                lines.set(i, String.join(",", p[0], p[1], p[2], p[3], DoctorAssignment.STATUS_ENDED));
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RecordNotFoundException("No assignment found with ID " + assignmentId);
        }
        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "END_ASSIGNMENT", assignmentId, "Assignment manually ended by admin");
    }

    // Lightweight list of {userId, fullName} for filling dropdowns
    public List<String[]> viewUserSummaries(String role) throws DataFileNotFoundException {
        List<String> lines = FileHelper.readAllLines(DataPaths.forRole(role));
        List<String[]> summaries = new ArrayList<>();

        for (String line : lines) {
            String[] p = line.split(",");
            if (p.length >= 4) {
                summaries.add(new String[]{p[0], p[3]}); // userId, fullName
            }
        }
        summaries.sort((a, b) -> a[0].compareTo(b[0]));
        return summaries;
    }

    private void requireUserExists(String role, String userId, String label)
            throws UserNotFoundException, DataFileNotFoundException {
        for (String line : FileHelper.readAllLines(DataPaths.forRole(role))) {
            if (line.split(",")[0].equals(userId)) {
                return;
            }
        }
        throw new UserNotFoundException("No " + label + " with ID " + userId);
    }

    public void addHospitalAsset(HospitalAsset asset)
            throws DuplicateRecordException, DataFileNotFoundException {

        String path = DataPaths.forEntity("HospitalAsset");
        List<String> lines;
        try {
            lines = FileHelper.readAllLines(path);
        } catch (DataFileNotFoundException e) {
            lines = new ArrayList<>(); // no file yet, nothing to clash with
        }

        checkDuplicateLocation(lines, asset.getLocation(), "");

        if (!FileHelper.appendLine(path, asset.toFileString())) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "ADD_ASSET", asset.getAssetId(),
                "Added " + asset.getAssetType() + " at " + asset.getLocation());
    }

    public void updateHospitalAsset(HospitalAsset asset)
            throws RecordNotFoundException, DuplicateRecordException, DataFileNotFoundException {

        String path = DataPaths.forEntity("HospitalAsset");
        List<String> lines = FileHelper.readAllLines(path);

        checkDuplicateLocation(lines, asset.getLocation(), asset.getAssetId());

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).split(",")[0].equals(asset.getAssetId())) {
                lines.set(i, asset.toFileString());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RecordNotFoundException("No asset with ID " + asset.getAssetId());
        }

        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "UPDATE_ASSET", asset.getAssetId(),
                "Updated " + asset.getAssetType() + " at " + asset.getLocation());
    }

    public void deleteHospitalAsset(String assetId)
            throws RecordNotFoundException, DataFileNotFoundException {

        String path = DataPaths.forEntity("HospitalAsset");
        List<String> lines = FileHelper.readAllLines(path);

        boolean removed = lines.removeIf(l -> l.split(",")[0].equals(assetId));
        if (!removed) {
            throw new RecordNotFoundException("No asset with ID " + assetId);
        }

        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "DELETE_ASSET", assetId, "Hospital asset deleted");
    }

    public List<HospitalAsset> viewAllHospitalAssets() throws DataFileNotFoundException {
        List<String> lines = FileHelper.readAllLines(DataPaths.forEntity("HospitalAsset"));
        List<HospitalAsset> assets = new ArrayList<>();

        for (String line : lines) {
            try {
                assets.add(HospitalAsset.fromFileString(line));
            } catch (InvalidCapacityException | IllegalArgumentException e) {
                // skip malformed line instead of crashing the whole screen
            }
        }
        assets.sort((a, b) -> a.getAssetId().compareTo(b.getAssetId()));
        return assets;
    }

    // One physical space = one asset; excludeId lets an update ignore its own record
    private void checkDuplicateLocation(List<String> lines, String location, String excludeId)
            throws DuplicateRecordException {
        for (String line : lines) {
            String[] p = line.split(",");
            if (p.length >= 3 && p[2].equalsIgnoreCase(location) && !p[0].equals(excludeId)) {
                throw new DuplicateRecordException("An asset at \"" + location + "\" already exists.");
            }
        }
    }

    public void addConsultationRate(ConsultationRate rate)
            throws DuplicateRecordException, DataFileNotFoundException {

        String path = DataPaths.forEntity("ConsultationRate");
        List<String> lines;
        try {
            lines = FileHelper.readAllLines(path);
        } catch (DataFileNotFoundException e) {
            lines = new ArrayList<>(); // no file yet, nothing to clash with
        }

        checkDuplicateRate(lines, rate.getDepartmentId(), rate.getDoctorLevel(), "");

        if (!FileHelper.appendLine(path, rate.toFileString())) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "ADD_RATE", rate.getRateId(),
                "Added rate for " + rate.getDepartmentId() + " / " + rate.getDoctorLevel());
    }

    public void updateConsultationRate(ConsultationRate rate)
            throws RecordNotFoundException, DuplicateRecordException, DataFileNotFoundException {

        String path = DataPaths.forEntity("ConsultationRate");
        List<String> lines = FileHelper.readAllLines(path);

        checkDuplicateRate(lines, rate.getDepartmentId(), rate.getDoctorLevel(), rate.getRateId());

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).split(",")[0].equals(rate.getRateId())) {
                lines.set(i, rate.toFileString());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RecordNotFoundException("No consultation rate with ID " + rate.getRateId());
        }

        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "UPDATE_RATE", rate.getRateId(),
                "Updated rate for " + rate.getDepartmentId() + " / " + rate.getDoctorLevel());
    }

    public void deleteConsultationRate(String rateId)
            throws RecordNotFoundException, DataFileNotFoundException {

        String path = DataPaths.forEntity("ConsultationRate");
        List<String> lines = FileHelper.readAllLines(path);

        boolean removed = lines.removeIf(l -> l.split(",")[0].equals(rateId));
        if (!removed) {
            throw new RecordNotFoundException("No consultation rate with ID " + rateId);
        }

        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "DELETE_RATE", rateId, "Consultation rate deleted");
    }

    public List<ConsultationRate> viewAllConsultationRates() throws DataFileNotFoundException {
        List<String> lines = FileHelper.readAllLines(DataPaths.forEntity("ConsultationRate"));
        List<ConsultationRate> rates = new ArrayList<>();

        for (String line : lines) {
            try {
                rates.add(ConsultationRate.fromFileString(line));
            } catch (InvalidAmountException | InvalidDateException | IllegalArgumentException e) {
                // skip malformed line instead of crashing the whole screen
            }
        }
        rates.sort((a, b) -> a.getRateId().compareTo(b.getRateId()));
        return rates;
    }

    // One rate per department + doctor level; excludeId lets an update ignore its own record
    private void checkDuplicateRate(List<String> lines, String departmentId, String doctorLevel, String excludeId)
            throws DuplicateRecordException {
        for (String line : lines) {
            String[] p = line.split(",");
            if (p.length >= 3
                    && p[1].equalsIgnoreCase(departmentId)
                    && p[2].equalsIgnoreCase(doctorLevel)
                    && !p[0].equals(excludeId)) {
                throw new DuplicateRecordException("A rate for " + departmentId + " / " + doctorLevel
                        + " already exists. Edit it instead.");
            }
        }
    }

    public void addInsuranceNetwork(InsuranceNetwork network)
            throws DuplicateRecordException, DataFileNotFoundException {

        String path = DataPaths.forEntity("InsuranceNetwork");
        List<String> lines;
        try {
            lines = FileHelper.readAllLines(path);
        } catch (DataFileNotFoundException e) {
            lines = new ArrayList<>(); // no file yet, nothing to clash with
        }

        checkDuplicateProvider(lines, network.getProviderName(), "");

        if (!FileHelper.appendLine(path, network.toFileString())) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "ADD_INSURANCE", network.getNetworkId(),
                "Added insurance network " + network.getProviderName());
    }

    public void updateInsuranceNetwork(InsuranceNetwork network)
            throws RecordNotFoundException, DuplicateRecordException, DataFileNotFoundException {

        String path = DataPaths.forEntity("InsuranceNetwork");
        List<String> lines = FileHelper.readAllLines(path);

        checkDuplicateProvider(lines, network.getProviderName(), network.getNetworkId());

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).split(",")[0].equals(network.getNetworkId())) {
                lines.set(i, network.toFileString());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RecordNotFoundException("No insurance network with ID " + network.getNetworkId());
        }

        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "UPDATE_INSURANCE", network.getNetworkId(),
                "Updated insurance network " + network.getProviderName());
    }

    public void deleteInsuranceNetwork(String networkId)
            throws RecordNotFoundException, DataFileNotFoundException {

        String path = DataPaths.forEntity("InsuranceNetwork");
        List<String> lines = FileHelper.readAllLines(path);

        boolean removed = lines.removeIf(l -> l.split(",")[0].equals(networkId));
        if (!removed) {
            throw new RecordNotFoundException("No insurance network with ID " + networkId);
        }

        if (!FileHelper.writeAllLines(path, lines)) {
            throw new DataFileNotFoundException("Could not save to " + path);
        }
        auditLog.logAction(this.userId, "DELETE_INSURANCE", networkId, "Insurance network deleted");
    }

    public List<InsuranceNetwork> viewAllInsuranceNetworks() throws DataFileNotFoundException {
        List<String> lines = FileHelper.readAllLines(DataPaths.forEntity("InsuranceNetwork"));
        List<InsuranceNetwork> networks = new ArrayList<>();

        for (String line : lines) {
            try {
                networks.add(InsuranceNetwork.fromFileString(line));
            } catch (InvalidCoverageException | IllegalArgumentException e) {
                // skip malformed line instead of crashing the whole screen
            }
        }
        networks.sort((a, b) -> a.getNetworkId().compareTo(b.getNetworkId()));
        return networks;
    }

    // Provider name must be unique; excludeId lets an update ignore its own record
    private void checkDuplicateProvider(List<String> lines, String providerName, String excludeId)
            throws DuplicateRecordException {
        for (String line : lines) {
            String[] p = line.split(",");
            if (p.length >= 2 && p[1].equalsIgnoreCase(providerName) && !p[0].equals(excludeId)) {
                throw new DuplicateRecordException("Provider \"" + providerName + "\" already exists.");
            }
        }
    }

    public List<String> viewActivityLog() {
        return auditLog.getLogsByUser(this.userId);
    }

    public List<AuditLogEntry> viewAllActivityLogs() throws DataFileNotFoundException {
        List<String> lines = FileHelper.readAllLines(DataPaths.forEntity("AuditLog"));
        List<AuditLogEntry> entries = new ArrayList<>();

        for (String line : lines) {
            try {
                entries.add(AuditLogEntry.fromFileString(line));
            } catch (IllegalArgumentException e) {
                // skip malformed line instead of crashing the whole screen
            }
        }

        // logId is "L" + System.currentTimeMillis(), so sorting descending by logId = newest first
        entries.sort((a, b) -> b.getLogId().compareTo(a.getLogId()));
        return entries;
    }

    public List<AuditLogEntry> searchActivityLogsByUser(String userId) throws DataFileNotFoundException {
        List<AuditLogEntry> results = new ArrayList<>();
        for (AuditLogEntry entry : viewAllActivityLogs()) {
            if (entry.getUserId().equals(userId)) {
                results.add(entry);
            }
        }
        return results;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "," + department;
    }

}
