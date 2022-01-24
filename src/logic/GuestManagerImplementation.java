/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import exceptions.BusinessLogicException;
import interfaces.GuestManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.Guest;
import model.Owner;
import restful.GuestRestfulClient;

/**
 *
 * @author YERAY
 */
public class GuestManagerImplementation implements GuestManager {

    private final GuestRestfulClient webClient;
    private static final Logger LOGGER = Logger.getLogger("UserManagerImplementation");

    public GuestManagerImplementation() {
        webClient = new GuestRestfulClient();
    }

    @Override
    public void register(Guest guest) throws BusinessLogicException {
        try {
            LOGGER.log(Level.INFO, "UsersManager: Creating guest {0}.", guest.getLogin());
            //Send user data to web client for creation. 
            webClient.create(guest);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GuestManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating guest:\n" + ex.getMessage());
        }
    }

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
            throw new BusinessLogicException("Error finding Guest with that id:\n" + ex.getMessage());
        }
        return guest;
    }

}
