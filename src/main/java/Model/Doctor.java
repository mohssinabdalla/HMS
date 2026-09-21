package model;

/**
 * STUB — owned by the teammate handling the Doctor role.
 * Add Doctor-specific attributes (e.g. specialty, departmentId, licenseNumber)
 * and methods (logVitals, issuePrescription, requestLabTest, etc.) here.
 */
public class Doctor extends User {

    // TODO: add Doctor-specific fields

    public Doctor(String userId, String username, String password, String fullName,
                  String email, String phoneNumber) {
        super(userId, username, password, fullName, email, phoneNumber, "Doctor");
    }

    @Override
    public boolean updateOwnProfile(String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        // TODO: persist to users.txt
        return true;
    }

    // TODO: Doctor-specific methods go here
}
