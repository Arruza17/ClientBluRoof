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
    
    public List<Facility> selectAll() throws BusinessLogicException;
    public List<Facility> selectByDate(String date) throws BusinessLogicException;
    public List<Facility> selectByType(String type) throws BusinessLogicException;
    public void remove(Long l) throws BusinessLogicException;
    public Facility selectById(Long id) throws BusinessLogicException;
    public void create(Facility f) throws BusinessLogicException;
    public void update(Facility f,Long id) throws BusinessLogicException;
}
