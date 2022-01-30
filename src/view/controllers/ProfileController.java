/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import enumerations.UserPrivilege;
import exceptions.BusinessLogicException;
import exceptions.FieldsEmptyException;
import interfaces.UserManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

/**
 * 
 *
 * @author Yeray Sampedro
 */
public class ProfileController {

    private static final Logger LOGGER = Logger.getLogger(WelcomeController.class.getName());

    private UserManager userManager;

    private User user;

    @FXML
    private Label lblName;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfEmail;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private TextField tfPhoneNo;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnChangePass;
    @FXML
    private ImageView userImg;

    public void initStage() {
        lblName.setText(user.getFullName());
        tfLogin.setText(user.getLogin());
        tfEmail.setText(user.getEmail());
        dpBirthDate.setValue(user.getBirthDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        tfPhoneNo.setText(user.getPhoneNumber());
        switch (user.getPrivilege()) {
            case "ADMIN":
                userImg.setImage(new Image("/view/resources/img/profile/admin.png"));
                break;
            case "GUEST":
                userImg.setImage(new Image("/view/resources/img/profile/guest.png"));
                break;
            case "HOST":
                userImg.setImage(new Image("/view/resources/img/profile/host.png"));
                break;

        }

    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void handleEditAction(ActionEvent event) {
        try {
            if (tfLogin.getText().trim().isEmpty() || tfEmail.getText().trim().isEmpty() || tfPhoneNo.getText().trim().isEmpty() || dpBirthDate.getValue() == null) {
                throw new FieldsEmptyException();
            }
            user.setEmail(tfEmail.getText().trim());
            user.setLogin(tfLogin.getText().trim());
            user.setPhoneNumber(tfPhoneNo.getText().trim());
            user.setBirthDate(Date.valueOf(dpBirthDate.getValue()));
            userManager.updateUser(user);
        } catch (FieldsEmptyException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.setTitle("Fields Empty");
            alert.show();
            LOGGER.log(Level.SEVERE, null, e);
        } catch (BusinessLogicException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.setTitle("Error with the server");
            alert.show();
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    /**
     * Method used to handle the password change
     * @param event 
     */
    @FXML
    private void handlePasswordChange(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/ChangePassword.fxml"));
            Stage stage = new Stage();
            Parent root = (Parent) loader.load();
            ChangePasswordController controller = ((ChangePasswordController) loader.getController());
            controller.setUser(user);
            controller.setStage(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

}
