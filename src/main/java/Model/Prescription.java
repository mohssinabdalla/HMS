/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mommi
 */
public class Prescription {
    
    private String prescriptionId;
    private String patientId;
    private String doctorId;
    private String medicationList;
    
    public Prescription(String prescriptionId,String patientId, String doctorId, String medicationList) {
        
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medicationList = medicationList;
    }
    
    //getter
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicationList() {
        return medicationList;
    }
    public void viewDetails() {
        
    }

    public String toFileLine() {
        return String.join(",", prescriptionId, patientId, doctorId, medicationList);
               
    }
   
     public static Prescription fromFileLine(String line) {

        String[] p = line.split(",");

        return new Prescription(
                p[0],
                p[1],
                p[2],
                p[3]
        );
    }
}
