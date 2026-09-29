package exceptions;

public class InvalidRatingException extends HMSException {

    public InvalidRatingException(String message) {
        super(message);
    }
}