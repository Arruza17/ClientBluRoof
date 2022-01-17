package exceptions;

/**
 * This exception is used in case password fields not equal.
 * @author Jorge_Crespo
 */
public class PassNotEqualException extends Exception{
    /**
     * Exception used to warn about passwordFields not being equal.
     */
    public PassNotEqualException() {
      super("Password fields are not the same");
    }
    
}
