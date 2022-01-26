/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.SimpleDateFormat;
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
 * @author Ander Arruza
 */
public class DwellingTableBean {

    private Long id;

    private SimpleStringProperty address;

    private SimpleBooleanProperty wifi;

    private SimpleDoubleProperty squareMeters;

    private SimpleStringProperty constructionDate;

    private SimpleFloatProperty rating;

    private Hyperlink moreInfo;

    public DwellingTableBean(Dwelling d) {
        this.id = d.getId();
        this.address = new SimpleStringProperty(d.getAddress());
        this.wifi = new SimpleBooleanProperty(d.getHasWiFi());
        this.squareMeters = new SimpleDoubleProperty(d.getSquareMeters());
        //PASAR DE Tue Dec 14 00:00:00 CET 2021 A 14/12/2021
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateBueno = format.format(d.getConstructionDate());
        this.constructionDate = new SimpleStringProperty(dateBueno);
        this.rating = new SimpleFloatProperty(d.getRating());
        Hyperlink hl = new Hyperlink("More info");
        hl.setOnAction(new EventHandler<ActionEvent>() {
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
        this.moreInfo = hl;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getConstructionDate() {
        return constructionDate.get();
    }

    public void setConstructionDate(String constructionDate) {
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

    public SimpleBooleanProperty wiFiProperty() {
        return this.wifi;
    }

}
