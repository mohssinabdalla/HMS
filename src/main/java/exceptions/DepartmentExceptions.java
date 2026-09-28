package exceptions;

public class DepartmentExceptions {

    public static class DuplicateDepartmentException extends HMSException {
        public DuplicateDepartmentException(String message) {
            super(message);
        }
    }

    public static class DepartmentNotFoundException extends HMSException {
        public DepartmentNotFoundException(String message) {
            super(message);
        }
    }
}