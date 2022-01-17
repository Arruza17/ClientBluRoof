package exceptions;

/**
 * This exception is used in case the server is down.
 * @author Yeray Sampedro
 */
public class ServerDownException extends Exception {

    /**
     * Constructs a new exception with the specified detail message. Used to
     * control if the server is down
     */
    public ServerDownException() {
        super("The server is down, try connecting again in a few minutes");
    }

    public ServerDownException(String msg) {
        super(msg);
    }
}
