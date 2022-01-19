/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import exceptions.BussinessLogicException;
import interfaces.DwellingManager;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.Dwelling;
import restful.DwellingRestfulClient;

/**
 *
 * @author ander
 */
public class DwellingManagerImplementation implements DwellingManager {
    
    private final DwellingRestfulClient dwellingRestfulClient;
    
    public DwellingManagerImplementation() {
        dwellingRestfulClient = new DwellingRestfulClient();
    }

    @Override
    public List<Dwelling> findAll() throws BussinessLogicException {
        List <Dwelling> dwellings = null;
        try{
            dwellings = dwellingRestfulClient.findAll_XML(new GenericType<List<Dwelling>>(){});
        }catch(ClientErrorException e){
            throw new BussinessLogicException(e.getMessage());
        }
        return dwellings;
    }

    @Override
    public List<Dwelling> findByDate(Date date) throws BussinessLogicException {
        List <Dwelling> dwellings = null;
        try{
            dwellings = dwellingRestfulClient.findByMinConstructionDate(new GenericType<List<Dwelling>>(){}, date.toString());
        }catch(ClientErrorException e){
            throw new BussinessLogicException(e.getMessage());
        }
        return dwellings;
    }

    @Override
    public List<Dwelling> findByRating(Integer rating) throws BussinessLogicException {
         List <Dwelling> dwellings = null;
        try{
            dwellings = dwellingRestfulClient.findByMinRating(new GenericType<List<Dwelling>>(){}, String.valueOf(rating));
        }catch(ClientErrorException e){
            throw new BussinessLogicException(e.getMessage());
        }
        return dwellings;    }

    
}
