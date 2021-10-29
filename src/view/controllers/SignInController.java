package view.controllers;

import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 *
 * @author Yeray Sampedro, Jorge Crespo
 */
public class SignInController {

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
    private Stage stage;

    public void initStage(Parent root){
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setResizable(false);
        stage.setTitle("BluRoof SignIn Page");
        stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(true);
        stage.setOnCloseRequest(this::handleWindowClosing);
        stage.show();
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

    private void handleWindowClosing(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            e.consume();
        }
        logger.warning("prueba");
    }
//GETTERS AND SETTERS

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
