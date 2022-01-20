/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 2dam
 */
public class Service implements Serializable {

    private SimpleLongProperty id;
    private SimpleStringProperty type;
    private SimpleStringProperty address;
    private SimpleStringProperty name;
    private SimpleObjectProperty<Neighbourhood> neighbourhood;

    public Service() {

        this.id = new SimpleLongProperty();
        this.type = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.neighbourhood = new SimpleObjectProperty<Neighbourhood>();
    }

    public Service(SimpleLongProperty id, SimpleStringProperty type, SimpleStringProperty address, SimpleStringProperty name, SimpleObjectProperty neighbourhood) {
        this.id = id;
        this.type = type;
        this.address = address;
        this.name = name;
        this.neighbourhood = neighbourhood;

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

    /**
     * Compares two Service objects for equality. This method consider a Service
     * equal to another Service if their id fields have the same value.
     *
     * @param object The other Facility object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares two Service objects for equality. This method consider a Service
     * equal to another Service if their id fields have the same value.
     *
     * @param object The other Service object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", type=" + type + ", address=" + address + ", name=" + name + ", neighborhood=" + neighbourhood + '}';
    }

}
