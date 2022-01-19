/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cipher.Cipher;
import interfaces.UserManager;
import exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.User;
import restful.UserRESTClient;

/**
 *
 * @author YERAY
 */
public class UserManagerImplementation implements UserManager {

    private final UserRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("UserManagerImplementation");

    public UserManagerImplementation() {
        webClient = new UserRESTClient();
    }

    @Override
    public List<User> findAllUsers() throws BusinessLogicException {
        List<User> users = null;
        try {
            LOGGER.info("UsersManager: Finding all users from REST service (XML).");
            //Ask webClient for all users' data.
            users = webClient.findAll_XML(new GenericType<List<User>>() {
            });
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all users, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all users:\n" + ex.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findAllAdminsByLogin(String login) throws BusinessLogicException {
        List<User> users = null;
        try {
            LOGGER.log(Level.INFO, "UsersManager: Finding all admins that contain {0}from REST service (XML).", login);
            //Ask webClient for all admins' data.
            users = webClient.findAllAdminsByLogin(new GenericType<List<User>>() {
            }, login);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all admins with login, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all admins with that login:\n" + ex.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findAllAdmins() throws BusinessLogicException {
        List<User> admins = null;
        try {
            LOGGER.info("UsersManager: Finding all admins from REST service (XML).");
            //Ask webClient for all admins' data.
            admins = webClient.findAllAdmins(new GenericType<List<User>>() {
            });

        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all admins, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all admins:\n" + ex.getMessage());
        }
        return admins;
    }

    /**
     * This method updates data for an existing User data for user. This is done
     * by sending a PUT request to a RESTful web service.
     *
     * @param user The UserBean object to be updated.
     * @throws BusinessLogicException If there is any error while processing.
     */
    @Override
    public void updateUser(User user) throws BusinessLogicException {
        try {
            LOGGER.log(Level.INFO, "UsersManager: Updating user {0}.", user.getLogin());
            webClient.edit(user, String.valueOf(user.getId()));
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception updating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error updating user:\n" + ex.getMessage());
        }
    }

    /**
     * This method adds a new created UserBean. This is done by sending a POST
     * request to a RESTful web service.
     *
     * @param user The UserBean object to be added.
     * @throws BusinessLogicException If there is any error while processing.
     */
    @Override
    public void createUser(User user) throws BusinessLogicException {
        try {
            LOGGER.log(Level.INFO, "UsersManager: Creating user {0}.", user.getLogin());
            //Send user data to web client for creation. 
            webClient.create(user);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating user:\n" + ex.getMessage());
        }
    }

    /**
     * This method deletes data for an existing user. This is done by sending a
     * DELETE request to a RESTful web service.
     *
     * @param id
     * @throws BusinessLogicException If there is any error while processing.
     */
    @Override
    public void deleteUser(String id) throws BusinessLogicException {
        try {
            LOGGER.log(Level.INFO, "UsersManager: Deleting user by its id");
            webClient.remove(id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error deleting user:\n" + ex.getMessage());
        }
    }

    @Override
    public User login(String login, String password) throws BusinessLogicException {
        User user = null;
        try {
            LOGGER.log(Level.INFO, "UsersManager: Logging user");
            user = webClient.logInUser(new GenericType<User>() {
            }, login, new Cipher().cipher(password.getBytes()));
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception logging user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error logging user:\n" + ex.getMessage());
        }
        return user;
    }

    @Override
    public User changePassword(String user, String pass) throws BusinessLogicException {
        User changedUser = null;
        try {
            LOGGER.log(Level.INFO, "UsersManager: Changing {0}'s password ", user);
            //user = webClient.changePassword(new GenericType<User>(){
            // }, user, pass);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception changing password, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error changing password:\n" + ex.getMessage());
        }
        return changedUser;
    }

    @Override
    public void resetPassword(String user) throws BusinessLogicException {
        try {
            LOGGER.log(Level.INFO, "UsersManager: Resetting {0}'s password", user);
            webClient.resetPassword(new GenericType<User>() {
            }, user);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception ressetting password, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error ressetting password:\n" + ex.getMessage());
        }

    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
