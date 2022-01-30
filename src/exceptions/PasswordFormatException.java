package exceptions;

/**
 * This exception is used in case minimum character limit isn't reached.
 * @author Yeray Sampedro
 */
public class PasswordFormatException extends Exception{

    /**
     *  Constructs a new exception with the specified detail message. Used to control that the password characters are at least 6
     */
    public PasswordFormatException(Integer size) {
        super("The password needs to have at least 6 characters and your password only has "+ size + " characters");
    }
    
    public PasswordFormatException(){
        super("Passwords should not contain spaces within, try with another one");
    }
    
}
