package factories;

import logic.DwellingManagerImplementation;
import interfaces.DwellingManager;
import javax.naming.OperationNotSupportedException;

/**
 * The factory class for the dwellings
 *
 * @author Ander Arruza
 */
public class DwellingManagerFactory {

    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    /**
     * Method that creates a dwelling manager depending of the type that the
     * user wants
     *
     * @param type the type of the logical part
     * @return a DwellingManager interface inicialized
     * @throws OperationNotSupportedException if the operation is not supported
     */
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
