package exceptions;

/**
 * Thrown when a data file genuinely cannot be found or read —
 * a real I/O problem, distinct from a file that exists but is simply empty
 * (which is normal and returns an empty list, not an exception).
 */ 
public class DataFileNotFoundException extends HMSException {
    public DataFileNotFoundException(String message) {
        super(message);
    }
}
