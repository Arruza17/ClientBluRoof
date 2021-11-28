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
public class MenuController {

    private Stage stage;
    @FXML
    private ImageView homeBtn;
    @FXML
    private ImageView homeBtn1;

    private BorderPane mainPane;

    private DropDownController drop;

    /**
     * Method used to load all stage settings when creating the stage.
     *
     * @param root the parent object represinting root node of the view graph.
     *
     */
    public void initStage(Parent root) {
        //Adding the css
        mainPane = (BorderPane) root;
        String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
        homeBtn.setOnMouseClicked(this::handleMenu);
        homeBtn1.setOnMouseClicked(this::handleMenu);

    }

    private void handleMenu(MouseEvent event) {
        if (mainPane.getLeft() == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/DropDownMenu.fxml"));
                Parent leftMenu = (Parent) loader.load();
                drop = loader.getController();             
                mainPane.setLeft(leftMenu);
                drop.initStage(mainPane);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            mainPane.setLeft(null);
        }
    }

}
