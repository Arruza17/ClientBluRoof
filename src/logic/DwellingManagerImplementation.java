/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import interfaces.DwellingManager;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.DwellingRestfulClient;

/**
 *
 * @author ander
 */
public class DwellingManagerImplementation implements DwellingManager {
    
    private DwellingRestfulClient dwellingRestfulClient;
    
    public DwellingManagerImplementation() {
    }

    @Override
    public String countREST() throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T findByMinConstructionDate(Class<T> responseType, String date) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T findByMinRating(Class<T> responseType, String rate) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object requestEntity) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T findAllDwellings(GenericType<T> responseType) throws ClientErrorException {
        return dwellingRestfulClient.findAll(responseType);
    }

    @Override
    public void remove(String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
