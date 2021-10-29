/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import exceptions.EmailFormatException;
import exceptions.FieldsEmptyException;
import exceptions.FullNameGapException;
import exceptions.MaxCharactersException;
import exceptions.PassMinCharacterException;
import exceptions.PassNotEqualException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 * Controller UI class for SignUp view in the user's managements application
 *
 * @author Ander Arruza and Adrián Pérez
 */
public class SignUpController {
    
    private static final Logger LOGGER = Logger.getLogger("package.class");
    public static final Pattern VALID_EMAIL_ADDRESS
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private Stage stage;
    @FXML
    private TextField tfUser;
    @FXML
    private Button btnSignUp;
    @FXML
    private Pane backgroundPane;
    @FXML
    private BorderPane backgroundBorderPane;
    @FXML
    private Pane formPane;
    @FXML
    private HBox lowHbox;
    @FXML
    private ImageView logoImg;
    @FXML
    private Button btnCancel;
    @FXML
    private GridPane formGridPane;
    @FXML
    private Label lblUsername;
    @FXML
    private TextField tfEmail;
    @FXML
    private Label lblPass;
    @FXML
    private PasswordField passField;
    @FXML
    private Tooltip tooltipPass;
    @FXML
    private Label lblRptPass;
    @FXML
    private Label lblFullName;
    @FXML
    private TextField tfFullName;
    @FXML
    private Label lblEmail;
    @FXML
    private Tooltip tooltipUser;
    @FXML
    private PasswordField rptPassword;
    @FXML
    private Tooltip tooltipFullName;
    @FXML
    private Tooltip tooltipRptPass;
    @FXML
    private Tooltip tooltipEmail;

    /**
     * This method is used to initialize the stage
     *
     * @param root The parent object representing root node of the view graph
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing SignUp stage.");
        //Creation of a new Scene
        Scene scene = new Scene(root);
        //Save the route of the .css file
        String css = this.getClass().getResource("/view/resources/styles/CSSLogIn.css").toExternalForm();
        //Sets the .css to the Scene
        scene.getStylesheets().add(css);
        //Sets the scene to the stage
        stage.setScene(scene);
        stage.setTitle("SignUp");
        stage.setResizable(true);
        stage.setOnCloseRequest(this::handleWindowClosing);
        stage.show();

    }

    //ALL THE HANDLERS
    /**
     * This method is used to check that all the data is correct.
     *
     * @return a boolean variable which return true if everything is OK
     * @throws FieldsEmptyException if any of the fields is empty
     * @throws MaxCharactersException if any of the fields are >255
     * @throws PassNotEqualException if the password fields are not the same
     * @throws PassMinCharacterException if the password is <6
     * @throws FullNameGapException if the full name doesn't contain any space
     * character
     * @throws EmailFormatException if the email is not in the valid form
     */
    private boolean checkFields() throws FieldsEmptyException, MaxCharactersException, PassNotEqualException, PassMinCharacterException, FullNameGapException, EmailFormatException {
        //Checks if all the fields are written
        if (tfUser.getText().trim().isEmpty()
                || tfFullName.getText().trim().isEmpty()
                || passField.getText().trim().isEmpty()
                || rptPassword.getText().trim().isEmpty()
                || tfEmail.getText().trim().isEmpty()) {
            //throw validation Error
            throw new FieldsEmptyException();
        }
        //Checks if the fields are >255 characters
        if (tfUser.getText().trim().length() > 255
                || tfFullName.getText().trim().length() > 255
                || passField.getText().trim().length() > 255
                || rptPassword.getText().trim().length() > 255
                || tfEmail.getText().trim().length() > 255) {
            //throw validation Error
            throw new MaxCharactersException();
        }
        //Checks if the password fields are the same
        if (!passField.getText().trim().equals(rptPassword.getText().trim())) {
            //throw validation Error
            throw new PassNotEqualException();
        }
        //Checks if the password fields are <6
        if (passField.getText().trim().length() < 6
                || rptPassword.getText().trim().length() < 6) {
            //throw validation Error
            throw new PassMinCharacterException();
        }
        //Checks if the fullName contains at least one space, in order to know
        //if the user did write the surname
        if (!tfFullName.getText().trim().contains(" ")) {
            //throw validation Error
            throw new FullNameGapException();
        }
        //Checks if the email is ins the correct form
        if (!validateEmail(tfEmail.getText().trim())) {
            //throw validation Error
            throw new EmailFormatException();
        }
        return true;
    }

    /**
     * This method is executed when the signUp button is clicked
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    private void handleSignUpAction(javafx.event.ActionEvent event) {
        User newUser;
        try {
            if (checkFields()) {
                //New Alert in order to ask the user if
                //he really want to add that user
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Sign Up CONFIRMATION");
                alert.setHeaderText("Are you sure you want to add?");
                //Create's an user with the params
                newUser = createUser();
                alert.setContentText("UserName = " + newUser.getLogin()
                        + "\nFullName = " + newUser.getFullName()
                        + "\nEmail = " + newUser.getEmail());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    //ADDING THE USER TO THE DATABASE
                    //NEEDED THE LOGICAL PART
                    
                    //TELLING THE USER THAT EVERYTHING HAD WORK
                    LOGGER.info("New User succesfully added");
                    Alert alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("New User");
                    alert1.setHeaderText(null);
                    alert1.setContentText("User corretly added");
                    alert1.showAndWait();
                } else {
                    Alert alertNotAdded = new Alert(AlertType.WARNING);
                    alertNotAdded.setTitle("User");
                    alertNotAdded.setHeaderText(null);
                    alertNotAdded.setContentText("The user is not registered");
                    alertNotAdded.showAndWait();
                }
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            LOGGER.warning(ex.getClass().getSimpleName() + " exception thrown at signUp method");
        }
    }

    /**
     * This method generates a new user taking all the values of teh fields
     * @return the created User
     */
    private User createUser() {
        User user = User.getUser();
        user.setLogin(tfUser.getText());
        user.setFullName(tfFullName.getText());
        user.setPassword(passField.getText());
        user.setEmail(tfEmail.getText());
        return user;
    }

    /**
     * This method closes the actual window and gets the user back to the
     * SignIn Window
     * @param event
     */
    @FXML
    private void handleCancelAction(javafx.event.ActionEvent event) {
        LOGGER.info("Closing SignUp Window");
        Platform.exit();
    }

    /**
     * This method is executed when the user tries to close the actual window
     * clicking in the close button of the actal window located top right
     * @param e
     */
    private void handleWindowClosing(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            e.consume();
        }
    }

    /**
     * This method validates that the email recived is in the correct form:
     * example = user@domain.com
     *
     * @param email the email recived to check
     * @return a boolean true if everything is OK written
     */
    private boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS.matcher(email);
        return matcher.find();
    }

    //GETTERS AND SETTERS
    /**
     * Gets the stage
     *
     * @return the stage to get
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage
     *
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
