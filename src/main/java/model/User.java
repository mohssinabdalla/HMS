package model;

/**
 * Abstract superclass for all HMS users (Admin, Doctor, Patient, MedicalManager).
 * Shared identity fields + shared behavior live here.
 * Each role subclass MUST implement updateOwnProfile() (polymorphism).
 */
public abstract class User {

    protected String userId;
    protected String username;
    private String password;          // private: only this class + AuthService (via checkPassword) touch it
    protected String fullName;
    protected String email;
    protected String phoneNumber;
    protected String role;            // e.g. "Admin", "Doctor", "Patient", "MedicalManager"

    public User(String userId, String username, String password, String fullName,
                String email, String phoneNumber, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // ---- Getters ----
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRole() { return role; }
    public String getPassword() {       
        return password;
    }
    // ---- Shared behavior ----
    public String getProfile() {
        return "ID: " + userId + " | Name: " + fullName + " | Role: " + role
                + " | Email: " + email + " | Phone: " + phoneNumber;
    }

    /**
     * Used only by AuthService to verify login — password itself is never exposed directly.
     */
    public boolean checkPassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Every role edits their own profile differently (different fields matter to each role),
     * so each subclass MUST provide its own implementation. This is polymorphism in action.
     */
    public abstract boolean updateOwnProfile(String fullName, String email, String phoneNumber);

    /**
     * Every role has DIFFERENT extra columns beyond the 6 shared ones (Admin's
     * department, a future Doctor's specialty, etc.) — so instead of the file
     * loader hardcoding "column 7 means X for this role", each subclass parses
     * its OWN extra fields here. The loader just hands over whatever columns
     * are left after the shared ones, without needing to know what they mean.
     * Stub roles with no extra fields yet can leave this empty.
     */
    public abstract void loadExtraFields(String[] extraFields);

    /**
     * Serializes this user into one CSV line for storage in users.txt.
     * Subclasses that add extra fields should override this and call super.toFileString()
     * then append their own fields, keeping a consistent column order per role.
     */
    public String toFileString() {
    return String.join(",", userId, username, password, fullName, email, phoneNumber);
}
}
