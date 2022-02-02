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
import view.controllers.ServicesController;

/**
 *
 * @author Adrián Pérez
 */
public class ServiceManagerImplementation implements ServicesManager{

    private final ServiceRestfulClient serviceRestfulClient;
    private static final Logger LOGGER = Logger.getLogger(ServiceManagerImplementation.class.getName());
    
    /**
     *
     */
    public ServiceManagerImplementation() {
        serviceRestfulClient = new ServiceRestfulClient();
    }
    
   /**
     * This method returns a List of Services, containing all services data.
     *
     * @return List The List with all data for services.
     * @throws BusinessLogicException If there is any error while processing.
     */
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

    /**
     *
     * @param id
     * @return
     * @throws ClientErrorException
     */
    @Override
    public Service find(String id) throws ClientErrorException {
         Service service = null;
        try{
            service = serviceRestfulClient.findServiceByAddress(new GenericType<Service>(){},id);
        }catch(ClientErrorException e){
             try {
                 throw new BusinessLogicException(e.getMessage());
             } catch (BusinessLogicException ex) {
                 LOGGER.log(Level.SEVERE, null, ex);
             }
        }
        return service;
    }

    /**
     *
     * @param serviceAddress
     * @return
     * @throws BusinessLogicException
     */
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

    /**
     *
     * @param serviceName
     * @return
     * @throws BusinessLogicException
     */
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

    /**
     *
     * @return
     * @throws BusinessLogicException
     */
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

    /**
     *
     * @param service
     * @throws BusinessLogicException
     */
    @Override
    public void updateService(Service service) throws BusinessLogicException {
        
         try {
            LOGGER.log(Level.INFO, "ServiceManager: Updating user {0}.", service.getName());
            serviceRestfulClient.edit(service, String.valueOf(service.getId()));
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "ServiceManager: Exception updating service, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error updating Service:\n" + ex.getMessage());
        }
    }

    /**
     *
     * @param service
     * @throws BusinessLogicException
     */
    @Override
    public void createService(Service service) throws BusinessLogicException {
       
        try {
            LOGGER.log(Level.INFO, "ServiceManager: Creating service {0}.", service.getName());
            //Send user data to web client for creation. 
            serviceRestfulClient.create(service);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "ServiceManager: Exception creating Service, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating user:\n" + ex.getMessage());
        }
        
    }

    /**
     *
     * @param id
     * @throws BusinessLogicException
     */
    @Override
    public void deleteService(Long id) throws BusinessLogicException {
     
         try {
              LOGGER.log(Level.INFO, "ServiceManager: deleting service with the id: {0}.", id);
             
            serviceRestfulClient.remove(String.valueOf(id));
        } catch (ClientErrorException ex) {
            
            LOGGER.log(Level.SEVERE,
                    "ServiceManager: Exception deleting Service, {0}",
                    ex.getMessage());
            
            throw new BusinessLogicException(ex.getMessage());
        }
        
    }

   


}
