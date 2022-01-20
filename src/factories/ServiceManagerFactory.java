/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

/**
 *
 * @author 2dam
 */
import logic.ServiceManagerImplementation;
import interfaces.ServicesManager;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author ander
 */
public class ServiceManagerFactory {

    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static ServicesManager createServiceManager(String type) throws OperationNotSupportedException {
        //The object to be returned
        ServicesManager dwellingManager = null;
        //Evaluate type parameter
        switch (type) {
            //If rest web client type is asked for, use DwellingManagerImplementation.
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use DwellingManagerImplementation
                dwellingManager = new ServiceManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }

        return dwellingManager;
    }

}
