package view.controllers;

import enumerations.ActualState;
import enumerations.UserPrivilege;
import enumerations.UserStatus;
import exceptions.EmailFormatException;
import exceptions.FieldsEmptyException;
import exceptions.FullNameFormatException;
import exceptions.LoginFoundException;
import exceptions.MaxCharactersException;
import exceptions.PasswordFormatException;
import exceptions.PassNotEqualException;
import factories.UserChildFactory;
import interfaces.UserChild;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Guest;
import model.Owner;
import model.User;

/**
 * Controller UI class for SignUp view in the user's managements application
 *
 * @author Ander Arruza and Adrián Pérez
 */
public class SignUpController {

    private final int MAX_WIDTH = 1920;
    private final int MAX_HEIGHT = 1024;
    private final int MIN_WIDTH = 1024;
    private final int MIN_HEIGHT = 768;

    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());

    /**
     * This variable contains the following Pattern in order to get the email
     * well written (format: user@server.domain)
     */
    public static final Pattern VALID_EMAIL_ADDRESS
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private Stage stage;
    @FXML
    private TextField tfUser;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField passField;
    @FXML
    private TextField tfFullName;
    @FXML
    private PasswordField rptPassword;
    @FXML
    private TextField tfPhoneNo;
    @FXML
    private DatePicker dpBdate;
    @FXML
    private ToggleGroup userType;
    @FXML
    private HBox hbChange;

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
        String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
        //Sets the .css to the Scene
        scene.getStylesheets().add(css);
        //Stage dimension setters
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
        //Sets the scene to the stage
        stage.setScene(scene);
        stage.setTitle("SignUp");
        //Sets the window not resizable
        stage.setResizable(false);
        userType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                hbChange.getChildren().clear();
                VBox vbox;
                Label label;
                if (selectedRadioButton.getText().equalsIgnoreCase("Owner")) {
                    label = new Label("Are you going to reside in the house?");
                    CheckBox cb = new CheckBox();
                    vbox = new VBox(label, cb);
                } else {
                    label = new Label("What is your actual situation? ");
                    ObservableList<ActualState> list = FXCollections.observableArrayList(Arrays.asList(ActualState.values()));
                    ComboBox cb = new ComboBox(list);
                    cb.getSelectionModel().selectFirst();
                    vbox = new VBox(label, cb);

                }
                hbChange.getChildren().add(vbox);
            }
        });

        stage.show();
        LOGGER.info("SignUp Open Window");
    }

    //ALL THE HANDLERS
    /**
     * This method is used to check that all the data is correct.
     *
     * @return a boolean variable which return true if everything is OK
     * @throws FieldsEmptyException if any of the fields is empty
     * @throws MaxCharactersException if any of the fields are more than 255
     * characters
     * @throws PassNotEqualException if the password fields are not the same
     * @throws PasswordFormatException if the password have less tha 6
     * characters
     * @throws FullNameFormatException if the full name doesn't contain any
     * space character
     * @throws EmailFormatException if the email is not in the valid form
     */
    private boolean checkFields() throws FieldsEmptyException, MaxCharactersException, PassNotEqualException, PasswordFormatException, FullNameFormatException, EmailFormatException {
        //Checks if all the fields are written
        if (tfUser.getText().trim().isEmpty()
                || tfFullName.getText().trim().isEmpty()
                || passField.getText().trim().isEmpty()
                || rptPassword.getText().trim().isEmpty()
                || tfEmail.getText().trim().isEmpty()
                || tfPhoneNo.getText().trim().isEmpty()
                || dpBdate.getValue().equals(null)
                || userType.getSelectedToggle().equals(null)) {
            //throw validation Error
            LOGGER.warning("Some fields are empty");
            throw new FieldsEmptyException();
        }

        //Checks if the fields have more than 255 characters
        if (tfUser.getText().trim().length() > 255
                || tfFullName.getText().trim().length() > 255
                || passField.getText().trim().length() > 255
                || rptPassword.getText().trim().length() > 255
                || tfEmail.getText().trim().length() > 255
                || tfPhoneNo.getText().trim().length() > 255) {
            //throw validation Error
            LOGGER.warning("Some field/s are more than >255 characters");
            throw new MaxCharactersException();
        }
        //Checks if the password fields are the same
        if (!passField.getText().trim().equals(rptPassword.getText().trim())) {
            //throw validation Error
            LOGGER.warning("The field passField and rptPassword are not the same");
            throw new PassNotEqualException();

        }
        //Checks if the password field has an space within
        if (passField.getText().trim().contains(" ")) {
            //throw validation Error
            LOGGER.warning("Password contains an space");
            throw new PasswordFormatException();
        }

        //Checks if the password fields are less than 6 characters
        if (passField.getText().trim().length() < 6
                || rptPassword.getText().trim().length() < 6) {
            //throw validation Error
            LOGGER.warning("The password has -6 characters");
            throw new PasswordFormatException(rptPassword.getText().trim().length());
        }
        //Checks if the fullName contains at least one space
        if (!tfFullName.getText().trim().contains(" ")) {
            LOGGER.warning("The surmane is not written");
            //throw validation Error
            throw new FullNameFormatException();
        }
        //Checks if the email is in the correct format
        if (!validateEmail(tfEmail.getText().trim())) {
            LOGGER.warning("The email is not in the correct format");
            //throw validation Error
            throw new EmailFormatException();
        }
        //Reachable if everything goes OK
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
                LOGGER.info("All the fields are OK");
                //New Alert in order to ask the user if
                //he really want to add that user
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Sign Up CONFIRMATION");
                alert.setHeaderText("Are you sure you want to add?");
                //Create's an user with the params
                RadioButton selectedRadioButton = (RadioButton) userType.getSelectedToggle();
                User user;

                if (selectedRadioButton.getText().equals("Owner")) {
                    user = new Owner();
                    ObservableList<Node> obs = hbChange.getChildren();
                    VBox vb = (VBox) obs.get(0);
                    ObservableList<Node> children = vb.getChildren();
                    CheckBox cb = (CheckBox) children.get(children.size() - 1);
                    ((Owner) user).setIsResident(cb.isSelected());

                } else {
                    user = new Guest();
                    ObservableList<Node> obs = hbChange.getChildren();
                    VBox vb = (VBox) obs.get(0);
                    ObservableList<Node> children = vb.getChildren();
                    ComboBox cb = (ComboBox) children.get(children.size() - 1);
                    ((Guest) user).setActualState(ActualState.valueOf(cb.getSelectionModel().getSelectedItem().toString()));

                }
                user.setLogin(tfUser.getText().toString().trim());
                user.setFullName(tfFullName.getText().toString().trim());
                user.setPassword(passField.getText().toString().trim());
                user.setEmail(tfEmail.getText().toString().trim());
                user.setPrivilege(selectedRadioButton.getText());
                user.setStatus(UserStatus.ENABLED.name());
                String content = "UserName = " + user.getLogin()
                        + "\nFullName = " + user.getFullName()
                        + "\nEmail = " + user.getEmail();
                String extra = (user instanceof Owner ? "\nIsResident: " + ((Owner) user).getIsResident() : "\nActualState: " + ((Guest) user).getActualState().name());
                alert.setContentText(content + extra);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    //ADDING THE USER TO THE DATABASE
                    UserChild uc = UserChildFactory.createUserChild(UserChildFactory.REST_WEB_CLIENT_TYPE);
                    uc.register(user);
                    //TELLING THE USER THAT EVERYTHING HAD WORK
                    LOGGER.info("New User succesfully added");
                    //Alert alert1 = new Alert(AlertType.INFORMATION);
                    //alert1.setTitle("New User");
                    //alert1.setHeaderText(null);
                    //alert1.setContentText("User corretly added");
                    //alert1.showAndWait();
                    //LOGGER.info("Closing SignUp Window");
                    //Stage stage = (Stage) btnCancel.getScene().getWindow();
                    // Close current window 
                    stage.close();

                }
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            LOGGER.warning(ex.getClass().getSimpleName() + " exception thrown at signUp method");
            if (ex.getMessage().equals(new LoginFoundException().getMessage())) {
                tfUser.requestFocus();
            } else if (ex.getMessage().equals(new PassNotEqualException().getMessage())) {
                passField.requestFocus();
            }
        }
    }

    /**
     * This method closes the actual window and gets the user back to the SignIn
     * Window
     *
     * @param event An Event representing some type of action
     */
    @FXML
    private void handleCancelAction(javafx.event.ActionEvent event) {
        LOGGER.info("Closing SignUp Window");
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Close current window 
        stage.close();
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
