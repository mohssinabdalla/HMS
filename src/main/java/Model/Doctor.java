package model;

import service.FileHelper;
import exceptions.DataFileNotFoundException;
import exceptions.InvalidVitalSignException;
import exceptions.InvalidPrescriptionException;
import exceptions.LabRequestNotFoundException;
import exceptions.RecordNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {

    private String doctorId;
    private String specialization;

    public Doctor(String userId, String username, String password,
                  String fullName, String email, String phoneNumber) {

        super(userId, username, password, fullName, email,
                phoneNumber, "Doctor");

        this.doctorId = userId;
        this.specialization = "";
    }
    @Override
    public void loadExtraFields(String[] extraFields) {

        if (extraFields.length > 0) {
            specialization = extraFields[0];
        }
    }
    //getter

    public String getDoctorId() {
        return doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }
    //setter

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Update Profile
    
    

    @Override
    public boolean updateOwnProfile(String fullName,
                                    String email,
                                    String phoneNumber) {

        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;

        try {

            List<String> lines =
                    FileHelper.readAllLines("src/main/data/UsersData/doctors.txt");

            List<String> updated = new ArrayList<>();
            boolean found = false;

            for (String line : lines) {

                if (line.startsWith(userId + ",")) {
                    updated.add(toFileString());
                    found = true;
                } else {
                    updated.add(line);
                }
            }
            if (! found) {
                 System.out.println("Doctor not found: " + userId);
                return false;
                
            }

            return FileHelper.writeAllLines(
                    "src/main/data/UsersData/doctors.txt",
                    
                    updated
            );

        } catch (DataFileNotFoundException e) {

            System.out.println(
                    "Dooctors file not found: "
                    + e.getMessage()
            );

            return false;
        }
    }

    
    // View Schedule
    

    public String viewSchedule() {

        try {

            List<String> lines = FileHelper.readAllLines("src/main/data/rosters.txt");
                    

            StringBuilder result = new StringBuilder();
                    

            for (String line : lines) {
                String[] parts = line.split(",");
                
            if (parts.length >= 4 && parts[0].trim().equalsIgnoreCase(fullName)) {
                result.append("Date: ")
                        .append(parts[1].trim())
                        .append("\n");

                result.append("Timing: ")
                        .append(parts[2].trim())
                        .append("\n");

                result.append("Department: ")
                        .append(parts[3].trim())
                        .append("\n");

                result.append("-------------------------")
                        .append("\n");
                }
            }
            if (result.length() == 0) { return "No shift assigned.";}

            return result.toString();

        } catch (DataFileNotFoundException e) {

            return "No shift roaster found";
        }
    }

    
    // Log Vital Sign
    

    public boolean logVitalSigns(
            String patientId,
            String bloodPressure,
            String heartRate,
            String temperature)
            throws InvalidVitalSignException {

        int hr = //hr = hearRate
                parseIntOrThrow(
                        heartRate,
                        "heart rate"
                );

        double temp =
                parseDoubleOrThrow(
                        temperature,
                        "temperature"
                );

        if (hr < 30 || hr > 220) {

            throw new InvalidVitalSignException(
                    "Invalid Heart Rate: "
                    + heartRate
            );
        }

        if (temp < 30.0 || temp > 43.0) {

            throw new InvalidVitalSignException(
                    "Invalid Temperature: "
                    + temperature
            );
        }

        if (bloodPressure == null ||
                !bloodPressure.matches(
                        "\\d{2,3}/\\d{2,3}")) {
                

            throw new InvalidVitalSignException(
                    "Blood pressure must be like 120/80, got: "
                    + bloodPressure
            );
        }

        VitalSign vital = new VitalSign(
                nextId("src/main/data/vitals.txt", "v"),
                patientId,
                doctorId,
                bloodPressure,
                heartRate,
                temperature
        );

        return FileHelper.appendLine(
                "src/main/data/vitals.txt",
                vital.toFileLine()
        );
    }

    private int parseIntOrThrow(
            String value,
            String fieldName)
            throws InvalidVitalSignException {

        try {

            return Integer.parseInt(value);

        } catch (NumberFormatException e) {

            throw new InvalidVitalSignException(
                    "Invalid "
                    + fieldName
                    + ": "
                    + value
            );
        }
    }

    private double parseDoubleOrThrow(
            String value,
            String fieldName)
            throws InvalidVitalSignException {

        try {

            return Double.parseDouble(value);

        } catch (NumberFormatException e) {

            throw new InvalidVitalSignException(
                    "Invalid "
                    + fieldName
                    + ": "
                    + value
            );
        }
    }

    
    // Medical Record
    

    public boolean writeMedicalRecords(String patientId, String diagnosis) {

        if (patientId == null || patientId.isBlank()) {

            return false;
        }

        if (diagnosis == null || diagnosis.isBlank()) {

            return false;
        }

        MedicalRecord record = new MedicalRecord(
                
                nextId("src/main/data/medicalrecords.txt","MR"),
                patientId,
                doctorId,
                diagnosis
        );

        return FileHelper.appendLine(
                "src/main/data/medicalrecords.txt",
                record.toFileLine()
        );
    }

    
    // Issue Prescription
   

    public boolean issuePrescription(String patientId, String medicationList)
            throws InvalidPrescriptionException {

        if (patientId == null || patientId.isBlank()) {

            throw new InvalidPrescriptionException("Patient ID cannot be empty.");
        }

        if (medicationList == null || medicationList.isBlank()) {

            throw new InvalidPrescriptionException("Medication list cannot be empty.");
        }

        Prescription prescription = new Prescription(
                nextId("src/main/data/prescriptions.txt", "RX"),
                patientId,
                doctorId,
                medicationList
        );

        return FileHelper.appendLine(
                "src/main/data/prescriptions.txt",
                prescription.toFileLine()
        );
    }

   
    // Request Lab Test    

    public boolean requestLabTest(String patientId, String testType) {

        if (patientId == null || patientId.isBlank()) {

            return false;
        }

        if (testType == null || testType.isBlank()) {

            return false;
        }

        LabRequest request = new LabRequest(
                nextId("src/main/data/labrequests.txt", "LR"),
                patientId,
                doctorId,
                testType,
                "Pending"
        );

        return FileHelper.appendLine(
               "src/main/data/labrequests.txt",
                request.toFileLine()
        );
    }

    
    // Record Consultation
   

    public boolean logConsultation(String patientId, String recordId) {

        if (patientId == null || patientId.isBlank()) {

            return false;
        }

        String consultationId = nextId(
               "src/main/data/consultations.txt", "C");

        String line = String.join(
                ",",
                consultationId,
                patientId,
                doctorId,
                recordId
        );

        return FileHelper.appendLine(
                "src/main/data/consultations.txt",
                line
        );
    }

   
    // Get Medical Record
    

    public String getMedicalRecordsForPatient(String patientId)
            throws RecordNotFoundException {

        try {

            List<String> lines =
                    FileHelper.readAllLines(
                           "src/main/data/medicalrecords.txt");

            StringBuilder result = new StringBuilder();

            for (String line : lines) {

                if (line.contains(patientId)) {

                    result.append(line)
                            .append(System.lineSeparator());
                }
            }

            if (result.length() == 0) {

                throw new RecordNotFoundException(
                        "No medical records found for patient: " + patientId);
            }

            return result.toString();

        } catch (DataFileNotFoundException e) {

            throw new RecordNotFoundException(
                    "No medical records found for patient: " + patientId);
        }
    }

    
    // Update Lab Request
    

    public boolean updateLabRequestStatus(String requestId, String newStatus)
            throws LabRequestNotFoundException {

        try {

            List<String> lines =
                    FileHelper.readAllLines(
                            "src/main/data/labrequests.txt");

            List<String> updated = new ArrayList<>();

            boolean found = false;

            for (String line : lines) {

                if (line.startsWith(requestId + ",")) {

                    LabRequest request = LabRequest.fromFileLine(line);

                    request.setStatus(newStatus);

                    updated.add(request.toFileLine());

                    found = true;

                } else {

                    updated.add(line);
                }
            }

            if (!found) {

                throw new LabRequestNotFoundException(
                        "Lab request not found: " + requestId);
            }

            return FileHelper.writeAllLines("src/main/data/labrequests.txt", updated);

        } catch (DataFileNotFoundException e) {

            throw new LabRequestNotFoundException(
                    "Lab request not found with id: " + requestId);
        }
    }

    
    // Generate next ID
   

    private String nextId(String filePath, String prefix) {

        try {

            List<String> lines = FileHelper.readAllLines(filePath);

            return prefix + (lines.size() + 1);

        } catch (DataFileNotFoundException e) {

            return prefix + "1";
        }
    }
}