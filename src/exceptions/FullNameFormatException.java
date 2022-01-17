package exceptions;

/**
 * This exception is used  if the name doesn't  have a gap
 * @author Yeray Sampedro
 */
public class FullNameFormatException extends Exception {

    /**
      * Constructs a new exception with the specified detail message. Used to control the validity of the full name
     */
    public FullNameFormatException() {
        super("The full name should contain the surnames too");
    }

}
