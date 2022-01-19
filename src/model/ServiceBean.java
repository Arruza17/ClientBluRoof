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

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty type;
    private final SimpleStringProperty address;
    private final SimpleStringProperty name;
    private final SimpleObjectProperty<Neighbourhood> neighbourhood;

    public ServiceBean(Integer id, String type, String address, String name, Neighbourhood neighbourhood) {
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.address = new SimpleStringProperty(address);
        this.name = new SimpleStringProperty(name);
        this.neighbourhood = new SimpleObjectProperty<Neighbourhood>(neighbourhood);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public SimpleStringProperty getType() {
        return type;
    }

    public SimpleStringProperty getAddress() {
        return address;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleObjectProperty<Neighbourhood> getNeighbourhood() {
        return neighbourhood;
    }
}
