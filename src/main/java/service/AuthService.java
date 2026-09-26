package service;

import model.User;
import java.util.Map;

/**
 * Handles login/logout for ALL roles. Kept separate from User itself
 * (single-responsibility: User = "who I am", AuthService = "how I get in").
 *
 * UPDATED: userDirectory Map of Maps — outer key is the role
 * ("Admin", "Doctor", ...), inner map is username -> User, matching the
 * one-file-per-role structure. Since the login screen's drop down already
 * tells us the role before checking credentials, login() now takes role
 * as a parameter and only searches within that one role's data — a Doctor's
 * username and a Patient's username never need to be compared against
 * each other at all. because if we have all users in the same file we need to add more verification
 * // layer if there to users with two different roles but with the same user name
 */
public class AuthService {

    private Map<String, Map<String, User>> userDirectory;

    public AuthService(Map<String, Map<String, User>> userDirectory) {
        this.userDirectory = userDirectory;
    }

    public User login(String username, String password, String role) {
        Map<String, User> roleMap = userDirectory.get(role);
        if (roleMap == null) {
            return null;} 

        User user = roleMap.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null; // caller (GUI) should show "invalid credentials"
    }
   }

