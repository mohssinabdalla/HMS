/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * VitalSign
 */
public class VitalSign {
    
    private String vitalId;
    private String patientId;
    private String doctorId;
    private String bloodPressure;
    private String heartRate;
    private String temperature;
    
    public VitalSign(String vitalId, String patientId, String doctorId, String bloodPressure, String heartRate, String temperature) {
        
        this.vitalId = vitalId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
    }
    //getter
     public String getVitalId() {
        return vitalId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getTemperature() {
        return temperature;
    }
    
    /**
     * record vital sign
     */
    public void recordVitals() {
        
    }
    public String toFileLine() {

        return String.join(",", vitalId, patientId, doctorId, bloodPressure, heartRate, temperature); 
               
        
    }

    /**
     * Rebuilds a VitalSign object from a saved file line.
     */
    public static VitalSign fromFileLIne(String line) {

        String[] p = line.split(",");

        return new VitalSign(
                p[0],
                p[1],
                p[2],
                p[3],
                p[4],
                p[5]
        );
    }
    
}
