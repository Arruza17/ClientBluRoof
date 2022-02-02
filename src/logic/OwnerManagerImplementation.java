package logic;

import exceptions.BusinessLogicException;
import exceptions.ExceptionGenerator;
import interfaces.OwnerManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.Owner;
import restful.OwnerRestfulClient;

/**
 * Implementation of the owner manager for a restful client
 *
 * @author Yeray Sampedro
 */
public class OwnerManagerImplementation implements OwnerManager {

    private final OwnerRestfulClient webClient;
    private static final Logger LOGGER = Logger.getLogger("UserManagerImplementation");

    /**
     *
     */
    public OwnerManagerImplementation() {
        webClient = new OwnerRestfulClient();
    }

    /**
     * Method used to register a owner
     *
     * @param owner the owner to register
     * @throws BusinessLogicException
     */
    @Override
    public void register(Owner owner) throws BusinessLogicException {
        try {
            LOGGER.log(Level.INFO, "OwnersManager: Creating owner {0}.", owner.getLogin());
            //Send user data to web client for creation. 
            webClient.create(owner);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating owner, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating owner:\n"
                    + ExceptionGenerator.exceptionGenerator(ex.getResponse().getStatus()));
        }
    }

    /**
     * Method used to find a owner by id
     *
     * @param id the owner to find
     * @return the owner with that id
     * @throws BusinessLogicException
     */
    @Override
    public Owner findById(String id) throws BusinessLogicException {
        Owner owner = null;
        try {
            LOGGER.log(Level.INFO, "OwnerManager: Finding owner whose id is {0} from REST service (XML).", id);
            //Ask webClient for all admins' data.
            owner = webClient.find(new GenericType<Owner>() {
            }, id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "OwnerManager: Exception finding owner with id, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding owner with that id:\n"
                    + ExceptionGenerator.exceptionGenerator(ex.getResponse().getStatus()));
        }
        return owner;
    }

}
