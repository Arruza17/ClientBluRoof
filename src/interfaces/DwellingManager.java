package interfaces;

import exceptions.BussinessLogicException;
import java.util.List;
import model.Dwelling;

/**
 * Interface of the logic part representing all the methods for
 * dwellingManagement
 *
 * @author Ander Arruza
 */
public interface DwellingManager {
    /**
     * 
     * @param dwelling
     * @throws BussinessLogicException 
     */
    public void add(Dwelling dwelling) throws BussinessLogicException;
    /**
     * 
     * @return
     * @throws BussinessLogicException 
     */
    public List<Dwelling> findAll() throws BussinessLogicException;
    /**
     * 
     * @param date
     * @return
     * @throws BussinessLogicException 
     */
    public List<Dwelling> findByDate(String date) throws BussinessLogicException;
    /**
     * 
     * @param rating
     * @return
     * @throws BussinessLogicException 
     */
    public List<Dwelling> findByRating(Integer rating) throws BussinessLogicException;
    /**
     * 
     * @param id
     * @throws BussinessLogicException 
     */
    public void remove(Long id) throws BussinessLogicException;
}
