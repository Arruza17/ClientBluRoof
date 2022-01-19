/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 *
 * @author YERAY
 */
public class MenuFactory {

    public FXMLLoader getMenu(String privilege) throws IOException {
        FXMLLoader fxmlLoader;
        switch (privilege) {
            case "ADMIN":
                fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/AdminMenu.fxml"));
                break;
            case "HOST":
                fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/HostMenu.fxml"));
                break;
            case "GUEST":
                fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/GuestMenu.fxml"));
                break;
            default:
                fxmlLoader = null;
        }

        return fxmlLoader;
    }

}
