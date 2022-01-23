/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import enumerations.UserPrivilege;
import enumerations.UserStatus;
import exceptions.BusinessLogicException;
import exceptions.FieldsEmptyException;
import interfaces.UserManager;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author Yeray Sampedro
 */
public class AdminController {

    private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());

    private UserManager userManager;

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
    private TableColumn<User, Date> colBirthDate;
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

    private ObservableList<User> admin;

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage() {
        LOGGER.info("Loading admin controller view");
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        try {
            admin = FXCollections.observableArrayList(userManager.findAllAdmins());
            tblAdmin.setItems(admin);
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            LOGGER.log(Level.WARNING, "{0} exception thrown at InitStage method", ex.getClass().getSimpleName());
        }
        colFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        colLogin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colPhone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        //colBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getBirthDate());

        tblAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                imgDel.setDisable(false);
                imgDel.setOpacity(1);
            } else {
                imgDel.setDisable(true);
                imgDel.setOpacity(0.25);
            }
        });
        tblAdmin.setEditable(true);
        imgCancel.setDisable(true);
        imgCancel.setOpacity(0.25);
        imgCommit.setDisable(true);
        imgCommit.setOpacity(0.25);
        imgDel.setDisable(true);
        imgDel.setOpacity(0.25);

        //Make full name column editable
        colFullName.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colFullName.setOnEditCommit(
                (CellEditEvent<User, String> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setFullName(t.getNewValue());
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colFullName);
                    tblAdmin.edit(t.getTablePosition().getRow(), colFullName);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);
                });

        colFullName.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Make login column editable
        colLogin.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colLogin.setOnEditCommit(
                (CellEditEvent<User, String> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setLogin(t.getNewValue());
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colLogin);
                    tblAdmin.edit(t.getTablePosition().getRow(), colLogin);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);
                });

        colLogin.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Make email column editable
        colEmail.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colEmail.setOnEditCommit(
                (CellEditEvent<User, String> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setEmail(t.getNewValue());
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colEmail);
                    tblAdmin.edit(t.getTablePosition().getRow(), colEmail);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);
                });

        colEmail.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Make phone column editable
        colPhone.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colPhone.setOnEditCommit(
                (CellEditEvent<User, String> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setPhoneNumber(t.getNewValue());
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colPhone);
                    tblAdmin.edit(t.getTablePosition().getRow(), colPhone);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);
                });

        colPhone.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Make birthdate column editable
        colBirthDate.setCellFactory(tableCell -> {
            return new TableCell<User, Date>() {
                @Override
                protected void updateItem(Date date, boolean dateIsEmpty) {
                    SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");
                    super.updateItem(date, dateIsEmpty);
                    if (date == null || dateIsEmpty) {
                        setText(null);
                    } else {
                        setText(sdFormat.format(date));
                    }
                }
            };
        });

        colBirthDate.setOnEditCommit(
                (CellEditEvent<User, Date> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setBirthDate(t.getNewValue());
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colPhone);
                    tblAdmin.edit(t.getTablePosition().getRow(), colPhone);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);
                });

        colBirthDate.setOnEditCancel((CellEditEvent<User, Date> t) -> {
            tblAdmin.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

    }

  

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @FXML
    private void handleTableAdd(MouseEvent event) {
        LOGGER.info("Adding new empty admin to the table");
        admin.add(new User());
        tblAdmin.getSelectionModel().select(admin.size() - 1);
        tblAdmin.getFocusModel().focus(admin.size() - 1, colFullName);
        tblAdmin.edit(admin.size() - 1, colFullName);
        imgCommit.setDisable(false);
        imgCommit.setOpacity(1);
        imgCancel.setDisable(false);
        imgCancel.setOpacity(1);
        imgAdd.setDisable(true);
        imgAdd.setOpacity(0.25);
        imgDel.setDisable(true);
        imgDel.setOpacity(0.25);
    }

    @FXML
    private void handleTableDelete(MouseEvent event) {
        LOGGER.info("Deleteing information about user");
        User user = tblAdmin.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete");
        alert.setContentText("You are about to delete the information about " + user.getFullName() + ". Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            LOGGER.info("User deletion cancelled");
        } else {
            LOGGER.info("User deleted");
            try {
                userManager.deleteUser(String.valueOf(user.getId()));
                admin.remove(user);
                tblAdmin.refresh();
            } catch (BusinessLogicException ex) {
                LOGGER.log(Level.SEVERE, "BusinessLogicException thrown at handleTableDelete(): {0}", ex.getMessage());
                Alert excAlert = new Alert(AlertType.INFORMATION);
                excAlert.setTitle("Error");
                excAlert.setContentText("There was an error with the deletion of the user: " + ex.getMessage());
                excAlert.show();
            }
        }
    }

    @FXML
    private void handleTableCommit(MouseEvent event) {
        User user = tblAdmin.getSelectionModel().getSelectedItem();
        user.setPrivilege(UserPrivilege.ADMIN.name());
        user.setStatus(UserStatus.ENABLED.name());
        int pos = tblAdmin.getSelectionModel().getSelectedIndex();
        //try {

        if (pos == admin.size() - 1) {
            user.setLastPasswordChange(new Date());
            //userManager.updateUser(user);   
            //userManager.resetPassword(user.getLogin());
            tblAdmin.refresh();
            LOGGER.info("Creation");
        } else {
            //userManager.createUser(user);
            tblAdmin.refresh();
            LOGGER.info("update");

        }
        /*} catch (BusinessLogicException ex) {
            Alert excAlert = new Alert(AlertType.INFORMATION);
            excAlert.setTitle("Error");
            excAlert.setContentText("There was an error with the edition of the user: " + ex.getMessage());
            excAlert.show();
        }*/
    }

    @FXML
    private void handleTableCancel(MouseEvent event) {
        int pos = tblAdmin.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Cancel");
        alert.setContentText("You are about to stop the edition\nAre you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        //try {
        if (result.get() == ButtonType.OK) {
            if (tblAdmin.getSelectionModel().getSelectedItem().getId() == null && pos == admin.size() - 1) {
                //userManager.updateUser(user);   
                //userManager.resetPassword(user.getLogin());
                LOGGER.info("Cancel creation");
                admin.remove(admin.size() - 1);
            } else {
                //userManager.createUser(user);

                LOGGER.info("Cancel update");
            }
            //tblAdmin.setItems(admin);
            tblAdmin.refresh();
        }
    }

    @FXML
    private void searchByLogin(ActionEvent event) {
        try {
            admin.clear();
            String text = tfSearch.getText().trim();
            if (text.isEmpty()) {
                admin = FXCollections.observableArrayList(userManager.findAllAdmins());
            } else {
                admin = FXCollections.observableArrayList(userManager.findAllAdminsByLogin("%" + text + "%"));
            }

        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, "BusinessLogicException thrown at searchByLogin(): {0}", ex.getMessage());
        } finally {
            tblAdmin.setItems(admin);
            tblAdmin.refresh();

        }

    }

    @FXML
    private void printReport(MouseEvent event) {

    }
}
