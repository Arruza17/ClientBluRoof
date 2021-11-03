package view.controllers;

import exceptions.ServerDownException;
import interfaces.Connectable;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.ConnectableFactory;
import model.User;
import view.controllers.SignInController;

/**
 *
 * @author Yeray Sampedro
 */
public class WelcomeController {

    private final int MAX_WIDTH = 1920;
    private final int MAX_HEIGHT = 1024;
    private final int MIN_WIDTH = 1024;
    private final int MIN_HEIGHT = 768;

    private User user;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());

    @FXML
    private Button btnSignOut;
    @FXML
    private Button btnClose;
    @FXML
    private Label lblHi;

    public void initStage(Parent root) {
        LOGGER.info("Initializing Welcome window");
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
        stage.setTitle("Welcome");
        stage.setOnCloseRequest(this::handleWindowClosing);
        stage.show();
        LOGGER.info("Welcome window shown");

    }

    private void handleWindowClosing(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            LOGGER.info("Welcome window closed");
            e.consume();
        }
    }

    @FXML
    private void handleSignOutAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Signing out");
        alert.setContentText("Are you sure you want to sign out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Welcome window closed");
            exit(btnSignOut);

        }
    }

    @FXML
    private void handleCloseAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Welcome window closed");
            exit(btnClose);

        }
    }

    private void exit(Button btn) {
        try {
            if (btn.getText().toString().equalsIgnoreCase("Sign Out")) {
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

    //GETTERS AND SETTERS
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        if (user != null) {
            this.user = user;
        }
    }

}
