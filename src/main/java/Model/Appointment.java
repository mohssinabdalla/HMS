package model;

/**
 * Represents an appointment between a Patient and a Doctor.
 */
public class Appointment {

    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private String status;

    /**
     * Creates a new appointment.
     *
     * New appointments start with status "Booked".
     */
    public Appointment(String appointmentId,
                       String patientId,
                       String doctorId,
                       String date,
                       String time) {

        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = "Booked";
    }

    // -------------------------
    // Getters
    // -------------------------

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    // -------------------------
    // Appointment operations
    // -------------------------

    /**
     * Changes the appointment date and time.
     */
    public void reschedule(String newDate, String newTime) {

        this.date = newDate;
        this.time = newTime;
    }

    /**
     * Cancels the appointment.
     */
    public void cancel() {

        this.status = "Cancelled";
    }

    /**
     * Converts the appointment into one CSV line.
     *
     * Format:
     * appointmentId,patientId,doctorId,date,time,status
     */
    public String toFileString() {

        return String.join(",",
                appointmentId,
                patientId,
                doctorId,
                date,
                time,
                status
        );
    }

    @Override
    public String toString() {

        return "Appointment ID: " + appointmentId
                + " | Patient ID: " + patientId
                + " | Doctor ID: " + doctorId
                + " | Date: " + date
                + " | Time: " + time
                + " | Status: " + status;
    }
}