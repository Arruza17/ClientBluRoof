/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.BusinessLogicException;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import model.Service;

/**
 *
 * @author 2dam
 */
public interface ServicesManager {

    public List<Service> findServiceByType(String serviceType) throws BusinessLogicException;

    public Service find(String id) throws ClientErrorException;

    public List<Service> findServiceByAddress(String serviceAddress) throws BusinessLogicException;

    public List<Service> findServiceByName(String serviceName) throws BusinessLogicException;

    public List<Service> findAll() throws BusinessLogicException;
    
    public void updateService(Service service) throws BusinessLogicException;

    public void createService(Service service) throws BusinessLogicException;
    
    public void deleteService(Long id) throws BusinessLogicException;

}
