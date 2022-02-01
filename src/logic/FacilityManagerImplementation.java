package logic;

import exceptions.BusinessLogicException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.Facility;
import interfaces.FacilityManager;
import restful.FacilityRestfulClient;

/**
 *Implementation of the facility manager
 * @author jorge
 */
public class FacilityManagerImplementation implements FacilityManager {

    private FacilityRestfulClient frc;
    /**
     * Method used to select all facilities
     * @return a list of facilities
     * @throws BusinessLogicException 
     */
    @Override
    public List<Facility> selectAll() throws BusinessLogicException {

        List<Facility> fac = null;
        try {
            fac = frc.findAll(new GenericType<List<Facility>>() {
            });
        } catch (ClientErrorException e) {
            Logger.getLogger(FacilityManagerImplementation.class.getName()).log(Level.SEVERE, null, e);

        }
        return fac;
    }

    public FacilityManagerImplementation() {
        frc = new FacilityRestfulClient();

    }
    /**
     * Method used to select data from facilities in 
     * the database by the selected date.
     * @param date String of the date which will be searched
     * @return list of facilities
     * @throws BusinessLogicException 
     */
    @Override
    public List<Facility> selectByDate(String date) throws BusinessLogicException {
        List<Facility> fac = null;
        try {
            fac = frc.findFacilityByAdqDate(new GenericType<List<Facility>>() {
            }, date);
        } catch (ClientErrorException e) {
            Logger.getLogger(FacilityManagerImplementation.class.getName()).log(Level.SEVERE, null, e);
        }
        return fac;

    }
/**
 * Method to obtain facilities by type.
 * @param type String containing the type
 * @return list of Facilities
 * @throws BusinessLogicException 
 */
    @Override
    public List<Facility> selectByType(String type) throws BusinessLogicException {
        List<Facility> fac = null;
        try {
            fac = frc.findFacilityByType(new GenericType<List<Facility>>() {
            }, type);
        } catch (ClientErrorException ex) {
        }
        return fac;
    }
/**
 * Method to obtain one facility selected by Id
 * @param id Long containing the id of the facility which will be searched.
 * @return the selected Facility
 * @throws BusinessLogicException 
 */
    @Override
    public Facility selectById(Long id) throws BusinessLogicException {
        Facility fac = null;
        try {
            fac = frc.find(new GenericType<Facility>() {
            }, id.toString());
        } catch (ClientErrorException ex) {
        }
        
        return fac;
    }
    /**
     * Method used to delete one facility by id.
     * @param id Long containing the id of the facility which will be deleted.
     * @throws BussinessLogicException 
     */
    @Override
    public void remove(Long id) throws BusinessLogicException {
        try {
            frc.remove(String.valueOf(id));
        } catch (ClientErrorException e) {
            throw new BusinessLogicException(e.getMessage());
        }
    }
/**
 * Method used to create one facility via POST request to the restful
 * web sevices.
 * @param f Facility object containing the new facility to be added.
 * @throws BusinessLogicException 
 */
    @Override
    public void create(Facility f) throws BusinessLogicException {
         try {
            frc.create(f);
        } catch (ClientErrorException e) {
            throw new BusinessLogicException(e.getMessage());
        }
    }
/**
 * Method used to update one facility via PUT request to the restful
 * web services.
 * @param f Facility object containing the facility data to be updated
 * @param id Long containing the id of the target facility to be updated.
 * @throws BusinessLogicException 
 */
    @Override
    public void update(Facility f, Long id) throws BusinessLogicException {
        try {
            frc.edit(f, id.toString());
        } catch (ClientErrorException e) {
            throw new BusinessLogicException(e.getMessage());
        }
    }
}
