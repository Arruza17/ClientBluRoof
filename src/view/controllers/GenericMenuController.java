package view.controllers;

import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.User;

/**
 * Parent class of the menus
 *
 * @author Yeray Sampedro
 */
public class GenericMenuController {

    //The user that accesses the menu
    private User user;

    private Stage stage;

    //The border pane of welcom window in which we stablish the views
    private BorderPane border;

    /**
     * Method used to get the border
     *
     * @return
     */
    public BorderPane getBorder() {
        return border;
    }

    /**
     * Method used to set the border
     *
     * @param border
     */
    public void setBorder(BorderPane border) {
        this.border = border;
    }

    /**
     * Method used to set the stage
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the stage
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Set the user
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getrs the user
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

}
