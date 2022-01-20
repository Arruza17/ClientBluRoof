/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

/**
 *
 * @author jorge
 */
public class FacilityTableBean {
    private SimpleLongProperty id;

    private SimpleObjectProperty<FacilityType> type;

    private SimpleObjectProperty<Date> adqDate;

    private Hyperlink moreInfo;

    public FacilityTableBean(Facility f) {
        this.id= new SimpleLongProperty(f.getId());
        this.type =new SimpleObjectProperty<>(f.getType());
        this.adqDate= new SimpleObjectProperty<>(f.getAdquisitionDate());
       
    }

    public SimpleLongProperty getId() {
        return id;
    }

    public void setId(SimpleLongProperty id) {
        this.id = id;
    }

    public SimpleObjectProperty<FacilityType> getType() {
        return type;
    }

    public void setType(SimpleObjectProperty<FacilityType> type) {
        this.type = type;
    }

    public SimpleObjectProperty<Date> getAdqDate() {
        return adqDate;
    }

    public void setAdqDate(SimpleObjectProperty<Date> adqDate) {
        this.adqDate = adqDate;
    }

    public Hyperlink getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(Hyperlink moreInfo) {
        this.moreInfo = moreInfo;
    }
    
}
