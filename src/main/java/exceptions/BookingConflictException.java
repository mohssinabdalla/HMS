package exceptions;

public class BookingConflictException extends HMSException {

    public BookingConflictException(String message) {
        super(message);
    }
}