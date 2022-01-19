/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author 2dam
 */
public interface ServicesManager {

    public <T> T findServiceByType(Class<T> responseType, String serviceType) throws ClientErrorException;

    public String countREST() throws ClientErrorException;

    public void edit(Object requestEntity, String id) throws ClientErrorException;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;

    public <T> T findServiceByAddress(Class<T> responseType, String serviceAddress) throws ClientErrorException;

    public <T> T findServiceByName(Class<T> responseType, String serviceName) throws ClientErrorException;

    public void create(Object requestEntity) throws ClientErrorException;

    public <T> T findAll(Class<T> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

}
