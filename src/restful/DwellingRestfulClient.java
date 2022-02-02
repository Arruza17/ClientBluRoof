package restful;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:DwellingFacadeREST
 * [entities.dwelling]<br>
 * USAGE:
 * <pre>
 *        DwellingRestfulClient client = new DwellingRestfulClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Ander Arruza
 */
public class DwellingRestfulClient {

    private WebTarget webTarget;
    private Client client;
    private static final Logger LOGGER = Logger.getLogger("DwellingRestfulClient");
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.config");
    private static String BASE_URI;

    /**
     * Construct a DwellingRestfulClient. It creates a RESTful web client and
     * establishes the path of the WebTarget object associated to the client.
     */
    public DwellingRestfulClient() {
        BASE_URI = resourceBundle.getString("BASE_URI");
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.dwelling");
    }

    /**
     * Method that loads the RestFull with the paths
     *
     * @return @throws ClientErrorException
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Get a list of dwelling's that have been constructed later than the date
     * entities XML representation from the dwelling RESTful web service and
     * return it as a generic type object.
     *
     * @param <T> A Generic type
     * @param responseType The Class object of the returning instance.
     * @param date the date to compare with
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The
     * error is wrapped in a HTTP error response.
     */
    public <T> T findByMinConstructionDate(GenericType<T> responseType, String date) throws ClientErrorException {
        LOGGER.info("GETTING ALL DWELLINGS DATA BY MIN CONSTRUCTION DATE");
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("minConstructionDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Method that edits a dwelling and call to the dwelling RESTful web service
     *
     * @param requestEntity an Object to be edited, normally a dwelling
     * @param id the id of the dwelling to be edited
     * @throws ClientErrorException If there is an error while processing. The
     * error is wrapped in a HTTP error response.
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Get a list of dwelling's that have more than that rating entities XML
     * representation from the dwelling RESTful web service and return it as a
     * generic type object.
     *
     * @param <T> A Generic type
     * @param responseType The object containing data to be created.
     * @param rate The minimoun rate to be search by. An String representation
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The
     * error is wrapped in a HTTP error response.
     */
    public <T> T findByMinRating(GenericType<T> responseType, String rate) throws ClientErrorException {
        LOGGER.info("GETTING ALL DWELLINGS DATA BY MIN RATING");
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("minRate/{0}", new Object[]{rate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Get a dwelling entity XML representation from the dwelling RESTful web
     * service and return it as a generic type object.
     *
     * @param <T> A Generic type
     * @param responseType The object containing data to be created.
     * @param id The if of the object to be find
     * @return a single dwelling with data if its found
     * @throws ClientErrorException If there is an error while processing. The
     * error is wrapped in a HTTP error response.
     */
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Get a dwelling's entity XML representation from the dwelling RESTful web
     * service and return it as a generic type object.
     *
     * @param <T> A Generic type
     * @param responseType
     * @param from First index of the object you want to get from the list of
     * all objects
     * @param to Last index of the object you want to get from the list of all
     * objects
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The
     * error is wrapped in a HTTP error response.
     */
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Creates a dwelling's entity XML representation and send it as a request
     * to create it to the dwelling RESTful web service.
     *
     * @param requestEntity The object containing data to be created.
     * @throws ClientErrorException If there is an error while processing. The
     * error is wrapped in a HTTP error response.
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Get a list of dwelling's entities XML representation from the dwelling
     * RESTful web service and return it as a generic type object.
     *
     * @param responseType The Class object of the returning instance.
     * @return A generic type, normally a list, containing the data.
     * @throws ClientErrorException If there is an error while processing. The
     * error is wrapped in a HTTP error response.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException {
        LOGGER.info("GETTING ALL DWELLINGS DATA");
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Send a request to the dwelling RESTful web service to delete a dwelling
     * identified by its id.
     *
     * @param id The id of the dwelling entity to be deleted.
     * @throws ClientErrorException If there is an error while processing. The
     * error is wrapped in a HTTP error response.
     */
    public void remove(Long id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Close RESTful web service client.
     */
    public void close() {
        client.close();
    }

}
