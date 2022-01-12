/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Adrián 
 */
public class NeighborhoodsController {

    @FXML
    private ChoiceBox<?> cbNeighborhood;
    @FXML
    private Spinner<?> spinnerNeighbourhood;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<?> tbvNeighborhood;
    private Stage stage;

    public void initStage(Parent root) {
        LOGGER.info("Initializing SignIn stage.");
        Scene scene = new Scene(root);
        //CSS load
        String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
        scene.getStylesheets().add(css);
        //Stage dimension setters
 
        stage.setTitle("BluRoof SignIn Page");
        //Gets the icon of the window.
   
        stage.setTitle("SignIn");
        //Close request handler declaration     
        stage.show();
        LOGGER.info("SignIn Open Window");
    }
   
    
}
