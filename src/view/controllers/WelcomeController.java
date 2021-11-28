package view.controllers;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
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
    private MenuController menu;

    @FXML
    private BorderPane mainPane;

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
        maximizeWindow();

        stage.setTitle("BluRoof Welcome Page");
        stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
        stage.setScene(scene);
        stage.setTitle("BluRoof Main");
        stage.setOnCloseRequest(this::handleWindowClosing);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Menu.fxml"));
            Parent topMenu = (Parent) loader.load();
            menu = loader.getController();
            mainPane.setTop(topMenu);
            menu.initStage(mainPane);
        } catch (IOException ex) {
            Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        stage.show();
        LOGGER.info("Welcome window shown");

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

    private void changeCenter(String uri) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(uri));
            Pane view = loader.load();
            view.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
            mainPane.setCenter(view);
        } catch (IOException ex) {
            Logger.getLogger(WelcomeController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void maximizeWindow() {
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bound = screen.getVisualBounds();
        stage.setX(bound.getMinX());
        stage.setY(bound.getMinY());
        stage.setWidth(bound.getWidth());
        stage.setHeight(bound.getHeight());
        stage.setResizable(false);
        stage.setMaximized(true);
    }

}
