package restful;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import model.Guest;

/**
 * Jersey REST client generated for REST resource:GuestFacadeREST
 * [entities.guest]<br>
 * USAGE:
 * <pre>
 *        GuestRestfulClient client = new GuestRestfulClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Yeray Sampedro
 */
public class GuestRestfulClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("resources.config").getString("URL");

    public GuestRestfulClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.guest");
    }

    /**
     * Method used to count all the Guest entities
     *
     * @return the number of Guest
     * @throws ClientErrorException if any error happens
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Method used to edit the information about a Guest
     *
     * @param requestEntity the Guest itself
     * @param id the id of the Guest
     * @throws ClientErrorException if any error happens
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Method that finds a Guest by its id
     *
     * @param <T>
     * @param responseType
     * @param id the id of the Guest
     * @return
     * @throws ClientErrorException if any error happens
     */
    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Method used to paginate the information
     *
     * @param <T>
     * @param responseType
     * @param from the first index
     * @param to the last index
     * @return a list of the paginated information
     * @throws ClientErrorException if any error happens
     */
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Method used to create a new Guest
     *
     * @param requestEntity the user to create
     * @throws ClientErrorException if any error happens
     */
    public void create(Guest requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Finds all the guests in the dastabase
     *
     * @param <T>
     * @param responseType
     * @return a list of all the users
     * @throws ClientErrorException if any error happens
     */
    public <T> T findAll(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Method used to delete the information about a guest in the database
     *
     * @param id the id of the user to delete
     * @throws ClientErrorException if any error happens
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     *
     */
    public void close() {
        client.close();
    }

}
