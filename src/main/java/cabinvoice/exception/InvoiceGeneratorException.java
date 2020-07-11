package cabinvoice.exception;

public class InvoiceGeneratorException extends Exception {
    public ExceptionType exceptionType;
    public enum ExceptionType{
        EMPTY_MAP, NO_SUCH_KEY, KEY_ALREADY_EXISTS,
    }
    public InvoiceGeneratorException(ExceptionType e, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }
}
