/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author 2dam
 */
public class NeighbourhoodBean {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty postCode;
    
    public NeighbourhoodBean(Integer id, String name, Integer postCode ){
        this.id=new SimpleIntegerProperty(id);
        this.name=new SimpleStringProperty(name);
        this.postCode=new SimpleIntegerProperty(postCode);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleIntegerProperty getPostCode() {
        return postCode;
    }
    
    
}
