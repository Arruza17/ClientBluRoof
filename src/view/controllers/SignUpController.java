/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.ConditionalFeature.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class SignUpController implements Initializable{

    private Stage stage;
    
    @FXML
    private TextField tfUser;   
   
    @FXML
    private void show(ActionEvent evento){
           
            tfUser.setText("sdfsdfsfsdf");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

   
}
