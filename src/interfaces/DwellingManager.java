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
     * Method that creates a new dwelling
     * @param dwelling the dwelling to add
     * @throws BussinessLogicException if there is an issue while asking for the
     * server side
     */
    public void add(Dwelling dwelling) throws BussinessLogicException;

    /**
     *
     * @return @throws BussinessLogicException
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
     * @param dwelling
     * @throws BussinessLogicException 
     */
    public void update(Dwelling dwelling) throws BussinessLogicException;
    /**
     *
     * @param id
     * @throws BussinessLogicException
     */
    public void remove(Long id) throws BussinessLogicException;
}
