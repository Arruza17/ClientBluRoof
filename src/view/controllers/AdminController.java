/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import cipher.Cipher;
import exceptions.BusinessLogicException;
import interfaces.UserManager;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author YERAY
 */
public class AdminController {

    private static final Logger LOGGER = Logger.getLogger("AdminController");

    private UserManager userManager;

    private Stage stage;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<User> tblAdmin;
    @FXML
    private TableColumn<User, String> colFullName;
    @FXML
    private TableColumn<User, String> colLogin;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, Timestamp> colBirthDate;
    @FXML
    private TableColumn<User, String> colPhone;
    @FXML
    private ImageView imgPrint;
    @FXML
    private ImageView imgAdd;
    @FXML
    private ImageView imgDel;
    @FXML
    private ImageView imgCommit;
    @FXML
    private ImageView imgCancel;

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("BluRoof Admin");

        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tblAdmin.setEditable(true);
        ObservableList<User> users = null;
        try {
            users = FXCollections.observableArrayList(userManager.findAllAdmins());
            tblAdmin.setItems(users);

            System.out.println(userManager.login("yeraysampedro", "abcd*1234"));
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            LOGGER.log(Level.WARNING, "{0} exception thrown at InitStage method", ex.getClass().getSimpleName());
        }

        /*users.addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> change) {
                System.out.println("Selection changed: " + change.getList());
            }
        })*/
        imgCancel.setDisable(true);
        imgCancel.setOpacity(0.25);
        imgCommit.setDisable(true);
        imgCommit.setOpacity(0.25);
        imgDel.setDisable(true);
        imgDel.setOpacity(0.25);

        stage.setScene(scene);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @FXML
    private void handleTableAdd(MouseEvent event) {
        System.out.println("Add");
    }

    @FXML
    private void handleTableDelete(MouseEvent event) {
        System.out.println("Delete");
    }

    @FXML
    private void handleTableCommit(MouseEvent event) {
        System.out.println("Commit");
    }

    @FXML
    private void handleTableCancel(MouseEvent event) {
        System.out.println("Cancel");
    }

}
