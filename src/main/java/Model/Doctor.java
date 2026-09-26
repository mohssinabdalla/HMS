package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Doctor extends User {

    private String doctorId;
    private String specialization;

    public Doctor(String userId, String username, String password,
                  String fullName, String email, String phoneNumber,
                  String doctorId, String specialization) {

        super(userId, username, password, fullName, email,
                phoneNumber, "Doctor");

        this.doctorId = doctorId;
        this.specialization = specialization;
    }

    // Getter
    public String getDoctorId() {
        return doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    // Setter
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Update doctor profile
    @Override
    public boolean updateOwnProfile(String fullName,
                                     String email,
                                     String phoneNumber) {

        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;

        // Temporary: only updates the object in memory
        return true;
    }

    // View doctor schedule
    public String viewSchedule() {

        return "TEMPORARY SCHEDULE\n\n"
                + "Monday    09:00 - 13:00   Ward A\n"
                + "Wednesday 14:00 - 18:00   Cardiology Clinic";
    }

    // Log patient vital signs
    public boolean logVitalSigns(
            String patientId,
            String bloodPressure,
            String heartRate,
            String temperature) {

        try {

            int hr = Integer.parseInt(heartRate);
            double temp = Double.parseDouble(temperature);

            // Validate heart rate
            if (hr < 30 || hr > 220) {
                return false;
            }

            // Validate temperature
            if (temp < 30.0 || temp > 43.0) {
                return false;
            }

            // Validate blood pressure
            if (bloodPressure == null ||
                    !bloodPressure.matches("\\d{2,3}/\\d{2,3}")) {
                return false;
            }

        } catch (NumberFormatException e) {

            return false;
        }

        appendTemp(
                "data/vitals.txt",
                String.join(",",
                        "V001",
                        patientId,
                        doctorId,
                        bloodPressure,
                        heartRate,
                        temperature)
        );

        return true;
    }

    // Write medical record
    public boolean writeMedicalRecords(
            String patientId,
            String diagnosis) {

        if (patientId == null || patientId.isBlank()) {
            return false;
        }

        if (diagnosis == null || diagnosis.isBlank()) {
            return false;
        }

        appendTemp(
                "data/medicalrecords.txt",
                String.join(",",
                        "MR001",
                        patientId,
                        doctorId,
                        diagnosis)
        );

        return true;
    }

    // Issue prescription
    public boolean issuePrescription(
            String patientId,
            String medicationList) {

        if (patientId == null || patientId.isBlank()) {
            return false;
        }

        if (medicationList == null || medicationList.isBlank()) {
            return false;
        }

        appendTemp(
                "data/prescriptions.txt",
                String.join(",",
                        "RX001",
                        patientId,
                        doctorId,
                        medicationList)
        );

        return true;
    }

    // Request laboratory test
    public boolean requestLabTest(
            String patientId,
            String testType) {

        if (patientId == null || patientId.isBlank()) {
            return false;
        }

        if (testType == null || testType.isBlank()) {
            return false;
        }

        appendTemp(
                "data/labrequests.txt",
                String.join(",",
                        "LR001",
                        patientId,
                        doctorId,
                        testType,
                        "Pending")
        );

        return true;
    }

    // Log consultation
    public boolean logConsultation(
            String patientId,
            String recordId) {

        if (patientId == null || patientId.isBlank()) {
            return false;
        }

        appendTemp(
                "data/consultations.txt",
                String.join(",",
                        "C001",
                        patientId,
                        doctorId,
                        recordId)
        );

        return true;
    }

    // Get medical records
    public String getMedicalRecordForPatient(
            String patientId) {

        return "TEMPORARY: Medical records for patient "
                + patientId
                + " will be loaded from the shared file later.";
    }

    // Update laboratory request status
    public boolean updateLabRequestStatus(
            String requestId,
            String newStatus) {

        System.out.println(
                "TEMPORARY: Lab request "
                + requestId
                + " changed to "
                + newStatus
        );

        return true;
    }

    // Temporary file writing method
    private void appendTemp(
            String filePath,
            String line) {

        try {

            Path path = Paths.get(filePath);

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            try (BufferedWriter writer =
                         new BufferedWriter(
                                 new FileWriter(filePath, true))) {

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Temporary file error: "
                    + e.getMessage()
            );
        }
    }
}