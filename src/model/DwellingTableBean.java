/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;

/**
 *
 * @author ander
 */
public class DwellingTableBean {

    private SimpleStringProperty type;

    private SimpleStringProperty address;

    private SimpleBooleanProperty wifi;

    private SimpleDoubleProperty squareMeters;

    private SimpleObjectProperty<Date> constructionDate;

    private SimpleFloatProperty rating;

    private Hyperlink moreInfo;

    public DwellingTableBean(Dwelling d, String type) {
        this.type = new SimpleStringProperty(type);
        this.address = new SimpleStringProperty(d.getAddress());
        this.wifi = new SimpleBooleanProperty(d.getHasWiFi());
        this.squareMeters = new SimpleDoubleProperty(d.getSquareMeters());
        this.constructionDate = new SimpleObjectProperty<>(d.getConstructionDate());
        this.rating = new SimpleFloatProperty(d.getRating());
        Hyperlink hl = new Hyperlink("More info");
        hl.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
              Alert alert = new Alert(AlertType.CONFIRMATION);
              alert.setTitle(address.get());
              alert.show();
              //Generar ventana dwelling
              //ControladorDwelling cd = new ControladorDwelling
              //cd.setDwelling(d)
               
            }
        });
        this.moreInfo = hl ;
      
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public Boolean getWifi() {
        return wifi.get();
    }

    public void setWifi(Boolean wifi) {
        this.wifi.set(wifi);
    }

    public Double getSquareMeters() {
        return squareMeters.get();
    }

    public void setSquareMeters(Double squareMeters) {
        this.squareMeters.set(squareMeters);
    }

    public Date getConstructionDate() {
        return constructionDate.get();
    }

    public void setConstructionDate(Date constructionDate) {
        this.constructionDate.set(constructionDate);
    }

    public Float getRating() {
        return rating.get();
    }

    public void setRating(Float rating) {
        this.rating.set(rating);
    }

    public Hyperlink getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(Hyperlink moreInfo) {
        this.moreInfo = moreInfo;
    }

   

}
