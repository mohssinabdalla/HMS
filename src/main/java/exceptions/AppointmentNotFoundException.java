package exceptions;

public class AppointmentNotFoundException extends HMSException {

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}