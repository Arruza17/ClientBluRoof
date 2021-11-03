package view.controllers;

import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 * Controller UI class for SignIn view in the users managements application.
 *
 * @author Yeray Sampedro, Jorge Crespo
 */
public class SignInController {

    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());
    private final int MAX_WIDTH = 1920;
    private final int MAX_HEIGHT = 1024;
    private final int MIN_WIDTH = 1024;
    private final int MIN_HEIGHT = 768;

    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfPassword;
    private Stage stage;
    @FXML
    private PasswordField passField;
    @FXML
    private TextField tfFullName;
    @FXML
    private PasswordField rptPassword;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSignUp;

    /**
     * Method used to load all stage settings when creating the stage.
     *
     * @param root the parent object represinting root node of the view graph.
     *
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing SignIn stage.");
        Scene scene = new Scene(root);
        //CSS load
        String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
        scene.getStylesheets().add(css);
        //Stage dimension setters
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        //sets the window as not resizable
        stage.setResizable(false);
        stage.setTitle("BluRoof SignIn Page");
        //Gets the icon of the window.
        stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
        stage.setScene(scene);
        stage.setTitle("SignIn");
        //Close request handler declaration
        stage.setOnCloseRequest(this::handleWindowClosing);
        stage.show();
        LOGGER.info("SignIn Open Window");
    }

    /**
     * Method used to opern the SignUp window.
     *
     * @param action action event that triggers when sign up hyperlink is
     * pressed.
     */
    public void signUp(ActionEvent action) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/fxml/SignUp.fxml"));
            Stage stageSignUp = new Stage();
            SignUpController controller = new SignUpController();
            controller.setStage(stageSignUp);
            stageSignUp.initModality(Modality.WINDOW_MODAL);
            stageSignUp.initOwner(
                    ((Node) action.getSource()).getScene().getWindow());
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Method used to Sign In.
     *
     * @param action action event that triggers when sign in button is pressed.
     */
    public void signIn(ActionEvent action) {

        //In this try we have methods that catch different types of exceptions
        try {
            //Checks all the fields of signIn window and throws a the type of exception when is required.
            checkEmptyFields();
            //Signs in the user and opens the welcome window.
            signIn(tfUser.getText().trim(), tfPassword.getText().trim());

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            LOGGER.warning(ex.getClass().getSimpleName() + " exception thrown at SignIn method");
        }
    }

    /**
     * Method used to check for possible errors the user may create.
     *
     * @throws FieldsEmptyException If one or more fields are empty this
     * exception is thrown.
     * @throws MaxCharactersException If 255 characters are exceeded this
     * exception is thrown.
     */
    private void checkEmptyFields() throws FieldsEmptyException, MaxCharactersException {

        //if one or more fields are empty , this method throws a FieldsEmptyException.
        if (tfUser.getText().trim().isEmpty() || tfPassword.getText().trim().isEmpty()) {
            LOGGER.warning("Fields empty");
            throw new FieldsEmptyException();
        }
        //if one or more fields of this window are filled with a string whose length is higher than 255 chars.
        if (tfUser.getText().trim().length() > 255 || tfPassword.getText().trim().length() > 255) {
            LOGGER.warning("Max character length reached");
            throw new MaxCharactersException();
        }
    }

    /**
     * Method used to signin to the server.
     *
     * @param login contains the login String.
     * @param pass contains the password String.
     */
    private void signIn(String login, String pass) {
        User user = User.getUser();
        user.setLogin(login);
        user.setPassword(new String(pass));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Login");
        alert.setContentText("Intento de login: " + login + " " + pass);
        alert.showAndWait();

        LOGGER.info(login + " login attempt");
    }

    /**
     * Method thrown when the window is trying to be closed
     * which contains an alert with a choice.
     *
     * @param e window event representing some type of action.
     *
     */
    private void handleWindowClosing(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        // If the button is Ok, it consumes the handleWindowClosing event,
        // so it won't do it and if not, it closes the window
        if (ButtonType.OK != result.get()) {
            e.consume();
        } else {
            LOGGER.info("SignIn Window is closed");
        }
    }
//GETTERS AND SETTERS

    /**
     * Gets the stage.
     *
     * @return the stage to get.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage the stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
    }

    @FXML
    private void handleSignUpAction(ActionEvent event) {
    }

}
