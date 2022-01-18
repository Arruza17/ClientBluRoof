/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Ander Arruza
 */
public interface DwellingManager {
    public String countREST() throws ClientErrorException;

    public <T> T findByMinConstructionDate(Class<T> responseType, String date) throws ClientErrorException;

    public void edit(Object requestEntity, String id) throws ClientErrorException;

    public <T> T findByMinRating(Class<T> responseType, String rate) throws ClientErrorException;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;

    public void create(Object requestEntity) throws ClientErrorException;

    public <T> T findAllDwellings(GenericType<T> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public void close();
}
