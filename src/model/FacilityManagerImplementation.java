/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.FacilityRestfulClient;

/**
 *
 * @author 2dam
 */
public class FacilityManagerImplementation implements FacilityManager{
private FacilityRestfulClient frc;
    @Override
    public List<Facility> selectAll() throws BusinessLogicException {

        List <Facility> fac = null;
        try{
            fac = frc.findAll(new GenericType<List<Facility>>(){});
        }catch(ClientErrorException e){
                Logger.getLogger(FacilityManagerImplementation.class.getName()).log(Level.SEVERE, null, e);
            
        }
        return fac;
    }

    public FacilityManagerImplementation() {
        frc=new FacilityRestfulClient();
        
    }
    
}
