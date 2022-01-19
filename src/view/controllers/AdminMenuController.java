/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import factories.UserManagerFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javax.naming.OperationNotSupportedException;

/**
 * FXML Controller class
 *
 * @author YERAY
 */
public class AdminMenuController extends GenericMenuController {

    @FXML
    private HBox menuAdminCrud;
    @FXML
    private HBox menuServiceCrud;
    @FXML
    private HBox menuFacilitiesCrud;
    @FXML
    private HBox menuSignOut;
    @FXML
    private Label menuExit;

    @FXML
    private void handleAdminCrud(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Admins.fxml"));
            BorderPane node = loader.load();
            AdminController controller = loader.getController();
            controller.setUserManager(UserManagerFactory.createUsersManager(UserManagerFactory.REST_WEB_CLIENT_TYPE));
            controller.setStage(getStage());
            getBorder().setCenter(node);
            controller.initStage();

        } catch (IOException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleServiceCrud(MouseEvent event) {
    }

    @FXML
    private void handleFacilitiesCrud(MouseEvent event) {
    }

    @FXML
    private void handleSignOut(MouseEvent event) {
    }

    @FXML
    private void handleExit(MouseEvent event) {
    }

}
