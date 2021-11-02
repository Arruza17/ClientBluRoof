package view.controllers;

import exceptions.ServerDownException;
import interfaces.Connectable;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 *
 * @author Yeray Sampedro
 */
public class WelcomeController {

    private Connectable connectable;
    private static final Logger logger = Logger.getLogger(WelcomeController.class.getName());
    private final int MAX_WIDTH = 1920;
    private final int MAX_HEIGHT = 1024;
    private final int MIN_WIDTH = 1024;
    private final int MIN_HEIGHT = 768;

    private User user;

    @FXML
    private Label lblHi;

    private Stage stage;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
        scene.getStylesheets().add(css);
       
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setResizable(false);
        
        stage.setTitle("BluRoof Welcome Page");
        stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(true);
        stage.setOnCloseRequest(this::handleWindowClosing);
        lblHi.setText(lblHi.getText() + " " + user.getLogin());
        stage.show();
    }

    private void handleWindowClosing(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            User user = null;
            try {
                connectable.signIn(user);
            } catch (ServerDownException ex) {
                Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                e.consume();
            }
        }

    }
//GETTERS AND SETTERS

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
