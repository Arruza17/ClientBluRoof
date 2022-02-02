/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import application.Application;
import exceptions.BusinessLogicException;
import factories.DwellingManagerFactory;
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
public class OwnerMenuController extends GenericMenuController {

    //Logger of the classs
    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());

    @FXML
    private HBox menuSignOut;
    @FXML
    private HBox menuExit;
    @FXML
    private HBox menuProfile;
    @FXML
    private HBox menuDwellings;

    /**
     * Usaed to sign out
     *
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
                Logger.getLogger(OwnerMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Used to exit the application
     *
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
     *
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
            Logger.getLogger(OwnerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(OwnerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param event 
     */
    @FXML
    private void handleDwellings(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/OwnerWindow.fxml"));
            Node node = loader.load();
            OwnerWindowController controller = loader.getController();
            controller.setDwellingManager(DwellingManagerFactory.createDwellingManager(DwellingManagerFactory.REST_WEB_CLIENT_TYPE));
            getBorder().setCenter(node);
            controller.setUserId(getUser().getId());
            controller.initStage();
        } catch (IOException ex) {
            Logger.getLogger(OwnerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(OwnerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
