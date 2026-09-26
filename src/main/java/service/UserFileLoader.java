package service;

import exception.DataFileNotFoundException;
import model.Admin;
import model.Doctor;
import model.MedicalManager;
import model.Patient;
import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads ONE role's file into a Map<String, User> (username -> object).
 * Now generic — works for any role, since each role has its own separate file
 * and the caller already knows which role it's loading (no "role" column needed).
 *
 * File format expected, one user per line:
 *   Admin:            userId,username,password,fullName,email,phoneNumber,department
 *   Doctor/Patient/MedicalManager (stub stage): userId,username,password,fullName,email,phoneNumber
 */
public class UserFileLoader {

    public static Map<String, User> loadUsersFromFile(String filePath, String role) throws DataFileNotFoundException {
        Map<String, User> directory = new HashMap<>();

        List<String> lines = FileHelper.readAllLines(filePath); // throws DataFileNotFoundException if missing

        for (String line : lines) {
            String[] p = line.split(",");
            if (p.length < 6) continue; // skip malformed lines

            String userId = p[0];
            String username = p[1];
            String password = p[2];
            String fullName = p[3];
            String email = p[4];
            String phoneNumber = p[5];

            User user;
            switch (role) {
                case "Admin":
                    String department = p.length > 6 ? p[6] : "";
                    user = new Admin(userId, username, password, fullName, email, phoneNumber, department);
                    break;
                case "Doctor":
                    user = new Doctor(userId, username, password, fullName, email, phoneNumber);
                    break;
                case "Patient":
                    user = new Patient(userId, username, password, fullName, email, phoneNumber);
                    break;
                case "MedicalManager":
                    user = new MedicalManager(userId, username, password, fullName, email, phoneNumber);
                    break;
                default:
                    continue; // unknown role, skip
            }

            directory.put(username, user);
        }

        return directory;
    }
}
