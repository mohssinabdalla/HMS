import model.Patient;
import java.util.Scanner;

public class TestAppointment {
    public static void main(String[] args) {
        // Create a test patient with ID "P101"
        Patient patient = new Patient("P101", "john_doe", "pass123", "John Doe", "john@mail.com", "0123456789");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- APPOINTMENT TEST MENU ---");
            System.out.println("1. Book Appointment");
            System.out.println("2. Reschedule Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    patient.bookAppointment();
                    break;
                case "2":
                    patient.rescheduleAppointment();
                    break;
                case "3":
                    patient.cancelAppointment();
                    break;
                case "4":
                    System.out.println("Exiting test.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}