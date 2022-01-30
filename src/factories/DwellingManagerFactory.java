package factories;

import logic.DwellingManagerImplementation;
import interfaces.DwellingManager;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author ander
 */
public class DwellingManagerFactory {

    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static DwellingManager createDwellingManager(String type) throws OperationNotSupportedException {
        //The object to be returned
        DwellingManager dwellingManager = null;
        //Evaluate type parameter
        switch (type) {
            //If rest web client type is asked for, use DwellingManagerImplementation.
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use DwellingManagerImplementation
                dwellingManager = new DwellingManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }

        return dwellingManager;
    }
}
