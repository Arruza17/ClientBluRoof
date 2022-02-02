package restful;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import org.glassfish.hk2.utilities.reflection.Logger;

/**
 * Jersey REST client generated for REST resource:FacilityFacadeREST
 * [entities.facility]<br>
 * USAGE:
 * <pre>
 *        FacilityRestfulClient client = new FacilityRestfulClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author jorge
 */
public class FacilityRestfulClient {

    private Logger log = Logger.getLogger();
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("resources.config").getString("BASE_URI");
/**
 * Constructor of Facility Restful client.
 * Makes a restful client and adds the path of the webtarget object
 * associated to the client.
 */
    public FacilityRestfulClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.facility");
    }
    /**
     * Method that loads the RestFull with the paths
     * @return a String containing 
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }
    /**
     * Create an facilitys entity XML representation and send it as a request to update it
     * to the facility RESTful web service.
     * @param requestEntity The object containing data to be updated.
     * @param id String containing the id of the facility to be updated
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
    /**
     * Get a Facility's entity XML representation from the Fac RESTful web service and 
     * return it as a generic type object.
     * @param <T> a GenericType
     * @param responseType responseType The Class object of the returning instance.
     * @param id String containig the facility id
     * @return The object containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * Get a dwelling's entity XML representation from the dwelling RESTful web
     * service and return it as a generic type object.
     * @param <T> A GenericType
     * @param responseType responseType The Class object of the returning instance.
     * @param from  First index of the object you want to get from the list of all objects
     * @param to Last index of the object you want to get from the list of all objects
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * Get a list of facilities entities XML representation from the facility RESTful web service
     * sorted by adquisition date and return it as a generic type object.
     * @param <T> a GenericType
     * @param responseType responseType The Class object of the returning instance.
     * @param String containing the date to be searched
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T findFacilityByAdqDate(GenericType<T> responseType, String date) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("adquisitionDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * Create a facility entity XML representation and send it as a request to create it
     * to the facility RESTful web service.
     * @param requestEntity Object containing the Facility to be created
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
    /**
     * Get a list of facilities entities XML representation from the facilities RESTful web service and 
     * return it as a generic type object.
     * @param <T> a GenericType
     * @param responseType responseType The Class object of the returning instance.
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * Send a request to the facility RESTful web service to delete a facility identified by its id.
     * @param id The id of the facility to be removed
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }
    /**
     * Get a list of facilities entities XML representation from the facility RESTful web service
     * sorted by type and return it as a generic type object.
     * @param <T> a GenericType
     * @param responseType responseType The Class object of the returning instance.
     * @param facilityType String containig the facility type
     * @return  A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The error is wrapped in a HTTP error response.  
     */
    public <T> T findFacilityByType(GenericType<T> responseType, String facilityType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("type/{0}", new Object[]{facilityType}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * Close Restful web service
     */
    public void close() {
        client.close();
    }

}
