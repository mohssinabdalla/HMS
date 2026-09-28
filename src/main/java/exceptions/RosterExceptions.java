package exceptions;

public class RosterExceptions {

    public static class ShiftConflictException extends HMSException {
        public ShiftConflictException(String message) {
            super(message);
        }
    }

    public static class InvalidShiftTimeException extends HMSException {
        public InvalidShiftTimeException(String message) {
            super(message);
        }
    }
}