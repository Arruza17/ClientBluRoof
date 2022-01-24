/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.BusinessLogicException;
import model.Owner;

/**
 *
 * @author YERAY
 */
public interface OwnerManager {
    
    public void register(Owner owner)  throws BusinessLogicException;
    
    public Owner findById(String id)  throws BusinessLogicException;
}
