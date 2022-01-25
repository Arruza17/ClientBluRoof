/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import exceptions.BusinessLogicException;
import interfaces.ServicesManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.Service;
import restful.ServiceRestfulClient;

/**
 *
 * @author 2dam
 */
public class ServiceManagerImplementation implements ServicesManager{

    private final ServiceRestfulClient serviceRestfulClient;
    private Logger LOGGER=Logger.getLogger("ServiceManagerIMple");
    
      public ServiceManagerImplementation() {
        serviceRestfulClient = new ServiceRestfulClient();
    }
    
    
    @Override
    public List<Service> findServiceByType(String serviceType) throws BusinessLogicException {
        List <Service> services = null;
        try{
            services = serviceRestfulClient.findServiceByType(new GenericType<List<Service>>(){},serviceType);
        }catch(ClientErrorException e){
            throw new BusinessLogicException(e.getMessage());
        }
        return services;
    }

    @Override
    public Service find(String id) throws ClientErrorException {
         Service service = null;
        try{
            service = serviceRestfulClient.findServiceByAddress(new GenericType<Service>(){},id);
        }catch(ClientErrorException e){
             try {
                 throw new BusinessLogicException(e.getMessage());
             } catch (BusinessLogicException ex) {
                 Logger.getLogger(ServiceManagerImplementation.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        return service;
    }

    @Override
    public List<Service> findServiceByAddress(String serviceAddress) throws BusinessLogicException {
        List <Service> services = null;
        try{
            services = serviceRestfulClient.findServiceByAddress(new GenericType<List<Service>>(){},serviceAddress);
        }catch(ClientErrorException e){
            throw new BusinessLogicException(e.getMessage());
        }
        return services;
    }

    @Override
    public List<Service> findServiceByName(String serviceName) throws BusinessLogicException {
       List <Service> services = null;
        try{
            services = serviceRestfulClient.findServiceByName(new GenericType<List<Service>>(){},serviceName);
        }catch(ClientErrorException e){
            throw new BusinessLogicException(e.getMessage());
        }
        return services;
    }

    @Override
    public List<Service> findAll() throws BusinessLogicException {
         List <Service> services = null;
        try{
            services = serviceRestfulClient.findAll(new GenericType<List<Service>>(){});
        }catch(ClientErrorException e){
            throw new BusinessLogicException(e.getMessage());
        }
        return services;
    }

    @Override
    public void updateService(Service service) throws BusinessLogicException {
        
         try {
            LOGGER.log(Level.INFO, "UsersManager: Updating user {0}.", service.getName());
            serviceRestfulClient.edit(service, String.valueOf(service.getId()));
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception updating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error updating user:\n" + ex.getMessage());
        }
    }

    @Override
    public void createService(Service service) throws BusinessLogicException {
       
        try {
            LOGGER.log(Level.INFO, "ServiceManager: Creating service {0}.", service.getName());
            //Send user data to web client for creation. 
            serviceRestfulClient.create(service);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating user:\n" + ex.getMessage());
        }
        
    }

    @Override
    public void deleteService(Long id) throws BusinessLogicException {
     
         try {
            serviceRestfulClient.remove(String.valueOf(id));
        } catch (ClientErrorException e) {
            throw new BusinessLogicException(e.getMessage());
        }
        
    }

   


}
