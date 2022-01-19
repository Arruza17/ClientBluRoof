package view.controllers;

import enumerations.UserPrivilege;
import exceptions.BusinessLogicException;
import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
import exceptions.ServerDownException;
import interfaces.UserManager;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 * Controller UI class for SignIn view in the users managements application.
 *
 * @author Yeray Sampedro, Jorge Crespo, Ander Arruza
 */
public class SignInController {
    
    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());
    
    private UserManager um;
    
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfPassword;
    private Stage stage;
    @FXML
    private Button btnSignIn;
    @FXML
    private Hyperlink hlSignUp;
    @FXML
    private Hyperlink hlPass;

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
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bound = screen.getVisualBounds();
        stage.setX(bound.getMinX());
        stage.setY(bound.getMinY());
        stage.setWidth(bound.getWidth());
        stage.setHeight(bound.getHeight());
        stage.setResizable(false);
        stage.setMaximized(true);
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
    @FXML
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
    @FXML
    public void signIn(ActionEvent action) {
        try {
            //User user = um.login(tfUser.getText(), tfPassword.getText());
            User user = new User();
            user.setPrivilege(tfUser.getText());
            if (user != null) {
                Parent root;
                FXMLLoader loader = null;                
                loader = new FXMLLoader(getClass().getResource("/view/fxml/Welcome.fxml"));
                root = (Parent) loader.load();
                Stage stageWelcome = new Stage();
                WelcomeController controller = ((WelcomeController) loader.getController());
                controller.setStage(stageWelcome);
                controller.setUm(um);
                controller.setUser(user);
                this.stage.close();
                LOGGER.info("Initializing Welcome window and closing SignIn window");
                controller.initStage(root);             
            }
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    @FXML
    private void retrievePassword(ActionEvent event) {
        //Create a textInputDialog to ask the user the login
        TextInputDialog txi = new TextInputDialog();
        txi.setHeaderText("Password reset");
        txi.setContentText("Type the username of the account you would like to reset the password from. In case it exists, you will receive an email with your new resetted password");
        txi.showAndWait();
        String login = txi.getEditor().getText();
        if (!login.isEmpty()) {
            try {
                //Call business logic to reset the password
                um.resetPassword(login);              
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Password reset");
                alert.setContentText("The password was successfully resetted");
                alert.showAndWait();
            } catch (BusinessLogicException e) {
                //Exception catched if there's any issue with the business logic
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error with the reset");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                LOGGER.log(Level.SEVERE, "SignInController --> retrievePassword():{0}", e.getLocalizedMessage());
            }
        } else {
            //If the user doesn't input a login show an error
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Exception ex = new FieldsEmptyException();
            alert.setHeaderText("Input error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            LOGGER.log(Level.SEVERE, "SignInController --> retrievePassword():{0}", ex.getLocalizedMessage());
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
     * Method that gets the Implementation of user manager
     *
     * @return um the user manager
     */
    public UserManager getUm() {
        return um;
    }

    /**
     * Method that sets the UserManager impoementation gotten with the factory
     *
     * @param um
     */
    public void setUm(UserManager um) {
        this.um = um;
    }
    
}
