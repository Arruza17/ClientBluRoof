/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.BusinessLogicException;
import model.Guest;

/**
 *
 * @author YERAY
 */
public interface GuestManager {
    
    public void register(Guest guest) throws BusinessLogicException;
    
    public Guest findById(String id)  throws BusinessLogicException;
}
