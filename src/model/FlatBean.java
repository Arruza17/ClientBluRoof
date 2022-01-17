/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

/**
 *
 * @author jorge
 */
public class FlatBean {
    private final SimpleIntegerProperty nRooms;
    private final SimpleIntegerProperty id;
    private final SimpleListProperty<FlatFacility> list;

    public FlatBean(Integer nRooms, Integer id, List<FlatFacility> list) {
        this.nRooms =new SimpleIntegerProperty(nRooms);
        this.id=new SimpleIntegerProperty(id);
        this.list =new SimpleListProperty<FlatFacility>((ObservableList<FlatFacility>) list);
    }

    public SimpleIntegerProperty getnRooms() {
        return nRooms;
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public SimpleListProperty<FlatFacility> getList() {
        return list;
    }
    
}
