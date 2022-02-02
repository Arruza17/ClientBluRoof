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
 * Business logic interface encapsulating business methods for services
 * management.
 *
 * @author Adrián Pérez
 */
public interface ServicesManager {

    /**
     * This method returns a List of Services, containing all services data by type.
     *
     * @return List The List with all data for services.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public List<Service> findServiceByType(String serviceType) throws BusinessLogicException;

    /**
     * This method returns a List of Services, containing all services data by id.
     *
     * @return List The List with all data for services.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public Service find(String id) throws ClientErrorException;

    /**
     * This method returns a List of Services, containing all services data by address.
     *
     * @return List The List with all data for services.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public List<Service> findServiceByAddress(String serviceAddress) throws BusinessLogicException;

    /**
     * This method returns a List of Services, containing all services data by name.
     *
     * @return List The List with all data for services.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public List<Service> findServiceByName(String serviceName) throws BusinessLogicException;

    /**
     * This method returns a List of Services, containing all services data.
     *
     * @return List The List with all data for services.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public List<Service> findAll() throws BusinessLogicException;

    /**
     * This method updates data for an existing ServiceBean data for Service.
     *
     * @param Service The ServiceBean object to be updated.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void updateService(Service service) throws BusinessLogicException;

    /**
     * This method adds a new created ServiceBean.
     *
     * @param Service The ServiceBean object to be added.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void createService(Service service) throws BusinessLogicException;

    /**
     * This method deletes data for an existing Service.
     *
     * @param Service The ServiceBean object to be deleted.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void deleteService(Long id) throws BusinessLogicException;

}
