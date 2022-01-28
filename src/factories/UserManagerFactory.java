/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.UserManager;
import javax.naming.OperationNotSupportedException;
import logic.UserManagerImplementation;

/**
 * Method that returns an implementation of UserManager
 *
 * @param type the type of instance
 * @return UserManager the implementation
 * @throws OperationNotSupportedException
 */
public class UserManagerFactory {

    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static UserManager createUsersManager(String type)
            throws OperationNotSupportedException {
        //The object to be returned.
        UserManager userManager = null;
        //Evaluate type parameter.
        switch (type) {
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use UsersManagerImplementation.
                userManager = new UserManagerImplementation();
                break;
            default:
                //If type is not one of the types accepted.
                throw new OperationNotSupportedException("Users manager type not supported.");
        }
        return userManager;
    }
}
