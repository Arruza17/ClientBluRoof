/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import model.User;
import restful.UserRestfulClient;

/**
 * FXML Controller class
 *
 * @author YERAY
 */
public class AdminsCRUDController {

    private Stage stage;

    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<User, String> colFullName;
    @FXML
    private TableColumn<User, String> colLogin;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, Date> colBirthDate;
    @FXML
    private TableColumn<User, String> colPhone;
    @FXML
    private TableView<User> tblAdmin;

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("BluRoof Admin");
        //Gets the icon of the window.

        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colFullName.setCellFactory(TextFieldTableCell.forTableColumn());

        ObservableList<User> users = FXCollections
                .observableArrayList(new UserRestfulClient().findAll(
                        new GenericType<List<User>>() {
                }));

        tblAdmin.setEditable(true);
        tblAdmin.setItems(users);
        stage.setScene(scene);
        stage.show();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
