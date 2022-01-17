package exceptions;

/**
 * This exception is used in case the e-mail format typed is incorrect.
 * @author Yeray Sampedro
 */
public class EmailFormatException extends Exception{
    
    /**
     * Constructs a new exception with the specified detail message. Used to control email format validity
     */
    public EmailFormatException(){
        super("Please enter the e-mail in user@server.domain format. EX: someone@mymail.net");
    }
           
}
