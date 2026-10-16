/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.File;

/**
 * Single place that knows where every data file lives.
 * Paths are relative to the project root (the working directory NetBeans
 * uses when running the project), so the app works on any computer.
 *
 * @author hp
 */
public class DataPaths {

    private static final String BASE = "src" + File.separator + "main" + File.separator + "data" + File.separator;
    private static final String USERS = BASE + "UsersData" + File.separator;

    public static String forRole(String role) {
        switch (role) {
            case "Admin":
                return USERS + "admins.txt";
            case "Doctor":
                return USERS + "doctors.txt";
            case "Patient":
                return USERS + "patients.txt";
            case "MedicalManager":
                return USERS + "medicalManagers.txt";
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

    public static String forEntity(String entity) {
        switch (entity) {
            case "InsuranceNetwork":
                return BASE + "insurance_networks.txt";
            case "ConsultationRate":
                return BASE + "consultation_rates.txt";
            case "HospitalAsset":
                return BASE + "assets.txt";
            case "DoctorAssignment":
                return BASE + "assignments.txt";
            case "AuditLog":
                return BASE + "auditlog.txt";
            case "Appointment":
                return BASE + "appointments.txt";
            case "Consultation":
                return BASE + "consultations.txt";
            case "Department":
                return BASE + "departments.txt";
            case "Feedback":
                return BASE + "feedback.txt";
            case "LabRequest":
                return BASE + "labrequests.txt";
            case "MedicalRecord":
                return BASE + "medicalrecords.txt";
            case "Prescription":
                return BASE + "prescriptions.txt";
            case "Roster":
                return BASE + "rosters.txt";
            case "VitalSign":
                return BASE + "vitals.txt";
            default:
                throw new IllegalArgumentException("Unknown entity: " + entity);
        }
    }

}
