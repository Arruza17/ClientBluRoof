package logic;

import exceptions.LoginFoundException;
import exceptions.ServerDownException;
import interfaces.Connectable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DataEncapsulator;
import model.User;
import view.controllers.SignInController;

/**
 *
 * @author Yeray Sampedro, Jorge Crespo
 */
public class ConnectableImplementation implements Connectable {

    private static final Logger logger = Logger.getLogger(ConnectableImplementation.class.getName());

    private static final ResourceBundle CONFIGFILE = ResourceBundle.getBundle("resources.config");
    private static int PORT;
    private static String IP;
    private static Socket cliente;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    @Override
    public DataEncapsulator signIn(User user) throws ServerDownException {
        DataEncapsulator de = new DataEncapsulator();
        try {
            de.setUser(user);
            oos.writeObject(de);
            de = null;
            de = (DataEncapsulator) ois.readObject();
            if (de.getException() != null) {
                if (de.getException().getMessage().equalsIgnoreCase("CLOSE")) {
                    oos.close();
                    ois.close();
                    cliente.close();
                    throw de.getException();
                }
            }
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
            throw new ServerDownException(ex.getMessage());

        }
        return de;
    }

    @Override
    public void signUp(User user) throws ServerDownException, LoginFoundException {
        DataEncapsulator de = new DataEncapsulator();;
        try {
            de.setUser(user);
            oos.writeObject(de);
            de = (DataEncapsulator) ois.readObject();
            if (de.getException() != null) {
                throw de.getException();
            }
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
            throw new ServerDownException(ex.getMessage());

        }

    }

    public ConnectableImplementation() throws ServerDownException {
        PORT = Integer.parseInt(CONFIGFILE.getString("PORT"));
        IP = CONFIGFILE.getString("IP");
        try {
            cliente = new Socket(IP, PORT);
            oos = new ObjectOutputStream(cliente.getOutputStream());
            ois = new ObjectInputStream(cliente.getInputStream());
        } catch (IOException ex) {
            //Logger.getLogger(ConnectableImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerDownException(ex.getMessage());

        }
    }

}
