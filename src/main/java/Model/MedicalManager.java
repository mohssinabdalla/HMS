package model;

/**
 * STUB — owned by the teammate handling the Medical Manager role.
 * Add MedicalManager-specific attributes and methods (createDepartment,
 * designShiftRoster, viewRevenueReports, etc.) here.
 */
public class MedicalManager extends User {

    // TODO: add MedicalManager-specific fields

    public MedicalManager(String userId, String username, String password, String fullName,
                           String email, String phoneNumber) {
        super(userId, username, password, fullName, email, phoneNumber, "MedicalManager");
    }

    @Override
    public boolean updateOwnProfile(String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        // TODO: persist to users.txt
        return true;
    }

    // TODO: MedicalManager-specific methods go here
}
