/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import exceptions.ServerDownException;
import interfaces.Connectable;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.ConnectableImplementation;

/**
 *
 * @author Yeray Sampedro
 */
public class ConnectableFactory {

    private static final Logger logger = Logger.getLogger(ConnectableImplementation.class.getName());

    public static Connectable getConnectable() throws ServerDownException {
        Connectable con = null;
        try {
            con = new ConnectableImplementation();
        } catch (ServerDownException ex) {
            logger.warning(ex.getMessage());
            throw new ServerDownException();
        }
        return con;
    }

}
