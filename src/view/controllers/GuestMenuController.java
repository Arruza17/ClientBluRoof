/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author YERAY
 */
public class GuestMenuController extends GenericMenuController {

    @FXML
    private HBox menuAdminCrud;
    @FXML
    private HBox menuSignOut;
    @FXML
    private Label menuExit;

    /**
     * Initializes the controller class.
     * @param root
     */
   
    public void initStage(Parent root) {
        // TODO
    }    
    
}
