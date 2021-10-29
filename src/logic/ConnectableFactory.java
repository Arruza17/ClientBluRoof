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

    public static Connectable getConnectable() {
        Connectable con = null;
        try {
            con = new ConnectableImplementation();
        } catch (ServerDownException ex) {
            Logger.getLogger(ConnectableFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

}
