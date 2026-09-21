package model;

/**
 * STUB — owned by the teammate handling the Patient role.
 * Add Patient-specific attributes (e.g. dateOfBirth, insuranceId)
 * and methods (bookAppointment, viewPrescriptions, submitFeedback, etc.) here.
 */
public class Patient extends User {

    // TODO: add Patient-specific fields

    public Patient(String userId, String username, String password, String fullName,
                   String email, String phoneNumber) {
        super(userId, username, password, fullName, email, phoneNumber, "Patient");
    }

    @Override
    public boolean updateOwnProfile(String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        // TODO: persist to users.txt
        return true;
    }

    // TODO: Patient-specific methods go here
}
