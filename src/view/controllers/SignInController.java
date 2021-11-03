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
 *Controller UI class for SignIn view in the users managements application.
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
/**
 * Method used to load all stage settings when creating the stage.
 * @param root the parent object represinting root node of the view graph.
 * 
 */
    public void initStage(Parent root){
       
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
    }
    /**
     * Method used to change to SignUp window.
     * @param action  action event that triggers when sign up hyperlink is pressed.
     */
    public void signUp(ActionEvent action) {

       
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("This window is on building, wait for it pedazo de pesao");
        alert.showAndWait();
    }
    /**
     * Method used to Sign In.
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
            logger.warning(ex.getClass().getSimpleName() + " exception thrown at SignIn method");
        }
    }
/**
 * Method used to check for possible errors the user may create.
 * @throws FieldsEmptyException   If one or more  fields are empty this exception is thrown.    
 * @throws MaxCharactersException  If 255 characters are exceeded this exception is thrown.
 */
    private void checkEmptyFields() throws FieldsEmptyException, MaxCharactersException {
        
        //if one or more fields are empty , this method throws a FieldsEmptyException.
        if (tfUser.getText().trim().isEmpty() || tfPassword.getText().trim().isEmpty()) {
            logger.warning("Fields empty");
            throw new FieldsEmptyException();
        }
        //if one or more fields of this window are filled with a string whose length is higher than 255 chars.
        if (tfUser.getText().trim().length() > 255 || tfPassword.getText().trim().length() > 255) {
            logger.warning("Max character length reached");
            throw new MaxCharactersException();
        }
    }
    /**
     * Method used to signin to the server.
     * @param login contains the  login String.
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

        logger.info(login + " login attempt");
    }
/**
 * Method which contains an alert with a choice.
 * @param e  window event representing some type of action.
 * 
 */
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
/**
 * gets the stage.
 * @return  the stage to get.
 */
    public Stage getStage() {
        return stage;
    }
/**
 * sets the stage.
 * @param stage the stage to set.
 */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
