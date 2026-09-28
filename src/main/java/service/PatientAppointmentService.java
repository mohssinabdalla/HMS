package service;

import model.Appointment;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles appointment operations performed by a Patient.
 *
 * Appointments are stored in data/appointments.txt.
 */
public class PatientAppointmentService {

    private static final String APPOINTMENT_FILE = "src/main/data/appointments.txt";

    private List<Appointment> appointments;

    /**
     * Creates the service and loads existing appointments.
     */
    public PatientAppointmentService() {
        appointments = new ArrayList<>();
        loadAppointments();
    }

    /**
     * Books a new appointment.
     */
    public boolean bookAppointment(Appointment appointment) {

        if (appointment == null) {
            return false;
        }
        
        if (new PatientProfileService().loadPatient(appointment.getPatientId()) == null) {
            return false;
        }

        if (!new PatientDoctorService().doctorExists(appointment.getDoctorId())) {
        return false;
        }

        // Prevent duplicate appointment IDs.
        if (findAppointment(appointment.getAppointmentId()) != null) {
            return false;
        }

        appointments.add(appointment);

        saveAppointments();

        return true;
    }

    /**
     * Returns all appointments belonging to a Patient.
     */
    public List<Appointment> getAppointmentsByPatient(String patientId) {

        List<Appointment> patientAppointments =
                new ArrayList<>();

        for (Appointment appointment : appointments) {

            if (appointment.getPatientId().equals(patientId)) {
                patientAppointments.add(appointment);
            }
        }

        return patientAppointments;
    }

    /**
     * Finds an appointment by ID.
     */
    public Appointment findAppointment(String appointmentId) {

        for (Appointment appointment : appointments) {

            if (appointment.getAppointmentId()
                    .equals(appointmentId)) {

                return appointment;
            }
        }

        return null;
    }

    /**
     * Reschedules an appointment.
     */
    public boolean rescheduleAppointment(String appointmentId,
                                         String newDate,
                                         String newTime) {

        Appointment appointment =
                findAppointment(appointmentId);

        if (appointment == null) {
            return false;
        }

        if (!appointment.getStatus().equals("Booked")) {
            return false;
        }

        appointment.reschedule(newDate, newTime);

        saveAppointments();

        return true;
    }

    /**
     * Cancels an appointment.
     */
    public boolean cancelAppointment(String appointmentId) {

        Appointment appointment =
                findAppointment(appointmentId);

        if (appointment == null) {
            return false;
        }

        if (!appointment.getStatus().equals("Booked")) {
            return false;
        }

        appointment.cancel();

        saveAppointments();

        return true;
    }

    /**
     * Returns all appointments.
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    /**
     * Saves all appointments to appointments.txt.
     */
    private void saveAppointments() {

        try (PrintWriter writer =
                     new PrintWriter(
                             new BufferedWriter(
                                     new FileWriter(APPOINTMENT_FILE)))) {

            for (Appointment appointment : appointments) {
                writer.println(appointment.toFileString());
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving appointments: "
                    + e.getMessage()
            );
        }
    }

    /**
     * Loads appointments from appointments.txt.
     */
    private void loadAppointments() {

        File file = new File(APPOINTMENT_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length == 6) {

                    Appointment appointment =
                            new Appointment(
                                    parts[0],
                                    parts[1],
                                    parts[2],
                                    parts[3],
                                    parts[4]
                            );

                    if (parts[5].equals("Cancelled")) {
                        appointment.cancel();
                    }

                    appointments.add(appointment);
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading appointments: "
                    + e.getMessage()
            );
        }
    }
}