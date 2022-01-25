/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.BusinessLogicException;
import model.User;

/**
 *
 * @author YERAY
 */
public interface UserChild {
    
       public void register(User user) throws BusinessLogicException;
}
