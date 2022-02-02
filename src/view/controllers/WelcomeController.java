package view.controllers;

import interfaces.UserManager;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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

    private User user;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(WelcomeController.class.getName());

    private VBox menuNode;

    private UserManager um;
    @FXML
    private BorderPane bpMain;
    @FXML
    private ImageView imgIcon;
    @FXML
    private ImageView imgName;

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
        //Setting up the size
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bound = screen.getVisualBounds();
        stage.setX(bound.getMinX());
        stage.setY(bound.getMinY());
        stage.setWidth(bound.getWidth());
        stage.setHeight(bound.getHeight());
        stage.setResizable(false);
        stage.setMaximized(true);

        stage.setTitle("BluRoof Welcome Page");
        stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
        stage.setScene(scene);
        stage.setTitle("BluRoof Main");
        stage.setOnCloseRequest(this::handleWindowClosing);
        stage.show();
        LOGGER.info("Welcome window shown");
        try {
            if (!user.getPrivilege().equals("GUEST")) {
                FXMLLoader loader = new MenuFactory().getMenu(user.getPrivilege());
                if (loader != null) {
                    menuNode = loader.load();
                    GenericMenuController menu = loader.getController();
                    menu.setUser(user);
                    menu.setStage(stage);
                    bpMain.setLeft(menuNode);
                    menu.setBorder(getBpMain());
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("There was an issue loading the menu information");
                    alert.setTitle("Problems with the menu");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Under development");
                alert.setContentText("The login of guests is still under development, try again in a few days."
                        + "\n Thus, the application will be closed");
                alert.showAndWait();
                stage.close();
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

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

    @FXML
    private void handleMenu(MouseEvent event) {
        //variable = Expression1 ? Expression2: Expression3
        if (bpMain.getLeft() == null) {
            bpMain.setLeft(menuNode);
        } else {
            bpMain.setLeft(null);

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

    /**
     * Method that gets the implementaiton of the user manager
     *
     * @return the implementaiton of the user manager
     */
    public UserManager getUm() {
        return um;
    }

    /**
     * Method that sets the implementaiton of the user manager
     *
     * @param um the implementaiton of the user manager
     */
    public void setUm(UserManager um) {
        this.um = um;
    }

    /**
     *
     * @return
     */
    public BorderPane getBpMain() {
        return bpMain;
    }

}
