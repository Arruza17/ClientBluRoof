package logic;

import exceptions.LoginFoundException;
import exceptions.ServerDownException;
import interfaces.Connectable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import model.DataEncapsulator;
import model.User;

/**
 * This class implements the Connectable interface
 *
 * @author Yeray Sampedro
 * 
 */
public class ConnectableImplementation implements Connectable {

    private static final Logger LOGGER = Logger.getLogger(ConnectableImplementation.class.getName());

    private static final ResourceBundle CONFIGFILE = ResourceBundle.getBundle("resources.config");
    private static int PORT;
    private static String IP;
    private static Socket cliente;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    /**
     * Constructor that gets all the data from the file.
     *
     * @throws ServerDownException if the server is down
     */
    public ConnectableImplementation() throws ServerDownException {
        PORT = Integer.parseInt(CONFIGFILE.getString("PORT"));
        IP = CONFIGFILE.getString("IP");
        try {
            cliente = new Socket(IP, PORT);
            oos = new ObjectOutputStream(cliente.getOutputStream());
            ois = new ObjectInputStream(cliente.getInputStream());
        } catch (IOException ex) {
            throw new ServerDownException(ex.getMessage());

        }
    }

    /**
     * This method is called when the user wants to signIn
     *
     * @param user the user to SignIn
     * @return DataEncapsulator object recived from the server with all the user
     * data and exception if any
     * @throws ServerDownException if the server is down
     */
    @Override
    public DataEncapsulator signIn(User user) throws ServerDownException {
        DataEncapsulator de = new DataEncapsulator();
        try {
            oos.flush();
            de.setUser(user);
            oos.writeObject(de);
            de = (DataEncapsulator) ois.readObject();
            if (de.getException() != null) {
                if (de.getException().getMessage().equalsIgnoreCase("OK")) {
                    oos.close();
                    ois.close();
                    cliente.close();
                }
            }
        } catch (Exception ex) {
            LOGGER.warning(ex.getMessage());
            throw new ServerDownException(ex.getMessage());
        }
        return de;
    }

    /**
     * Method that Signs up the user
     *
     * @param user the user to be Signed up
     * @throws ServerDownException if the server is down
     * @throws LoginFoundException if the user is already in the DataBase
     */
    @Override
    public void signUp(User user) throws ServerDownException, LoginFoundException {
        DataEncapsulator de = new DataEncapsulator();;
        try {
            oos.flush();
            de.setUser(user);
            oos.writeObject(de);
            de = (DataEncapsulator) ois.readObject();
            if (de.getException() != null) {
                throw de.getException();
            }
        } catch (Exception ex) {
            LOGGER.warning(ex.getMessage());
            throw new ServerDownException(ex.getMessage());
        }
    }

}
