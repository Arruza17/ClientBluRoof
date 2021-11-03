package logic;

import exceptions.ServerDownException;
import interfaces.Connectable;
import model.DataEncapsulator;
import model.User;

/**
 *
 * @author Yeray Sampedro, Jorge Crespo
 */
public class ConnectableImplementation implements Connectable {


    @Override
    public void signUp(User user) throws ServerDownException {
        
    }

    @Override
    public DataEncapsulator signIn(User user) throws ServerDownException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
