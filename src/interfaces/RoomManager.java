/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author jorge
 */
public interface RoomManager {


    public <T> T findRoomsByNOutlets(Class<T> responseType, String outlets) throws ClientErrorException;

    public <T> T findRoomsWNaturalLight(Class<T> responseType, String naturalLight) throws ClientErrorException;

    public String countREST() throws ClientErrorException;

    public void edit(Object requestEntity, String id) throws ClientErrorException;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;

    public void create(Object requestEntity) throws ClientErrorException;

    public <T> T findAll(Class<T> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

}
