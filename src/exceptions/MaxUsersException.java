package exceptions;

/**
 * This exception is used in case max user limit is reached.
 * @author Jorge_Crespo
 */
public class MaxUsersException extends Exception{
    /**
     * Exception used to warn about max limit of users being already reacherd.
     */
    public MaxUsersException() {
        super("Max user limit reached, please try again later.");
    }
    
}
