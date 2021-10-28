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
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
 *
 * @author 2dam
 */
public class SignUpController {

    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
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

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     *
     * @param root
     */
    public void initStage(Parent root) {
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
    private boolean checkFields() throws FieldsEmptyException, MaxCharactersException, PassNotEqualException, PassMinCharacterException, FullNameGapException, EmailFormatException {

        if (tfUser.getText().trim().isEmpty()
                || tfFullName.getText().trim().isEmpty()
                || passField.getText().trim().isEmpty()
                || rptPassword.getText().trim().isEmpty()
                || tfEmail.getText().trim().isEmpty()) {
            throw new FieldsEmptyException();
        }

        if (tfUser.getText().trim().length() > 255
                || tfFullName.getText().trim().length() > 255
                || passField.getText().trim().length() > 255
                || rptPassword.getText().trim().length() > 255
                || tfEmail.getText().trim().length() > 255) {
            throw new MaxCharactersException();
        }

        if (!passField.getText().equals(rptPassword.getText())) {
            throw new PassNotEqualException();
        }

        if (passField.getText().trim().length() < 6
                || rptPassword.getText().trim().length() < 6) {
            throw new PassMinCharacterException();
        }
        if (!tfFullName.getText().trim().contains(" ")) {
            throw new FullNameGapException();
        }
        if(!validateEmail(tfEmail.getText())){
            throw new EmailFormatException();
        }
        return true;
    }

    @FXML
    private void handleSignUpAction(javafx.event.ActionEvent event) {

        try {
            if (checkFields()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Sign Up CONFIRMATION");
                alert.setHeaderText("Are you sure you want to add?");
                User newUser = createUser();
                alert.setContentText("UserName = "+newUser.getLogin()+
                        "\nFullName = "+newUser.getFullName()+
                        "\nEmail = "+newUser.getEmail());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Alert alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("Information Dialog");
                    alert1.setHeaderText(null);
                    alert1.setContentText("I have a great message for you!");

                    alert1.showAndWait();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }

        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            logger.warning(ex.getClass().getSimpleName() + " exception thrown at signUp method");
        }
    }

    private User createUser() {
        User user = User.getUser();
        user.setLogin(tfUser.getText());
        user.setFullName(tfFullName.getText());
        user.setPassword(passField.getText());
        user.setEmail(tfEmail.getText());
        return user;
    }

    @FXML
    private void handleCancelAction(javafx.event.ActionEvent event) {
    }

    private void handleWindowClosing(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            e.consume();
        }

    }
    //GETTERS AND SETTERS

    /**
     *
     * @return
     */
    public Stage getStage() {
        return stage;
    }

    /**
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

}
