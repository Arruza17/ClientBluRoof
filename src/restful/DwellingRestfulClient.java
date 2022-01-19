/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import interfaces.DwellingManager;
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
    private static final String BASE_URI = "http://localhost:37302/ServerBluRoof/webresources";
    private static final Logger LOGGER = Logger.getLogger("DwellingRestfulClient");

    /**
     * Construct a DwellingRestfulClient. It creates a RESTful web client and establishes
     * the path of the WebTarget object associated to the client.
     */
    public DwellingRestfulClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.dwelling");
    }

    /**
     *
     * @return
     * @throws ClientErrorException
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public <T> T findByMinConstructionDate(GenericType<T> responseType, String date) throws ClientErrorException {
        LOGGER.info("GETTING ALL DWELLINGS DATA BY MIN CONSTRUCTION DATE");
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("minConstructionDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public <T> T findByMinRating(GenericType<T> responseType, String rate) throws ClientErrorException {
        LOGGER.info("GETTING ALL DWELLINGS DATA BY MIN RATING");
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("minRate/{0}", new Object[]{rate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException {
        LOGGER.info("GETTING ALL DWELLINGS DATA");
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public void close() {
        client.close();
    }
    
}
