/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.controllers.SignInController;

/**
 *
 * @author Yeray Sampedro, Jorge Crespo
 */
public class Application extends javafx.application.Application{
   
    public static void main(String[] args) {
        launch(args);
    }

   

    @Override
    public void start(Stage primaryStage) throws Exception {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/SignIn.fxml"));
            Parent root = (Parent) loader.load();
            SignInController controller=((SignInController)loader.getController());
            controller.setStage(primaryStage);
            controller.initStage(root);
           
    }
}
