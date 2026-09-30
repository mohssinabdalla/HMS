/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author hp
 */
public class DataPaths {

    private static final String BASE = "D:\\Univesity\\Semester 3\\OODJ\\Assignment\\HMS\\src\\main\\data\\";

    public static String forRole(String role) {
        switch (role) {
            case "Admin":
                return BASE + "admins.txt";
            case "Doctor":
                return BASE + "doctors.txt";
            case "Patient":
                return BASE + "patients.txt";
            case "MedicalManager":
                return BASE + "medicalManagers.txt";
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
            default:
                throw new IllegalArgumentException("Unknown entity: " + entity);
        }
    }

}
