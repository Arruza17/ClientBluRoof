package view.controllers;

import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
import exceptions.ServerDownException;
import interfaces.Connectable;
import java.io.IOException;
import static java.lang.Thread.sleep;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.ConnectableFactory;
import model.DataEncapsulator;
import model.User;

/**
 *
 * @author Yeray Sampedro, Jorge Crespo
 */
public class SignInController {

    private Connectable connectable;
    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());
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

    public void initStage(Parent root) {
        LOGGER.info("Initializing SignIn window");
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
        LOGGER.info("SHOWING SignIn window");
    }

    public void signUp(ActionEvent action) {

    }

    public void signIn(ActionEvent action) {
        try {
            LOGGER.info("SignIn method");
            checkEmptyFields();
            DataEncapsulator de = signIn(tfUser.getText().trim(), tfPassword.getText().trim());
            if (de.getException() != null) {
                if (de.getException().getMessage().equalsIgnoreCase("OK")) {
                    welcomeWindow(de.getUser());
                } else {
                    throw de.getException();
                }
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            LOGGER.warning(ex.getClass().getSimpleName() + " exception thrown at SignIn method");
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

    private DataEncapsulator signIn(String login, String pass) throws Exception {
        try {
            openSocket();
            User user = User.getUser();
            user.setLogin(login);
            user.setPassword(pass);
            DataEncapsulator de = connectable.signIn(user);
            if (de.getException() != null && !de.getException().getMessage().equalsIgnoreCase("OK")) {
                LOGGER.warning(de.getException().getClass().getSimpleName() + " exception thrown at SignIn");
                throw de.getException();
            } else {
                LOGGER.info(user.getLogin() + " signed in");
                return de;
            }
        } catch (Exception ex) {
            LOGGER.warning(ex.getClass().getSimpleName() + " exception thrown at SignIn method");
            throw ex;

        }

    }

    private void handleWindowClosing(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to close this window");
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("CLOSING WINDOW");
        } else {
            e.consume();
        }

    }

    private void welcomeWindow(User user) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/fxml/Welcome.fxml"));
            Stage stageWelcome = new Stage();
            WelcomeController controller = new WelcomeController();
            controller.setUser(user);
            controller.setStage(stageWelcome);
            this.stage.close();
            LOGGER.info("Initializing Welcome window and closing SignIn window");

            controller.initStage(root);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error generating the window");
            alert.setContentText(ex.getMessage());
        }
    }

    private void openSocket() throws ServerDownException {
        try {
            LOGGER.info("Opening connection to the server");
            connectable = ConnectableFactory.getConnectable();
        } catch (ServerDownException ex) {
            LOGGER.info(ex.getMessage());
            throw ex;
        }
    }

    //GETTERS AND SETTERS
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setConnectable(Connectable connectable) {
        this.connectable = connectable;
    }

}
