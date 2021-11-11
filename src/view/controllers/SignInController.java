package view.controllers;

import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
import exceptions.ServerDownException;
import interfaces.Connectable;
import java.io.IOException;
import java.util.Optional;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.ConnectableFactory;
import model.DataEncapsulator;
import model.User;

/**
 * Controller UI class for SignIn view in the users managements application.
 *
 * @author Yeray Sampedro, Jorge Crespo, Ander Arruza
 */
public class SignInController {

    private Connectable connectable;
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
        //Sets the window not resizable
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
     * Method used to open the SignUp window.
     *
     * @param action action event that triggers when sign up hyperlink is
     * pressed.
     */
    public void signUp(ActionEvent action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/SignUp.fxml"));
            Stage stageSignUp = new Stage();
            Parent root = (Parent) loader.load();
            SignUpController controller = ((SignUpController) loader.getController());
            controller.setStage(stageSignUp);
            stageSignUp.initModality(Modality.APPLICATION_MODAL);
            stageSignUp.initOwner(
                    ((Node) action.getSource()).getScene().getWindow());
            controller.initStage(root);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText("There was a problem creating the window, try again later");
            alert.showAndWait();
            LOGGER.warning(ex.getClass().getSimpleName() + " exception thrown at SignIn method");
        }

    }

    /**
     * Method used to Sign In exectued when the user clicks the signIn button.
     *
     * @param action action event that triggers when sign in button is pressed.
     */
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
            //Depending on the Exception thrown by the server:
            // 1.- User Limit passed
            // 2.- User not found
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
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
     * This method is executed when the user wants to sign In
     *
     * @param login the username to set
     * @param pass the password to set
     * @return the DataEncapsulator object
     * @throws Exception if there is an error
     */
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

    /**
     * Method thrown when the window is trying to be closed which contains an
     * alert with a choice.
     *
     * @param e window event representing some type of action.
     *
     */
    private void handleWindowClosing(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to close this window");
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

    /**
     * This method is executed when the user gets Logged in
     *
     * @param user The user
     */
    private void welcomeWindow(User user) {
        Parent root;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/view/fxml/Welcome.fxml"));
            root = (Parent) loader.load();
            Stage stageWelcome = new Stage();
            WelcomeController controller = ((WelcomeController) loader.getController());
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

    /**
     * This method opens the socket
     *
     * @throws ServerDownException if Server is down
     */
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
    /**
     * The stage to get
     *
     * @return the stage
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

    /**
     * Sets the connectable
     *
     * @param connectable the connectable to set
     */
    public void setConnectable(Connectable connectable) {
        this.connectable = connectable;
    }

}
