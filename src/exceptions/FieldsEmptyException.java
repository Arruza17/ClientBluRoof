package exceptions;

/**
 * This exception is used in case one field is empty.
 * @author Jorge_Crespo
 */
public class FieldsEmptyException extends Exception{
/**
 * Exception used to warn about a field being empty.
 */
    public FieldsEmptyException() {
        super("At least one field is empty.");
    }
    
}
