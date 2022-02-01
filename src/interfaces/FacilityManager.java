package interfaces;


import exceptions.BusinessLogicException;
import java.util.Date;
import java.util.List;
import model.Facility;

/**
 *Interface with all facilities logic methods
 * @author jorge
 */
public interface FacilityManager {
    /**
     * This method returns a List of Facilities, containing all Facilities data.
     * @return List of Facility The List with all data of facilities. 
     * @throws BusinessLogicException 
     */
    public List<Facility> selectAll() throws BusinessLogicException;
    /**
     * This method returns a List of Facilities searched by date.
     * @param date String containing the date to be searched.
     * @return List of Facility The List with all data of facilities by date.
     * @throws BusinessLogicException 
     */
    public List<Facility> selectByDate(String date) throws BusinessLogicException;
    /**
     * This method returns a List of facilities searched by type.
     * @param type String containing the type to be searched.
     * @return List of Facility Tge list with all data of facilities by type.
     * @throws BusinessLogicException 
     */
    public List<Facility> selectByType(String type) throws BusinessLogicException;
    /**
     * This method is used to remove one facility.
     * @param l Long containing the id of the Facility to be deleted.
     * @throws BusinessLogicException 
     */
    public void remove(Long l) throws BusinessLogicException;
    /**
     * This method returns a Facility searched by its id
     * @param id Long containing the id to be searched
     * @return Facility selected
     * @throws BusinessLogicException 
     */
    public Facility selectById(Long id) throws BusinessLogicException;
    /**
     * This method adds a new created Facility.
     * @param f Facility to be created
     * @throws BusinessLogicException 
     */
    public void create(Facility f) throws BusinessLogicException;
    /**
     * This method updates data for an existing Facility. 
     * @param f
     * @param id
     * @throws BusinessLogicException 
     */
    public void update(Facility f,Long id) throws BusinessLogicException;
}
