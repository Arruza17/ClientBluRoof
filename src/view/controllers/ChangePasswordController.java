package view.controllers;

import exceptions.BusinessLogicException;
import exceptions.FieldsEmptyException;
import exceptions.PassNotEqualException;
import exceptions.PasswordFormatException;
import factories.UserManagerFactory;
import interfaces.UserManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import model.User;

/**
 * Password change fxml handler
 *
 * @author Yeray Sampedro
 */
public class ChangePasswordController {

    //Logger of the class
    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());

    //The user to change tha password from
    private User user;

    private Stage stage;
    @FXML
    private PasswordField tfCurPas;
    @FXML
    private PasswordField tfNewPass;
    @FXML
    private PasswordField tfRptNewPass;
    @FXML
    private Button btnUpdate;

    /**
     * Initializer of the stage
     *
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing ChangePassword stage.");
        //Creation of a new Scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        LOGGER.info("Change pass window open");
        stage.show();

    }

    /**
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method used to handle the password change
     *
     * @param event
     */
    @FXML
    private void handlePasswordChange(ActionEvent event) {
        try {
            //Control if any of the fields are empty
            if (tfCurPas.getText().trim().isEmpty() || tfNewPass.getText().trim().isEmpty() || tfRptNewPass.getText().trim().isEmpty()) {
                throw new FieldsEmptyException();
            }
            if (tfNewPass.getText().trim().contains(" ") || tfRptNewPass.getText().trim().contains(" ")) {
                throw new PasswordFormatException();
            }
            if (tfNewPass.getText().trim().length() < 6) {
                throw new PasswordFormatException(tfNewPass.getText().trim().length());
            }
            if (tfRptNewPass.getText().trim().length() < 6) {
                throw new PasswordFormatException(tfRptNewPass.getText().trim().length());
            }
            if (!tfRptNewPass.getText().trim().equals(tfNewPass.getText().trim())) {
                throw new PassNotEqualException();
            }

            UserManager um = UserManagerFactory.createUsersManager(UserManagerFactory.REST_WEB_CLIENT_TYPE);
            //Login to check if the password is correct
            user = um.login(user.getLogin(), tfCurPas.getText().trim());
            //Update the pasword
            um.changePassword(user.getLogin(), tfRptNewPass.getText().trim());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Password successfully changed");
            alert.setTitle("Password change");
            alert.showAndWait();
            this.stage.close();
        } catch (PasswordFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.setTitle("Password format error");
            alert.show();
            LOGGER.log(Level.INFO, null, e);
        } catch (FieldsEmptyException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.setTitle("Error with the empty fields");
            alert.show();
            LOGGER.log(Level.INFO, null, e);
        } catch (PassNotEqualException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.setTitle("Passwords do not match");
            alert.show();
            LOGGER.log(Level.INFO, null, e);
        } catch (BusinessLogicException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.setTitle("Error with the server");
            alert.show();
            LOGGER.log(Level.SEVERE, null, e);
        } catch (OperationNotSupportedException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.setTitle("Error with the connection");
            alert.show();
            LOGGER.log(Level.WARNING, null, e);
        }

    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
