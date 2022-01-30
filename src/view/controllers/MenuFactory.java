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
 * Factory used to generate the menus
 * @author Yeray Sampedro
 */
public class MenuFactory {

    /**
     * Method used to get the menu depending on the type of user
     * @param privilege UserPrivilege item refering to the type of user
     * @return fxmlloader the menu
     * @throws IOException 
     */
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
