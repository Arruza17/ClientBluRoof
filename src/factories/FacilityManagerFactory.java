/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import javax.naming.OperationNotSupportedException;
import interfaces.FacilityManager;
import logic.FacilityManagerImplementation;

/**
 *
 * @author jorge
 */
public class FacilityManagerFactory {
     public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static FacilityManager createFacilityManager(String type) throws OperationNotSupportedException {
        //The object to be returned
        FacilityManager facManager = null;
        //Evaluate type parameter
        switch (type) {
            //If rest web client type is asked for, use FacilityManagerImplementation.
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use FacilityManagerImplementation
                facManager = new FacilityManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }

        return facManager;
    }
}
