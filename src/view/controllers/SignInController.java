package view.controllers;

import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.stage.Stage;
import model.User;

/**
 *
 * @author Yeray Sampedro, Jorge Crespo
 */
public class SignInController extends Application {

    private static final Logger logger = Logger.getLogger(SignInController.class.getName());
    private final int MAX_WIDTH = 1920;
    private final int MAX_HEIGHT = 1024;
    private final int MIN_WIDTH = 1024;
    private final int MIN_HEIGHT = 768;

    @FXML
    private Hyperlink hlSignUp;

    @FXML
    private Button btnSignIn;

    @FXML
    private TextField tfUser;

    @FXML
    private TextField tfPassword;

    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Initializing SignIn window");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/SignIn.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
            scene.getStylesheets().add(css);

            primaryStage.setMaxWidth(MAX_WIDTH);
            primaryStage.setMinWidth(MIN_WIDTH);
            primaryStage.setMaxHeight(MAX_HEIGHT);
            primaryStage.setMinHeight(MIN_HEIGHT);
            primaryStage.setResizable(false);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BluRoof SignIn Page");
            primaryStage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
            primaryStage.show();

        } catch (IOException ex) {

            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void signUp(ActionEvent action) {
      
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("This window is on building, wait for it pedazo de pesao");
        alert.showAndWait();
    }

    public void signIn(ActionEvent action) {
        try {
            checkEmptyFields();
            signIn(tfUser.getText().trim(), tfPassword.getText().trim());
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            logger.warning(ex.getClass().getSimpleName() + " exception thrown at SignIn method");
        }
    }

    
    
    private void checkEmptyFields() throws FieldsEmptyException, MaxCharactersException {
        if (tfUser.getText().trim().isEmpty() || tfPassword.getText().trim().isEmpty()) {
            throw new FieldsEmptyException();
        }
        if (tfUser.getText().trim().length() > 255 || tfPassword.getText().trim().length() > 255) {
            throw new MaxCharactersException();
        }
    }

    private void signIn(String login, String pass) {
        User user = User.getUser();
        user.setLogin(login);
        user.setPassword(new String(pass));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Login");
        alert.setContentText("Intento de login: " + login + " " + pass);
        alert.showAndWait();

        logger.info(login + " login attempt");
    }

}
