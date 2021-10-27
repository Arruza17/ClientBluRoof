/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

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

/**
 *
 * @author 2dam
 */
public class SignUpController extends Application {

    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());

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
    private Tooltip tooltipEmail;
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

    @Override
    public void start(Stage primaryStage) {
        try {
            String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/SignUp.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            SignUpController controller = ((SignUpController) fxmlLoader.getController());
            controller.setStage(primaryStage);
            controller.initStage(root);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param root
     */
    public void initStage(Parent root) {

        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/view/resources/styles/CSSLogIn.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle("SignUp");
        stage.setResizable(true);
        //Event when the window is started
        //stage.setOnShowing(this::handleWindowShowing);
        //Event when the button is clicked      
        stage.show();

    }

    //ALL THE HANDLERS
    @FXML
    private void signUp(javafx.event.ActionEvent event) {

        try {
            if (checkFields()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Look, a Confirmation Dialog");
                alert.setContentText("Are you ok with this?");

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

    private boolean checkFields() throws FieldsEmptyException, MaxCharactersException, PassNotEqualException, PassMinCharacterException, FullNameGapException {

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
        if(!tfFullName.getText().trim().contains(" ")){
        throw new FullNameGapException();}
        return true;
    }

    /*if (event.getSource().equals(btnCancel)) {
            Platform.exit();
            try {
                stop();
            } catch (Exception ex) {
                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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

    @FXML
    private void cancel(javafx.event.ActionEvent event) {
    
            Platform.exit();
            try {
                stop();
            } catch (Exception ex) {
                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }

}
