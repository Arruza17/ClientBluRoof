/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author 2dam
 */
public class MainSignUp extends Application {
    
    private Stage stage;
    
    @FXML
    private TextField tfUser;
    
    @Override
    public void start(Stage primaryStage) {
        
        
         try {
            String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/SignUp.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Scene scene = new Scene(root);
     
            scene.getStylesheets().add(css);
            primaryStage.setTitle("");
            primaryStage.setScene(scene);
            primaryStage.show();

            
        } catch (IOException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initStage(Parent root){
     
        Scene scene =new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("SignUp");
        stage.setResizable(true);
        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
        
    }
    
    private void handleWindowShowing(WindowEvent event){
        
        tfUser.setText("sfddsfasdf");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
