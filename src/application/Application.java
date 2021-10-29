/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.controllers.SignUpController;

/**
 * Entry point for the java application
 * @author BluRoof
 */
public class Application extends javafx.application.Application{
    
    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getName());
    /** Entry point for the java application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Entry pont for the JavaFX application. Load, sets and shows the primary window
     * @param primaryStage The primary window of the applicarion
     * @throws Exception the Exception to be thrown
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/SignUp.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            SignUpController controller = ((SignUpController) fxmlLoader.getController());
            controller.setStage(primaryStage);
            //Initialize stage
            controller.initStage(root);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "UI LogIn Controller: Error openning users managing window",ex.getMessage());
        }
    }
}
