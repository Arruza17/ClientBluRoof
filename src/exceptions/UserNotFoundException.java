package exceptions;


/**
 * This exception is used in case user is not found.
 * @author Yeray Sampedro
 */
public class UserNotFoundException extends Exception{
    
    /**
     * Constructs a new exception with the specified detail message. Used to control email format validity
     */
    public UserNotFoundException(){
        super("Login or password are incorrect");
    }
           
}
