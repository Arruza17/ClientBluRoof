/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import application.Application;
import exceptions.BusinessLogicException;
import factories.FacilityManagerFactory;
import factories.ServiceManagerFactory;
import factories.UserManagerFactory;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import model.User;

/**
 * Controller of the Admin menu
 *
 * @author Yeray Sampedro
 */
public class AdminMenuController extends GenericMenuController {

    //Logger of the classs
    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());

    //Each of the windows
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
    private HBox menuProfile;
    
    /**
     * Used to generate the AdminCrud window
     * @param event 
     */
    @FXML
    private void handleAdminCrud(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Admins.fxml"));
            BorderPane node = loader.load();
            AdminController controller = loader.getController();
            controller.setUserManager(UserManagerFactory.createUsersManager(UserManagerFactory.REST_WEB_CLIENT_TYPE));
            controller.setUser(getUser());
            getBorder().setCenter(node);
            controller.initStage();

        } catch (IOException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

      /**
     * Used to generate the Service window
     * @param event 
     */
    @FXML
    private void handleServiceCrud(MouseEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Services.fxml"));
            BorderPane node = loader.load();
            ServicesController controller = loader.getController();           
            controller.setServiceManager(ServiceManagerFactory.createServiceManager(ServiceManagerFactory.REST_WEB_CLIENT_TYPE));
            getBorder().setCenter(node);
            controller.initStage();

        } catch (IOException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

      /**
     * Used to generate the Facilities window
     * @param event 
     */
    @FXML
    private void handleFacilitiesCrud(MouseEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Facilities.fxml"));
            BorderPane node = loader.load();
            FacilitiesController controller = loader.getController();           
            controller.setFacMan(FacilityManagerFactory.createFacilityManager(FacilityManagerFactory.REST_WEB_CLIENT_TYPE));
            getBorder().setCenter(node);
            controller.initStage();

        } catch (IOException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Usaed to sign out 
     * @param event 
     */
    @FXML
    private void handleSignOut(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Signing out");
        alert.setContentText("You are about to sign out of the application. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                LOGGER.info("User signed out");
                getStage().close();
                new application.Application().start(new Stage());           
            } catch (Exception ex) {
                Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Used to exit the application
     * @param event 
     */
    @FXML
    private void handleExit(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Signing out");
        alert.setContentText("You are about to sign out of the application. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("User exited");
            getStage().close();
        }
    }

    /**
     * Used to handle the profile window
     * @param event 
     */
    @FXML
    private void handleProfile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Profile.fxml"));
            Node node = loader.load();
            ProfileController controller = loader.getController();
            controller.setUserManager(UserManagerFactory.createUsersManager(UserManagerFactory.REST_WEB_CLIENT_TYPE));
            controller.setUser(getUser());
            getBorder().setCenter(node);
            controller.initStage();
        } catch (IOException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(AdminMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
