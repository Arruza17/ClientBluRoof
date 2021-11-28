package view.controllers;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 * Controller UI class for Welcome view in the users managements application.
 *
 * @author Yeray Sampedro
 */
public class DropDownController {


    private Stage stage;

    @FXML
    private HBox btnSignOut;
    @FXML
    private HBox btnClose;
    @FXML
    private HBox btnProfile;
    @FXML
    private HBox btnDwellings;

    
    private BorderPane mainPane;
    private int selected;

    /**
     * Method used to load all stage settings when creating the stage.
     *
     * @param root the parent object represinting root node of the view graph.
     *
     */
    public void initStage(Parent root) {    
        mainPane = (BorderPane) root;
        //Adding the css
        String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();     
        selected = 0;

    }

    @FXML
    private void handleProfile(MouseEvent event) {
        changeCenter("/view/fxml/Profile.fxml");
        if (selected == 2) {
            btnDwellings.getStyleClass().remove("selectedMenu");
            btnDwellings.getStyleClass().add("unselectedMenu");
        }
        selected = 1;
        btnProfile.getStyleClass().add("selectedMenu");
        btnProfile.getStyleClass().remove("unselectedMenu");
    }

    @FXML
    private void handleDwellings(MouseEvent event) {
        changeCenter("/view/fxml/Dwellings.fxml");

        if (selected == 1) {
            btnProfile.getStyleClass().remove("selectedMenu");
            btnProfile.getStyleClass().add("unselectedMenu");
        }
        selected = 2;
        btnDwellings.getStyleClass().add("selectedMenu");
        btnDwellings.getStyleClass().remove("unselectedMenu");

    }

 

    /**
     * Method executed when the SignOut button is pressed
     *
     * @param e An event representing some type of Action
     */
    @FXML
    private void handleSignOutAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Signing out");
        alert.setContentText("Are you sure you want to sign out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {         
            exit(btnSignOut);
        }
    }

    /**
     * Method executed when the Close button is pressed
     *
     * @param e An event representing some type of Action
     */
    @FXML
    private void handleCloseAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {      
            exit(btnClose);

        }
    }

    /**
     * Method that returns the user to the SignIn Window
     *
     * @param btn the Button Pressed
     */
    private void exit(HBox btn) {
        try {
            if (btn.getId().toString().contains("Sign Out")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/SignIn.fxml"));
                Parent root = (Parent) loader.load();
                SignInController controller = ((SignInController) loader.getController());
                controller.setStage(new Stage());
                controller.initStage(root);
            }
        } catch (Exception ex) {
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setHeaderText("Error generating the window");
            error.setContentText(ex.getMessage());
            error.showAndWait();
        } finally {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        }
    }

 

    private void changeCenter(String uri) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(uri));
            Pane view = loader.load();
            mainPane.setCenter(view);
        } catch (IOException ex) {
            Logger.getLogger(WelcomeController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    

}
