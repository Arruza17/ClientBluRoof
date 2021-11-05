package logic;

import exceptions.ServerDownException;
import interfaces.Connectable;
import java.util.logging.Logger;

/**
 * Class that returns the instace of Connectable
 *
 * @author Yeray Sampedro
 */
public class ConnectableFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectableImplementation.class.getName());

    /**
     * Method that return the Connectable Object
     *
     * @return Connectable to open the connection
     * @throws ServerDownException if the Server is down
     */
    public static Connectable getConnectable() throws ServerDownException {
        Connectable con = null;
        try {
            con = new ConnectableImplementation();
        } catch (ServerDownException ex) {
            LOGGER.warning(ex.getMessage());
            throw new ServerDownException();
        }
        return con;
    }

}
