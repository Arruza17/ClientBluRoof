/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author jorge
 */
public class RoomBean {
    private final SimpleBooleanProperty naturalLight;
    private final SimpleIntegerProperty nOutlets;
    private final SimpleIntegerProperty id;

    public RoomBean(Integer nOutlets, Boolean naturalLight, Integer id) {
        this.naturalLight = new SimpleBooleanProperty(naturalLight);
        this.nOutlets = new SimpleIntegerProperty(nOutlets);
        this.id=new SimpleIntegerProperty(id);
}

    public SimpleBooleanProperty getNaturalLight() {
        return naturalLight;
    }

    public SimpleIntegerProperty getnOutlets() {
        return nOutlets;
    }

    public SimpleIntegerProperty getId() {
        return id;
    }
    
}
