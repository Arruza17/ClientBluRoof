/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.BusinessLogicException;
import java.util.List;
import model.User;

/**
 *
 * @author YERAY
 */
public interface UserManager {

    public void updateUser(User user) throws BusinessLogicException;

    public void createUser(User user) throws BusinessLogicException;

    public List<User> findAllUsers() throws BusinessLogicException;

    public void deleteUser(String id) throws BusinessLogicException;

    public User login(String login, String password) throws BusinessLogicException;

    public User changePassword(String user, String pass) throws BusinessLogicException;

    public void resetPassword(String user) throws BusinessLogicException;

    public List<User> findAllAdmins() throws BusinessLogicException;

    public List<User> findAllAdminsByLogin(String login) throws BusinessLogicException;

    public void close();

}
