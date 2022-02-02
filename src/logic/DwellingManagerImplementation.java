package logic;

import exceptions.BussinessLogicException;
import interfaces.DwellingManager;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import model.Dwelling;
import restful.DwellingRestfulClient;

/**
 * Class that implements the DwellingManager methods Manager Implementation for
 * the RestFul service
 *
 * @author Ander Arruza
 */
public class DwellingManagerImplementation implements DwellingManager {

    private final DwellingRestfulClient dwellingRestfulClient;

    public DwellingManagerImplementation() {
        dwellingRestfulClient = new DwellingRestfulClient();
    }

    /**
     * Method that returns all the dwellings
     *
     * @return a list of all the dwellings
     * @throws BussinessLogicException the bussinessLogic exception
     */
    @Override
    public List<Dwelling> loadAllDwellings() throws BussinessLogicException {
        List<Dwelling> dwellings = null;
        try {
            dwellings = dwellingRestfulClient.findAll_XML(new GenericType<List<Dwelling>>() {
            });
        } catch (ClientErrorException e) {
            throw new BussinessLogicException(e.getMessage());
        }
        return dwellings;
    }

    /**
     * Method that returns all the dwellings by min construction date
     *
     * @param date the date in dd/MM/yyyy format
     * @return the dwellings that had been created after this date
     * @throws BussinessLogicException the bussinessLogic exception
     */
    @Override
    public List<Dwelling> findByDate(String date) throws BussinessLogicException {
        List<Dwelling> dwellings = null;
        try {
            dwellings = dwellingRestfulClient.findByMinConstructionDate(new GenericType<List<Dwelling>>() {
            }, date);
        } catch (ClientErrorException e) {
            throw new BussinessLogicException(e.getMessage());
        }
        return dwellings;
    }

    /**
     * Method that returns all the dwellings by min rating
     *
     * @param rating the integer value of the rating
     * @return the list of all the dwelling greater than the rating
     * @throws BussinessLogicException the bussiness logic exception
     */
    @Override
    public List<Dwelling> findByRating(Integer rating) throws BussinessLogicException {
        List<Dwelling> dwellings = null;
        try {
            dwellings = dwellingRestfulClient.findByMinRating(new GenericType<List<Dwelling>>() {
            }, String.valueOf(rating));
        } catch (ClientErrorException e) {
            throw new BussinessLogicException(e.getMessage());
        }
        return dwellings;
    }

    /**
     * Method that removes a dwelling by giving the id
     *
     * @param id the id of the dwelling to be removed
     * @throws BussinessLogicException the bussinesslogic exception
     */
    @Override
    public void remove(Long id) throws BussinessLogicException {
        try {
            dwellingRestfulClient.remove(id);
        } catch (ClientErrorException e) {
            throw new BussinessLogicException(e.getMessage());
        }
    }

    /**
     * Method that creates a new dwelling
     *
     * @param dwelling the dwelling to add
     * @throws BussinessLogicException if there is an issue while asking for the
     * server side
     */
    @Override
    public void add(Dwelling dwelling) throws BussinessLogicException {
        try {
            dwellingRestfulClient.create(dwelling);
        } catch (ClientErrorException e) {
            throw new BussinessLogicException(e.getMessage());
        }
    }

    /**
     * Method that updates a dwelling
     *
     * @param dwelling the dwelling to be updated
     * @throws BussinessLogicException the bussinesslogic exception
     */
    @Override
    public void update(Dwelling dwelling) throws BussinessLogicException {
        try {
            dwellingRestfulClient.edit(dwelling, String.valueOf(dwelling.getId()));
        } catch (ClientErrorException e) {
            throw new BussinessLogicException(e.getMessage());
        }
    }

}
