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
     *
     * @param dwelling the dwelling to add
     * @throws BussinessLogicException if there is an issue while asking for the
     * server side
     */
    public void add(Dwelling dwelling) throws BussinessLogicException;

    /**
     * Method that returns all the dwellings
     *
     * @return a list of all the dwellings
     * @throws BussinessLogicException the bussinessLogic exception
     */
    public List<Dwelling> loadAllDwellings() throws BussinessLogicException;

    /**
     * Method that returns all the dwellings by min construction date
     *
     * @param date the date in dd/MM/yyyy format
     * @return the dwellings that had been created after this date
     * @throws BussinessLogicException the bussinessLogic exception
     */
    public List<Dwelling> findByDate(String date) throws BussinessLogicException;

    /**
     * Method that returns all the dwellings by min rating
     *
     * @param rating the integer value of the rating
     * @return the list of all the dwelling greater than the rating
     * @throws BussinessLogicException the bussiness logic exception
     */
    public List<Dwelling> findByRating(Integer rating) throws BussinessLogicException;

    /**
     * Method that updates a dwelling
     *
     * @param dwelling the dwelling to be updated
     * @throws BussinessLogicException the bussinesslogic exception
     */
    public void update(Dwelling dwelling) throws BussinessLogicException;

    /**
     * Method that removes a dwelling by giving the id
     *
     * @param id the id of the dwelling to be removed
     * @throws BussinessLogicException the bussinesslogic exception
     */
    public void remove(Long id) throws BussinessLogicException;
}
