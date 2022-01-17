package exceptions;

/**
 * This exception is used in case one login is already registered.
 * @author Jorge_Crespo
 */
public class LoginFoundException extends Exception{
    /**
     * Exception used to warn about login being already registered in the database.
     */
    public LoginFoundException() {
        super("Login field is already registered. Change the value of login field.");
    }
    
}
