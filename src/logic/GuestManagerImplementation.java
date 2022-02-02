
package logic;

import exceptions.BusinessLogicException;
import exceptions.ExceptionGenerator;
import interfaces.GuestManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.Guest;
import model.Owner;
import restful.GuestRestfulClient;

/**
 * Implementation of the guest manager for a restful client
 *
 * @author Yeray Sampedro
 */
public class GuestManagerImplementation implements GuestManager {

    private final GuestRestfulClient webClient;
    private static final Logger LOGGER = Logger.getLogger("UserManagerImplementation");


    public GuestManagerImplementation() {
        webClient = new GuestRestfulClient();
    }

    /**
     * Method used to register a guest
     *
     * @param guest the guest to register
     * @throws BusinessLogicException
     */
    @Override
    public void register(Guest guest) throws BusinessLogicException {
        try {
            LOGGER.log(Level.INFO, "UsersManager: Creating guest {0}.", guest.getLogin());
            //Send user data to web client for creation. 
            webClient.create(guest);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "GuestManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating guest:\n"
                    + ExceptionGenerator.exceptionGenerator(ex.getResponse().getStatus()));
        }
    }

    /**
     * Method used to find a guest by id
     *
     * @param id the guest to find
     * @return the guest with that id
     * @throws BusinessLogicException
     */
    @Override
    public Guest findById(String id) throws BusinessLogicException {
        Guest guest = null;
        try {
            LOGGER.log(Level.INFO, "GuestManager: Finding Guest whose id is {0} from REST service (XML).", id);
            //Ask webClient for all admins' data.
            guest = webClient.find(new GenericType<Guest>() {
            }, id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "GuestManager: Exception finding Guest with id, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding Guest with that id:\n"
                    + ExceptionGenerator.exceptionGenerator(ex.getResponse().getStatus()));
        }
        return guest;
    }

}
