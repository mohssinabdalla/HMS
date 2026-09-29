/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mommi
 */
public class MedicalRecord {
    
    private String recordId;
    private String patientId;
    private String doctorId;
    private String diagnosis;

    public MedicalRecord(String recordId, String patientId,
                         String doctorId, String diagnosis) {

        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
    }
    //getter
      public String getRecordId() {
        return recordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
    
    public String getDoctorId() {
        return doctorId;
    }
    
    public void viewDetails() {
       
    }

    public String toFileLine() {
        return String.join(",", recordId, patientId, doctorId, diagnosis);
        
    }
    public static MedicalRecord fromFileLine(String line) {

        String[] p = line.split(",");

        return new MedicalRecord(
                p[0],
                p[1],
                p[2],
                p[3]
        );
    }

}
