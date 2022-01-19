/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.stage.Stage;
/**
 *
 * @author YERAY
 */
public class GenericMenuController {

    
    private Stage stage;
    
    private BorderPane border;

    public BorderPane getBorder() {
        return border;
    }

    public void setBorder(BorderPane border) {
        this.border = border;
}

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
    
    

    
    
    

}
