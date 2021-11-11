package view.controllers;

import java.util.Optional;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 * Controller UI class for Welcome view in the users managements application.
 *
 * @author Yeray Sampedro
 */
public class WelcomeController {

    private final int MAX_WIDTH = 1920;
    private final int MAX_HEIGHT = 1024;
    private final int MIN_WIDTH = 1024;
    private final int MIN_HEIGHT = 768;
    private User user;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());

    @FXML
    private Button btnSignOut;
    @FXML
    private Button btnClose;
    @FXML
    private Label lblHi;

    /**
     * Method used to load all stage settings when creating the stage.
     *
     * @param root the parent object represinting root node of the view graph.
     *
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing Welcome window");
        Scene scene = new Scene(root);
        //Adding the css
        String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
        scene.getStylesheets().add(css);
        //Setting up the Max & Mins 
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setResizable(true);
        stage.setTitle("BluRoof Welcome Page");
        stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
        stage.setScene(scene);
        stage.setTitle("BluRoof Main");
        stage.setOnCloseRequest(this::handleWindowClosing);
        stage.show();
        LOGGER.info("Welcome window shown");
        lblHi.setText("Welcome back, " + user.getLogin());

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
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            e.consume();
        } else {
            LOGGER.info("Welcome window closed");
        }
    }

    /**
     * Method executed when the SignOut button is pressed
     *
     * @param e An event representing some type of Action
     */
    @FXML
    private void handleSignOutAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Signing out");
        alert.setContentText("Are you sure you want to sign out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Welcome window closed");
            exit(btnSignOut);

        }
    }

    /**
     * Method executed when the Close button is pressed
     *
     * @param e An event representing some type of Action
     */
    @FXML
    private void handleCloseAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close this window?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Welcome window closed");
            exit(btnClose);

        }
    }

    /**
     * Method that returns the user to the SignIn Window
     *
     * @param btn the Button Pressed
     */
    private void exit(Button btn) {
        try {
            if (btn.getText().toString().equalsIgnoreCase("Sign Out")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/SignIn.fxml"));
                Parent root = (Parent) loader.load();
                SignInController controller = ((SignInController) loader.getController());
                controller.setStage(new Stage());
                controller.initStage(root);
            }
        } catch (Exception ex) {
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setHeaderText("Error generating the window");
            error.setContentText(ex.getMessage());
            error.showAndWait();
        } finally {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
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
     * Sets the user
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        if (user != null) {
            this.user = user;
        }
    }

}
