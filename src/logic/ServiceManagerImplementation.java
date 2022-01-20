/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import exceptions.BussinessLogicException;
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
    
      public ServiceManagerImplementation() {
        serviceRestfulClient = new ServiceRestfulClient();
    }
    
    
    @Override
    public List<Service> findServiceByType(String serviceType) throws BussinessLogicException {
        List <Service> services = null;
        try{
            services = serviceRestfulClient.findServiceByType(new GenericType<List<Service>>(){},serviceType);
        }catch(ClientErrorException e){
            throw new BussinessLogicException(e.getMessage());
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
                 throw new BussinessLogicException(e.getMessage());
             } catch (BussinessLogicException ex) {
                 Logger.getLogger(ServiceManagerImplementation.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        return service;
    }

    @Override
    public List<Service> findServiceByAddress(String serviceAddress) throws BussinessLogicException {
        List <Service> services = null;
        try{
            services = serviceRestfulClient.findServiceByAddress(new GenericType<List<Service>>(){},serviceAddress);
        }catch(ClientErrorException e){
            throw new BussinessLogicException(e.getMessage());
        }
        return services;
    }

    @Override
    public List<Service> findServiceByName(String serviceName) throws BussinessLogicException {
       List <Service> services = null;
        try{
            services = serviceRestfulClient.findServiceByName(new GenericType<List<Service>>(){},serviceName);
        }catch(ClientErrorException e){
            throw new BussinessLogicException(e.getMessage());
        }
        return services;
    }

    @Override
    public List<Service> findAll() throws BussinessLogicException {
         List <Service> services = null;
        try{
            services = serviceRestfulClient.findAll(new GenericType<List<Service>>(){});
        }catch(ClientErrorException e){
            throw new BussinessLogicException(e.getMessage());
        }
        return services;
    }

   


}
