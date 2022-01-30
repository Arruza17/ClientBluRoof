/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.*;
/**
 *
 * @author jorge
 */
public class FlatFacilityBean {
     private final SimpleIntegerProperty id;
     private final SimpleObjectProperty<Flat> flat;
     private final SimpleObjectProperty<Facility> facility;
     private final SimpleStringProperty facilityCondition;

    public FlatFacilityBean(Integer id, Flat flat,Facility facility,String facilityCondition) {
        this.id = new SimpleIntegerProperty(id);
        this.flat = new SimpleObjectProperty<Flat>(flat);
        this.facility=new SimpleObjectProperty<Facility>(facility);
        this.facilityCondition= new SimpleStringProperty(facilityCondition);
    }

    public SimpleIntegerProperty getId(){
        return id;
    }

    public SimpleObjectProperty<Flat> getFlat(){
        return flat;
    }

    public SimpleObjectProperty<Facility> getFacility(){
        return facility;
    }

    public SimpleStringProperty getFacilityCondition(){
        return facilityCondition;
    }
     
     
}
