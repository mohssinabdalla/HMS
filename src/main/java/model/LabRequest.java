/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mommi
 */
public class LabRequest {
    private String requestId;
    private String patientId;
    private String doctorId;
    private String testType;
    private String status;
    public LabRequest(String requestId, String patientId, String doctorId, String testType, String status) {
        this.requestId = requestId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.testType = testType;
        this.status = status;
    }
    //getter
   
    public String getRequestId() {
        return requestId;
    }

    public String getTestType() {
        return testType;
    }

    public String getStatus() {
        return status;
    }
    //setter
    public void setStatus(String status) {
        this.status = status;
    }
    //submit request
    public void submitRequest() {
        
    }
    public String toFileLine() {
        return String.join(",", requestId, patientId, doctorId, testType, status);
    }
    public  static LabRequest fromFileLine(String line) {
        String[] p = line.split(",");
        return new LabRequest(
                p[0],
                p[1],
                p[2],
                p[3],
                p[4]
        );
    }
    
    
 
    
}
