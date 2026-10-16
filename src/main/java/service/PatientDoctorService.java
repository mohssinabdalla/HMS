package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientDoctorService {

private static final String DOCTORS_FILE = DataPaths.forRole("Doctor");

    /** Returns every doctor as a raw String[] of columns: userId,username,password,fullName,email,phoneNumber */
    public List<String[]> listAllDoctors() {
        List<String[]> doctors = new ArrayList<>();
        File file = new File(DOCTORS_FILE);
        if (!file.exists()) {
            return doctors;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    doctors.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    /** Filters listAllDoctors() by name, case-insensitive, partial match. */
    public List<String[]> searchDoctorsByName(String query) {
        List<String[]> matches = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (String[] doctor : listAllDoctors()) {
            if (doctor[3].toLowerCase().contains(lowerQuery)) {
                matches.add(doctor);
            }
        }
        return matches;
    }
    
    public boolean doctorExists(String doctorId) {
        for (String[] doctor : listAllDoctors()) {
            if (doctor[0].equals(doctorId)) {
                return true;
            }
        }
        return false;
    }
}