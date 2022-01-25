/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;


import logic.ServiceManagerImplementation;
import interfaces.ServicesManager;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author Adrián
 */
public class ServiceManagerFactory {

    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static ServicesManager createServiceManager(String type) throws OperationNotSupportedException {
        //The object to be returned
        ServicesManager serviceManager = null;
        //Evaluate type parameter
        switch (type) {
            //If rest web client type is asked for, use ServiceManagerImplementation.
            case REST_WEB_CLIENT_TYPE:
               
                serviceManager = new ServiceManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }

        return serviceManager;
    }

}
