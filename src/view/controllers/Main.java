/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import factories.UserManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author YERAY
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Entry pont for the JavaFX application. Load, sets and shows the primary
     * window
     *
     * @param primaryStage The primary window of the applicarion
     * @throws Exception the Exception to be thrown
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/Admins.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AdminController controller = ((AdminController) fxmlLoader.getController());
        controller.setStage(primaryStage);
        controller.setUserManager(UserManagerFactory.createUsersManager(UserManagerFactory.REST_WEB_CLIENT_TYPE));
        //Initialize stage 
        controller.initStage(root);

    }

  
}
