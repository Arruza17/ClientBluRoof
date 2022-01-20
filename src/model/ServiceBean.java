/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author 2dam
 */
public class ServiceBean {

    private  SimpleLongProperty id;
    private  SimpleStringProperty type;
    private  SimpleStringProperty address;
    private  SimpleStringProperty name;
    private  SimpleObjectProperty<Neighbourhood> neighbourhood;

    

    public ServiceBean(Service s) {
         this.id = new SimpleLongProperty(s.getId());
        this.type = new SimpleStringProperty(s.getType());
        this.address = new SimpleStringProperty(s.getAddress());
        this.name = new SimpleStringProperty(s.getName());
        this.neighbourhood = new SimpleObjectProperty<Neighbourhood>(s.getNeighborhood());
    }

   
    /**
     *
     * @return id of the service.
     */
    public Long getId() {
        return this.id.get();
    }

    /**
     *
     * @param id service id.
     */
    public void setId(Long id) {
        this.id.set(id);
    }

    /**
     *
     * @return type of service.
     */
    public String getType() {
        return this.type.get();
    }

    /**
     *
     * @param type service type.
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     *
     * @return address of the service.
     */
    public String getAddress() {
        return this.type.get();
    }

    /**
     *
     * @param address service address.
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     *
     * @return name of the service.
     */
    public String getName() {
        return this.name.get();
    }

    /**
     *
     * @param name service name.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     *
     * @return the neighborhood of the service.
     */
    public Neighbourhood getNeighborhood() {
        return this.neighbourhood.get();
    }

    /**
     *
     * @param neighborhood service neighborhood.
     */
    public void setNeighborhood(Neighbourhood neighborhood) {
        this.neighbourhood.set(neighborhood);
    }

    
   
}
