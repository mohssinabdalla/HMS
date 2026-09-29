package model;

import service.PatientAppointmentService;
import service.PatientFeedbackService;
import exceptions.BookingConflictException;
import exceptions.AppointmentNotFoundException;
import java.util.List;
import java.util.Scanner;;

/**
 * Represents a Patient in the Hospital Management System.
 */
public class Patient extends User {

    private String patientId;
    private String dateOfBirth;

    public Patient(String userId,
                   String username,
                   String password,
                   String fullName,
                   String email,
                   String phoneNumber) {

        super(userId, username, password,
              fullName, email, phoneNumber, "Patient");

        this.patientId = userId;
        this.dateOfBirth = "";
    }

    // -------------------------
    // Getters & Setters
    // -------------------------

    public String getPatientId() {
        return patientId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



    @Override
    public boolean updateOwnProfile(String fullName,
                                    String email,
                                    String phoneNumber) {

        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        this.fullName = fullName.trim();
        this.email = email.trim();
        this.phoneNumber = phoneNumber.trim();

        return true;
    }
    @Override
    public void loadExtraFields(String[] extraFields){}


  
    public void bookAppointment() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();

        System.out.println("Enter appointment date (e.g. 12/10/2026): ");
        String date = scanner.nextLine();

        System.out.println("Enter appointment time (e.g. 10:00): ");
        String time = scanner.nextLine();

        PatientAppointmentService service = new PatientAppointmentService();

        int nextNumber = service.getAllAppointments().size() + 1;
        String appointmentId = "APT" + String.format("%03d", nextNumber);

        Appointment appointment = new Appointment(
                appointmentId, patientId, doctorId, date, time);

        try {
            boolean success = service.bookAppointment(appointment);

            if (success) {
                System.out.println(
                        "Appointment booked successfully. ID: " + appointmentId
            );
            } else {
                System.out.println("Could not book appointment.");
            }

        } catch (BookingConflictException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    /**
     * Allows the Patient to view their existing appointments, then select one to reschedule.
     */
    public void rescheduleAppointment() {
        Scanner scanner = new Scanner(System.in);
        PatientAppointmentService service = new PatientAppointmentService();

        List<Appointment> myAppointments = service.getAppointmentsByPatient(patientId);
        if (myAppointments.isEmpty()) {
            System.out.println("No appointments found to reschedule.");
            return;
        }

        System.out.println("\n--- Your Current Appointments ---");
        for (Appointment app : myAppointments) {
            System.out.println(app.toFileString());
        }

        System.out.println("\nEnter Appointment ID to reschedule (e.g. APT001): ");
        String appointmentId = scanner.nextLine().trim();

        System.out.println("Enter new date (e.g. 15/10/2026): ");
        String newDate = scanner.nextLine().trim();

        System.out.println("Enter new time (e.g. 14:00): ");
        String newTime = scanner.nextLine().trim();

        try {
            boolean success = service.rescheduleAppointment(
                appointmentId,
                newDate,
                newTime
            );

            if (success) {
                System.out.println(
                        "Appointment " + appointmentId
                        + " rescheduled successfully."
                );
            } else {
                System.out.println("Unable to reschedule appointment.");
            }

        } catch (AppointmentNotFoundException e) {
            System.out.println("Reschedule failed: " + e.getMessage());
        }
    }

   
    public void cancelAppointment() {
        Scanner scanner = new Scanner(System.in);
        PatientAppointmentService service = new PatientAppointmentService();

        List<Appointment> myAppointments = service.getAppointmentsByPatient(patientId);
        if (myAppointments.isEmpty()) {
            System.out.println("No appointments found to cancel.");
            return;
        }

        System.out.println("\n--- Your Current Appointments ---");
        for (Appointment app : myAppointments) {
            System.out.println(app.toFileString());
        }

        System.out.println("\nEnter Appointment ID to cancel (e.g. APT001): ");
        String appointmentId = scanner.nextLine().trim();

        try {
            boolean success = service.cancelAppointment(appointmentId);

            if (success) {
            System.out.println(
                    "Appointment " + appointmentId
                    + " cancelled successfully."
            );
            } else {
                System.out.println("Unable to cancel appointment.");
            }

        } catch (AppointmentNotFoundException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }
    }

   
    public void viewMedicalHistory() {
        System.out.println(
                "Patient " + patientId + " can view medical history."
        );
    }

   
    public void submitFeedback() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your rating (1-5): ");
        int rating = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter your comment: ");
        String comment = scanner.nextLine();

        PatientFeedbackService service = new PatientFeedbackService();

        int nextNumber = service.getFeedbackCount() + 1;
        String feedbackId = "FB" + String.format("%03d", nextNumber);

        Feedback feedback = new Feedback(feedbackId, rating, comment);

        try {
            boolean success = service.submitFeedback(feedback, patientId);

            if (success) {
                System.out.println(
                    "Feedback submitted successfully. ID: " + feedbackId
                );
            } else {
                System.out.println("Could not submit feedback.");
            }

        } catch (exceptions.InvalidRatingException e) {

            System.out.println(
                "Invalid rating: " + e.getMessage()
            );
        }
    }
    
    @Override
    public String toFileString() {
        return super.toFileString() + ","
                + patientId + ","
                + dateOfBirth;
    }
}