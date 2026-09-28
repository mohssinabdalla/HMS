package exceptions;

public class ReportExceptions {

    public static class ReportGenerationException extends HMSException {
        public ReportGenerationException(String message) {
            super(message);
        }
    }
}