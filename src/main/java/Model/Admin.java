package model;

import service.AuditLog;
import java.util.List;

public class Admin extends User {

    private String department;
    private AuditLog auditLog;

    public Admin(String userId, String username, String password, String fullName,
                 String email, String phoneNumber, String department) {
        super(userId, username, password, fullName, email, phoneNumber, "Admin");
        this.department = department;
        this.auditLog = new AuditLog();
    }

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }

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
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        // TODO: persist change to users.txt
        auditLog.logAction(this.userId, "UPDATE_OWN_PROFILE", this.userId, "Admin updated own profile");
        return true;
    }

    public boolean createUser(User newUser) {
        // TODO: append newUser.toFileString() to users.txt
        auditLog.logAction(this.userId, "CREATE_USER", newUser.getUserId(), "Created new " + newUser.getRole());
        return true;
    }

    public boolean updateUser(User target, User requester) {
        boolean isSelf = target.getUserId().equals(requester.getUserId());
        boolean requesterIsAdmin = requester instanceof Admin;
        boolean targetIsAdmin = target instanceof Admin;

        if (!requesterIsAdmin && !isSelf) {
            return false; // non-admins can only edit themselves
        }
        if (requesterIsAdmin && targetIsAdmin && !isSelf) {
            return false; // admins cannot edit OTHER admins
        }

        // TODO: apply field updates + rewrite users.txt
        auditLog.logAction(requester.getUserId(), "UPDATE_USER", target.getUserId(), "Profile updated");
        return true;
    }

    public boolean deleteUser(String userId) {
        // TODO: remove matching line from users.txt
        auditLog.logAction(this.userId, "DELETE_USER", userId, "User deleted");
        return true;
    }

    public User viewUser(String userId) {
        // TODO: look up from in-memory directory / users.txt
        auditLog.logAction(this.userId, "VIEW_USER", userId, "Viewed user profile");
        return null; // placeholder until data-loading layer exists
    }

    public boolean assignDoctorToManager(String doctorId, String managerId) {
        // TODO: create/append a DoctorAssignment record to assignments.txt
        auditLog.logAction(this.userId, "ASSIGN_DOCTOR", doctorId, "Assigned to manager " + managerId);
        return true;
    }

    public boolean allocateAsset(String assetId) {
        // TODO: update matching row in assets.txt
        auditLog.logAction(this.userId, "ALLOCATE_ASSET", assetId, "Asset allocated/updated");
        return true;
    }

    public boolean configureConsultationRate(String rateId) {
        // TODO: update/add row in consultation_rates.txt
        auditLog.logAction(this.userId, "CONFIGURE_RATE", rateId, "Consultation rate configured");
        return true;
    }

    public boolean manageInsuranceNetwork(String networkId) {
        // TODO: update/add row in insurance_networks.txt
        auditLog.logAction(this.userId, "MANAGE_INSURANCE", networkId, "Insurance network updated");
        return true;
    }

    public List<String> viewActivityLog() {
        return auditLog.getLogsByUser(this.userId);
    }
    
    @Override
    public String toFileString() {
        return super.toFileString() + "," + department;
    }
    
}
