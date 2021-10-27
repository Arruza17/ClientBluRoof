/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
        btnSignUp.setOnAction(this::handleButtonPressed);
        btnCancel.setOnAction(this::handleButtonPressed);
        stage.show();

    }

    //ALL THE HANDLERS
    @FXML
    private void handleButtonPressed(javafx.event.ActionEvent event) {
        // Alert alert = new Alert(Alert.AlertType.ERROR);
        //alert.showAndWait();
        if (event.getSource().equals(btnSignUp)) {
            try {
                if (tfUser.getText().trim().isEmpty()
                        || tfFullName.getText().trim().isEmpty()
                        || passField.getText().trim().isEmpty())
                        {
                    throw new Exception();
                }
                //if()
            } catch (Exception e) {

            }
        } else if (event.getSource().equals(btnCancel)) {
            Platform.exit();
            try {
                stop();
            } catch (Exception ex) {
                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

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

}
