/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.fxml;

import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 *
 * @author jorge
 */
public class SpecialSpinner<T>extends Spinner{
    @Override
    public void increment(){
    try{
    increment(1);}catch(Exception e){
        System.out.println("Error, only number accepted");}
    }

    
    

   

    

   
    
}
