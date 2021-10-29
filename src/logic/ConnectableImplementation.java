package logic;

import exceptions.LoginFoundException;
import exceptions.ServerDownException;
import interfaces.Connectable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DataEncapsulator;
import model.User;

/**
 *
 * @author Yeray Sampedro, Jorge Crespo
 */
public class ConnectableImplementation implements Connectable {

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
             de = (DataEncapsulator) ois.readObject();
            if (de.getException().getMessage().equals("CLOSE")) {
                oos.close();
                ois.close();
                cliente.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(ConnectableImplementation.class.getName()).log(Level.SEVERE, null, ex);
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
            /* de = (DataEncapsulator) ois.readObject();
            if (de.getException() != null) {
                throw de.getException();
            }*/
        } catch (Exception ex) {
            Logger.getLogger(ConnectableImplementation.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerDownException(ex.getMessage());

        }

    }

    public ConnectableImplementation() throws ServerDownException {
        PORT = 5000;
        IP = "localhost";
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
