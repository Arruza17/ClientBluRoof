/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import cipher.Cipher;
import enumerations.UserPrivilege;
import enumerations.UserStatus;
import exceptions.BusinessLogicException;
import exceptions.EmailFormatException;
import exceptions.FieldsEmptyException;
import exceptions.FullNameFormatException;
import exceptions.MaxCharactersException;
import exceptions.PhoneFormatException;
import interfaces.UserManager;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleObjectProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Callback;

import model.User;
import resources.DateEditingCell;
import static view.controllers.SignUpController.VALID_EMAIL_ADDRESS;

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
            LOGGER.log(Level.WARNING, "{0} exception thrown at AdminController initStage", ex.getClass().getSimpleName());

        }

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
        colFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        colFullName.setEditable(true);
        colFullName.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colFullName.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> t) {
                try {
                    if (t.getNewValue().trim().isEmpty()) {
                        throw new FieldsEmptyException();
                    }
                    if (!t.getNewValue().trim().contains(" ") || t.getNewValue().trim().length() < 3) {
                        throw new FullNameFormatException();
                    }
                    if (t.getNewValue().trim().length() > 255) {
                        throw new MaxCharactersException();
                    }
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setFullName(t.getNewValue());
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colFullName);
                    tblAdmin.edit(t.getTablePosition().getRow(), colFullName);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);
                } catch (FullNameFormatException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Wrong format");
                    alert.setContentText(e.getMessage());
                    alert.show();

                    tblAdmin.layout();
                    tblAdmin.requestFocus();
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colFullName);
                    tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colFullName);
                    tblAdmin.edit(t.getTablePosition().getRow(), colFullName);
                } catch (FieldsEmptyException e) {

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Field empty");
                    alert.setContentText(e.getMessage());
                    alert.show();

                    tblAdmin.layout();
                    tblAdmin.requestFocus();
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colFullName);
                    tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colFullName);
                    tblAdmin.edit(t.getTablePosition().getRow(), colFullName);

                } catch (MaxCharactersException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Too many characters");
                    alert.setContentText(e.getMessage());
                    alert.show();

                    tblAdmin.layout();
                    tblAdmin.requestFocus();
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colFullName);
                    tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colFullName);
                    tblAdmin.edit(t.getTablePosition().getRow(), colFullName);

                } finally {
                    tblAdmin.refresh();
                }
            }
        });

        colFullName.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Make login column editable
        colLogin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));

        colLogin.setEditable(true);
        colLogin.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colLogin.setOnEditCommit(
                (CellEditEvent<User, String> t) -> {
                    try {
                        if (t.getNewValue().trim().isEmpty()) {
                            throw new FieldsEmptyException();
                        }

                        if (t.getNewValue().trim().length() > 255) {
                            throw new MaxCharactersException();
                        }

                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLogin(t.getNewValue());
                        tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colLogin);
                        tblAdmin.edit(t.getTablePosition().getRow(), colLogin);
                        imgCommit.setDisable(false);
                        imgCommit.setOpacity(1);
                        imgCancel.setDisable(false);
                        imgCancel.setOpacity(1);
                    } catch (FieldsEmptyException e) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Fields empty");
                        alert.setContentText(e.getMessage());
                        alert.show();
                        tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colLogin);
                        tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colLogin);
                        tblAdmin.edit(t.getTablePosition().getRow(), colLogin);
                    } catch (MaxCharactersException e) {
                        tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colLogin);
                        tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colLogin);
                        tblAdmin.edit(t.getTablePosition().getRow(), colLogin);
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Too many characters");
                        alert.setContentText(e.getMessage());
                        alert.show();
                    } finally {
                        tblAdmin.refresh();
                    }
                });

        colLogin.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Make email column editable
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colEmail.setEditable(true);
        colEmail.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colEmail.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> t) {
                try {
                    Pattern valid_email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = valid_email.matcher(t.getNewValue().trim());
                    if (!matcher.find()) {
                        throw new EmailFormatException();
                    }
                    if (t.getNewValue().trim().isEmpty()) {
                        throw new FieldsEmptyException();
                    }
                    if (t.getNewValue().trim().length() > 255) {
                        throw new MaxCharactersException();
                    }
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setEmail(t.getNewValue());
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colEmail);
                    tblAdmin.edit(t.getTablePosition().getRow(), colEmail);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);
                } catch (EmailFormatException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Wrong format");
                    alert.setContentText(e.getMessage());
                    alert.show();
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colEmail);
                    tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colEmail);
                    tblAdmin.edit(t.getTablePosition().getRow(), colEmail);
                } catch (FieldsEmptyException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Field empty");
                    alert.setContentText(e.getMessage());
                    alert.show();
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colEmail);
                    tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colEmail);
                    tblAdmin.edit(t.getTablePosition().getRow(), colEmail);
                } catch (MaxCharactersException e) {
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colEmail);
                    tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colEmail);
                    tblAdmin.edit(t.getTablePosition().getRow(), colEmail);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Too many characters");
                    alert.setContentText(e.getMessage());
                    alert.show();
                } finally {
                    tblAdmin.refresh();
                }

            }
        });

        colEmail.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Make phone column editable
        colPhone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        colPhone.setEditable(true);
        colPhone.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colPhone.setOnEditCommit(
                (CellEditEvent<User, String> t) -> {
                    try {
                        if (t.getNewValue().trim().isEmpty()) {
                            throw new FieldsEmptyException();
                        }
                        if (t.getNewValue().trim().length() > 255) {
                            throw new MaxCharactersException();
                        }
                        Pattern valid_phone = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$");
                        Matcher matcher = valid_phone.matcher(t.getNewValue().trim());
                        if (!matcher.find()) {
                            throw new PhoneFormatException();
                        }

                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setPhoneNumber(t.getNewValue());
                        tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colPhone);
                        tblAdmin.edit(t.getTablePosition().getRow(), colPhone);
                        imgCommit.setDisable(false);
                        imgCommit.setOpacity(1);
                        imgCancel.setDisable(false);
                        imgCancel.setOpacity(1);
                    } catch (FieldsEmptyException | PhoneFormatException e) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Wrong format");
                        alert.setContentText(e.getMessage());
                        alert.show();
                        tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colPhone);
                        tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colPhone);
                        tblAdmin.edit(t.getTablePosition().getRow(), colPhone);
                    } catch (MaxCharactersException e) {
                        tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colPhone);
                        tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colPhone);
                        tblAdmin.edit(t.getTablePosition().getRow(), colPhone);
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Too many characters");
                        alert.setContentText(e.getMessage());
                        alert.show();
                    } finally {
                        tblAdmin.refresh();
                    }
                });

        colPhone.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Make birthdate column editable   
        colBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBirthDate()));
        colBirthDate.setEditable(true);
        Callback<TableColumn<User, Date>, TableCell<User, Date>> dateCellFactory
                = (TableColumn<User, Date> param) -> new DateEditingCell();
        colBirthDate.setCellFactory(dateCellFactory);

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
        User newUser = new User();
        newUser.setId(Long.MIN_VALUE);
        admin.add(newUser);
        tblAdmin.getSelectionModel().select(admin.size() - 1);
        tblAdmin.layout();
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
        int pos = tblAdmin.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete");
        alert.setContentText("You are about to delete the information about " + user.getFullName() + ". Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            LOGGER.info("User deletion cancelled");
        } else {
            LOGGER.info("User deleted");
            try {
                if (!(pos == admin.size() - 1 && user.getId().equals(Long.MIN_VALUE))) {
                    userManager.deleteUser(String.valueOf(user.getId()));
                }
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
        imgDel.setDisable(true);
        imgDel.setOpacity(0.25);
        imgAdd.setDisable(false);
        imgAdd.setOpacity(1);
    }

    @FXML
    private void handleTableCommit(MouseEvent event) {
        User user = tblAdmin.getSelectionModel().getSelectedItem();
        int pos = tblAdmin.getSelectionModel().getSelectedIndex();
        try {
            if (pos == admin.size() - 1 && user.getId().equals(Long.MIN_VALUE)) {
                user.setPrivilege(UserPrivilege.ADMIN.name());
                user.setStatus(UserStatus.ENABLED.name());
                user.setBirthDate(new Date());
              
                userManager.createUser(user);
                //userManager.resetPassword(user.getLogin());
                tblAdmin.refresh();
                LOGGER.info("Creation of new admin");
            } else {
                userManager.updateUser(user);
                tblAdmin.refresh();
                LOGGER.info("Update admin");
            }
        } catch (BusinessLogicException ex) {
            Alert excAlert = new Alert(AlertType.INFORMATION);
            excAlert.setTitle("Error");
            excAlert.setContentText("There was an error with the edition of the user: " + ex.getMessage());
            excAlert.show();
            LOGGER.log(Level.SEVERE, "BusinessLogicException thrown at handleTableCommit(): {0}", ex.getMessage());
        }
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
            if (pos == admin.size() - 1 && admin.get(pos).getId() == Long.MIN_VALUE) {
                LOGGER.info("Cancel creation");
                admin.remove(admin.size() - 1);
            } else {
                LOGGER.info("Cancel update");
            }
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
            imgAdd.setDisable(false);
            imgAdd.setOpacity(1);
            imgDel.setDisable(true);
            imgDel.setOpacity(0.25);
            tblAdmin.setItems(admin);
            tblAdmin.refresh();
            tblAdmin.getSelectionModel().clearSelection(tblAdmin.getSelectionModel().getSelectedIndex());
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
            Alert excAlert = new Alert(AlertType.INFORMATION);
            excAlert.setTitle("Error");
            excAlert.setContentText("There was an error finding information " + ex.getMessage());
            excAlert.show();
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
