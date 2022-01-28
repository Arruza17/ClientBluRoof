/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.OwnerManager;

import javax.naming.OperationNotSupportedException;
import logic.OwnerManagerImplementation;

/**
 * Method that returns an implementation of UserManager
 *
 * @param type the type of instance
 * @return UserManager the implementation
 * @throws OperationNotSupportedException
 */
public class OwnerManagerFactory {

    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static OwnerManager createOwnerManager(String type)
            throws OperationNotSupportedException {
        //The object to be returned.
        OwnerManager ownerManager = null;
        //Evaluate type parameter.
        switch (type) {
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use UsersManagerImplementation.
                ownerManager = new OwnerManagerImplementation();
                break;
            default:
                //If type is not one of the types accepted.
                throw new OperationNotSupportedException("Users manager type not supported.");
        }
        return ownerManager;
    }
}
