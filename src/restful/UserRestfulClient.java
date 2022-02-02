package restful;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import model.User;

/**
 * Jersey REST client generated for REST resource:UserFacadeREST
 * [entities.user]<br>
 * USAGE:
 * <pre>
 *        UserRESTClient client = new UserRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Yeray Sampedro
 */
public class UserRestfulClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("resources.config").getString("BASE_URI");

    public UserRestfulClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.user");
    }

    /**
     * Method used to call the GET function to reset the password
     *
     * @param <T>
     * @param responseType
     * @param user the login of the person we want to reset the password from
     * @return
     * @throws ClientErrorException if any error happens
     */
    public <T> T resetPassword(GenericType<T> responseType, String user) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("reset/{0}", new Object[]{user}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);

    }

    /**
     * Method used to count all the User entities
     *
     * @return the number of users
     * @throws ClientErrorException if any error happens
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Method used to edit the information about a User
     *
     * @param requestEntity the user itself
     * @param id the id of the user
     * @throws ClientErrorException if any error happens
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), new GenericType<User>() {
                });
    }

    /**
     * Method that finds a User by its id
     * @param <T>
     * @param responseType 
     * @param id the id of the user
     * @return
     * @throws ClientErrorException if any error happens
     */
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Method used to paginate the information
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
     * Method used to create a new User
     *
     * @param requestEntity the user to create
     * @throws ClientErrorException if any error happens
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), new GenericType<User>() {
                });

    }

    /**
     * Finds all the users in the dastabase
     *
     * @param <T>
     * @param responseType
     * @return a list of all the users
     * @throws ClientErrorException if any error happens
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        //Make request and return data from the response
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Method used to delete the information about a user in the database
     *
     * @param id the id of the user to delete
     * @throws ClientErrorException if any error happens
     */
    public void remove(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        //Make request
        resource.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Method to retrieve all the information about a user from all their login
     * information
     *
     * @param <T>
     * @param responseType
     * @param login the user to log in
     * @param password the possible password
     * @return the user
     * @throws ClientErrorException if any error happens
     */
    public <T> T logInUser(GenericType<T> responseType, String login, String password) throws ClientErrorException {
        WebTarget resource = webTarget;
        //Make request
        resource = resource.path(java.text.MessageFormat.format("login/{0}/password/{1}", new Object[]{login, password}));
        //Return data from the response
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Mehod used to change the password of any user
     *
     * @param <T>
     * @param responseType
     * @param user the user to change the password from
     * @param pass the new password
     * @return
     * @throws ClientErrorException if any error happens
     */
    public <T> T changePassword(GenericType<T> responseType, String user, String pass) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("update/{0}/password/{1}", new Object[]{user, pass}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Method that finds all the admins
     *
     * @param <T>
     * @param responseType
     * @return all the admins
     * @throws ClientErrorException if any error happens
     */
    public <T> T findAllAdmins(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("admin");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Method that finds all the admins that contain an specific characters in
     * their login
     *
     * @param <T>
     * @param responseType
     * @param login the word to search for
     * @return a list of all the admins that contain the login field in their
     * login
     * @throws ClientErrorException if any error happens
     */
    public <T> T findAllAdminsByLogin(GenericType<T> responseType, String login) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("admin/{0}", new Object[]{login}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void close() {
        client.close();
    }

}
