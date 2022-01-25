package logic;

import exceptions.BusinessLogicException;
import interfaces.UserChild;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Guest;
import model.Owner;
import model.User;
import restful.GuestRestfulClient;
import restful.OwnerRestfulClient;
import restful.UserRESTClient;

/**
 *
 * @author Yeray Sampedro
 */
public class UserChildImplementation implements UserChild {

    private static final Logger LOGGER = Logger.getLogger("UserChildImplementation");

    @Override
    public void register(User user) throws BusinessLogicException {
        try {
            if (user instanceof Owner) {
                OwnerRestfulClient webTarget = new OwnerRestfulClient();
                webTarget.create((Owner)user);
            } else if (user instanceof Guest) {
                GuestRestfulClient webTarget = new GuestRestfulClient();
                webTarget.create((Guest)user);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating user:\n" + ex.getMessage());
        }

    }

}
