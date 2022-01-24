/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.GuestManager;
import javax.naming.OperationNotSupportedException;
import logic.GuestManagerImplementation;

/**
 *
 * @author YERAY
 */
public class GuestManagerFactory {

    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static GuestManager createGuestManager(String type)
            throws OperationNotSupportedException {
        //The object to be returned.
        GuestManager guestManager = null;
        //Evaluate type parameter.
        switch (type) {
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use UsersManagerImplementation.
                guestManager = new GuestManagerImplementation();
                break;
            default:
                //If type is not one of the types accepted.
                throw new OperationNotSupportedException("Users manager type not supported.");
        }
        return guestManager;
    }
}
