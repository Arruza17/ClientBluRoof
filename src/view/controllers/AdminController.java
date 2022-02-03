package view.controllers;

import enumerations.UserPrivilege;
import enumerations.UserStatus;
import exceptions.BusinessLogicException;
import exceptions.EmailFormatException;
import exceptions.FieldsEmptyException;
import exceptions.FullNameFormatException;
import exceptions.MaxCharactersException;
import exceptions.PhoneFormatException;
import interfaces.UserManager;
import java.util.Collection;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import resources.DateEditingCell;

/**
 * FXML Controller for AdminTable
 *
 * @author Yeray Sampedro
 */
public class AdminController {

    //Logger of the class
    private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());

    //Implementation of the userManager
    private UserManager userManager;

    //The user that opened the window
    private User user;

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
     */
    public void initStage() {
        LOGGER.info("Loading admin controller view");
        //Setting all the value factories
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        try {
            //Reading all the information of the admins
            admin = FXCollections.observableArrayList(userManager.findAllAdmins());
            tblAdmin.setItems(admin);
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            LOGGER.log(Level.WARNING, "{0} exception thrown at AdminController initStage", ex.getClass().getSimpleName());

        }
        //Method used to control if a table row has been or not selected
        tblAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                imgDel.setDisable(false);
                imgDel.setOpacity(1);
            } else {
                imgDel.setDisable(true);
                imgDel.setOpacity(0.25);
            }
        });
        //Setting the table editable
        tblAdmin.setEditable(true);
        //Setting all the button properties
        disableEdition();
        imgDel.setDisable(true);
        imgDel.setOpacity(0.25);

        //Make full name column editable
        colFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        colFullName.setEditable(true);
        colFullName.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colFullName.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> t) {
                enableEdition();
                try {

                    //Catch the exceptions in case the user imput is not valid
                    if (t.getNewValue().trim().isEmpty()) {
                        throw new FieldsEmptyException();
                    }
                    if (!t.getNewValue().trim().contains(" ") || t.getNewValue().trim().length() < 3) {
                        throw new FullNameFormatException();
                    }
                    if (t.getNewValue().trim().length() > 255) {
                        throw new MaxCharactersException();
                    }
                    //Update the data of the user
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setFullName(t.getNewValue());
                    //Move row
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colLogin);
                    tblAdmin.edit(t.getTablePosition().getRow(), colLogin);
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
        //Disable the commit and cancel buttons if the user cancels
        colFullName.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
        });

        //Make login column editable
        colLogin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));

        colLogin.setEditable(true);
        colLogin.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colLogin.setOnEditCommit(
                (CellEditEvent<User, String> t) -> {
                    enableEdition();
                    //Catch the exceptions in case the user input is not valid
                    try {
                        if (t.getNewValue().trim().isEmpty()) {
                            throw new FieldsEmptyException();
                        }

                        if (t.getNewValue().trim().length() > 255) {
                            throw new MaxCharactersException();
                        }
                        //Update the data of the user
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLogin(t.getNewValue());
                        //Move row
                        tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colEmail);
                        tblAdmin.edit(t.getTablePosition().getRow(), colEmail);
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

        //Disable the commit and cancel buttons if the user cancels
        colLogin.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
        });

        //Make email column editable
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colEmail.setEditable(true);
        colEmail.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colEmail.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> t) {
                enableEdition();
                try {
                    Pattern valid_email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = valid_email.matcher(t.getNewValue().trim());
                    //Disable the commit and cancel buttons if the user cancels
                    if (!matcher.find()) {
                        throw new EmailFormatException();
                    }
                    if (t.getNewValue().trim().isEmpty()) {
                        throw new FieldsEmptyException();
                    }
                    if (t.getNewValue().trim().length() > 255) {
                        throw new MaxCharactersException();
                    }
                    //Update the data of the user
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setEmail(t.getNewValue());
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colBirthDate);
                    //Move row
                    tblAdmin.edit(t.getTablePosition().getRow(), colBirthDate);
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

        //Disable the commit and cancel buttons if the user cancels
        colEmail.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
        });

        //Make phone column editable
        colPhone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        colPhone.setEditable(true);
        colPhone.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colPhone.setOnEditCommit(
                (CellEditEvent<User, String> t) -> {
                    try {
                        enableEdition();
                        //Catch the exceptions in case the user imput is not valid
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
                        //Update the data of the user
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setPhoneNumber(t.getNewValue());
                        //Move row
                        tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colPhone);
                        tblAdmin.edit(t.getTablePosition().getRow(), colPhone);
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

        //Disable the commit and cancel buttons if the user cancels
        colPhone.setOnEditCancel((CellEditEvent<User, String> t) -> {
            tblAdmin.refresh();
        });

        //Make birthdate column editable   
        colBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBirthDate()));

        colBirthDate.setEditable(true);

        //Create the datePicker cell
        Callback<TableColumn<User, Date>, TableCell<User, Date>> dateCellFactory
                = (TableColumn<User, Date> param) -> new DateEditingCell();
        colBirthDate.setCellFactory(dateCellFactory);

        //Enable the buttons and update the data of the user when commiting
        colBirthDate.setOnEditCommit(
                (CellEditEvent<User, Date> t) -> {
                    enableEdition();
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setBirthDate(t.getNewValue());
                    tblAdmin.getSelectionModel().select(t.getTablePosition().getRow(), colPhone);
                    tblAdmin.getFocusModel().focus(t.getTablePosition().getRow(), colPhone);
                });
        //Disable the commit and cancel buttons if the user cancels
        colBirthDate.setOnEditCancel((CellEditEvent<User, Date> t) -> {
            tblAdmin.refresh();

        });

    }

    /**
     *
     * @param userManager
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Handles the adition of new rows to the table
     *
     * @param event
     */
    @FXML
    private void handleTableAdd(MouseEvent event) {
        LOGGER.info("Adding new empty admin to the table");
        User newUser = new User();
        //Setting the id to the lowest long value to know it is a creation
        newUser.setId(Long.MIN_VALUE);
        admin.add(newUser);
        //Selecting that row
        tblAdmin.getSelectionModel().select(admin.size() - 1);
        tblAdmin.layout();
        //Focusing the column
        tblAdmin.getFocusModel().focus(admin.size() - 1, colFullName);
        //Opening the first column in edit mode
        tblAdmin.edit(admin.size() - 1, colFullName);
        enableEdition();
        imgAdd.setDisable(true);
        imgAdd.setOpacity(0.25);
        imgDel.setDisable(true);
        imgDel.setOpacity(0.25);
    }

    /**
     * Used to handle the deletion of rows
     *
     * @param event
     */
    @FXML
    private void handleTableDelete(MouseEvent event) {
        LOGGER.info("Deleteing information about user");
        //Getting the information of the user to delete
        User selectedUser = tblAdmin.getSelectionModel().getSelectedItem();
        if (user.getId() == selectedUser.getId()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cannot delete this user");
            alert.setContentText("You cannot delete yourself from the table");
            alert.show();
        } else {
            //Getting the position of the user to delete
            int pos = tblAdmin.getSelectionModel().getSelectedIndex();
            //Show an alert to confirm
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete");
            alert.setContentText("You are about to delete the information about " + selectedUser.getFullName() + ". Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                LOGGER.info("User deletion cancelled");
                Alert allert = new Alert(AlertType.INFORMATION);
                allert.setTitle("Cancelled");
                allert.setContentText("User deletion cancelled");
                allert.show();
            } else {
                LOGGER.info("User deleted");
                try {
                    //If it was not an addition of row
                    if (!(pos == admin.size() - 1 && selectedUser.getId().equals(Long.MIN_VALUE))) {
                        userManager.deleteUser(String.valueOf(selectedUser.getId()));
                    }
                    //Either case, we update the table
                    admin.remove(selectedUser);
                    tblAdmin.refresh();
                } catch (BusinessLogicException ex) {
                    LOGGER.log(Level.SEVERE, "BusinessLogicException thrown at handleTableDelete(): {0}", ex.getMessage());
                    Alert excAlert = new Alert(AlertType.INFORMATION);
                    excAlert.setTitle("Error");
                    excAlert.setContentText("There was an error with the deletion of the user: " + ex.getMessage());
                    excAlert.show();
                } catch (Exception ex) {
                    Alert excAlert = new Alert(AlertType.INFORMATION);
                    excAlert.setTitle("Error");
                    excAlert.setContentText("There was an error with the connection of the server");
                    excAlert.show();
                    LOGGER.log(Level.SEVERE, ex.getClass().getSimpleName() + " thrown at handleTableCommit(): {0}", ex.getMessage());
                }
            }

            imgDel.setDisable(true);
            imgDel.setOpacity(0.25);
            imgAdd.setDisable(false);
            imgAdd.setOpacity(1);
        }
    }

    /**
     * Method used to commit the changes of the table
     *
     * @param event
     */
    @FXML
    private void handleTableCommit(MouseEvent event) {
        User user = tblAdmin.getSelectionModel().getSelectedItem();
        int pos = tblAdmin.getSelectionModel().getSelectedIndex();
        try {
            //Check if any of the data inputted is empty
            if (user.getLogin().isEmpty()
                    || user.getEmail().isEmpty() || user.getFullName().isEmpty() || user.getPhoneNumber().isEmpty()) {
                throw new FieldsEmptyException();
            }
            //Check if the position its the last (Addition)
            if (pos == admin.size() - 1 && user.getId().equals(Long.MIN_VALUE)) {
                //Setting the values that are compulsery
                user.setPrivilege(UserPrivilege.ADMIN.name());
                user.setStatus(UserStatus.ENABLED.name());
                user.setLastPasswordChange(new Date());
                user.setId(null);
                userManager.createUser(user);
                //Reset the password so the admins receive a unique pass
                userManager.resetPassword(user.getLogin());
                tblAdmin.refresh();
                LOGGER.info("Creation of new admin");
            } else {
                userManager.updateUser(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("User updated");
                alert.setContentText("User information successfully updated!");
                alert.show();
                tblAdmin.refresh();
                LOGGER.info("Update admin");
            }
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
            imgAdd.setDisable(false);
            imgAdd.setOpacity(1);
            imgDel.setDisable(true);
            imgDel.setOpacity(0.25);

        } catch (BusinessLogicException ex) {
            Alert excAlert = new Alert(AlertType.INFORMATION);
            excAlert.setTitle("Error");
            excAlert.setContentText(ex.getMessage());
            excAlert.show();
            LOGGER.log(Level.SEVERE, "BusinessLogicException thrown at handleTableCommit(): {0}", ex.getMessage());
        } catch (FieldsEmptyException ex) {
            Alert excAlert = new Alert(AlertType.INFORMATION);
            excAlert.setTitle("Error");
            excAlert.setContentText(ex.getMessage());
            excAlert.show();
            LOGGER.log(Level.SEVERE, "FieldsEmptyException thrown at handleTableCommit(): {0}", ex.getMessage());
        } catch (Exception ex) {
            Alert excAlert = new Alert(AlertType.INFORMATION);
            excAlert.setTitle("Error");
            excAlert.setContentText("There was an error with the connection of the server");
            excAlert.show();
            LOGGER.log(Level.SEVERE, ex.getClass().getSimpleName() + " thrown at handleTableCommit(): {0}", ex.getMessage());
        } finally {
            tblAdmin.getSelectionModel().clearSelection();
        }
    }

    /**
     * Method used to handle the cancel action
     *
     * @param event
     */
    @FXML
    private void handleTableCancel(MouseEvent event) {
        int pos = tblAdmin.getSelectionModel().getSelectedIndex();
        //Alert to ask if they want to cancel
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Cancel");
        alert.setContentText("You are about to stop the edition\nAre you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                //Remove the data if called an addition
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
                tblAdmin.getItems().clear();
                admin = FXCollections.observableArrayList(userManager.findAllAdmins());
                tblAdmin.setItems(admin);
                tblAdmin.refresh();
                tblAdmin.getSelectionModel().clearSelection(tblAdmin.getSelectionModel().getSelectedIndex());
            } catch (BusinessLogicException ex) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Error with the cancel");
                alert.setContentText("There was an error updating the information, try again later");
                LOGGER.log(Level.SEVERE,
                        "Error updating the table at handleTableCancel(): {0}",
                        ex.getMessage());
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Method used to search admins that contain words in their login
     *
     * @param event
     */
    @FXML
    private void searchByLogin(ActionEvent event) {
        try {
            //Clear the data of the admins in the table
            admin.clear();
            String text = tfSearch.getText().trim();
            if (text.isEmpty()) {
                //Search for all the admins if no text is imput
                admin = FXCollections.observableArrayList(userManager.findAllAdmins());
            } else {
                //Search for the admins that contain the text
                admin = FXCollections.observableArrayList(userManager.findAllAdminsByLogin("%" + text + "%"));
            }
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
            imgAdd.setDisable(false);
            imgAdd.setOpacity(1);
            imgDel.setDisable(true);
            imgDel.setOpacity(0.25);
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

    /**
     * Method used to print a report using JasperReports
     *
     * @param event
     */
    @FXML
    private void printReport(MouseEvent event) {
        try {
            LOGGER.info("Beginning printing action...");
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/reports/AdminReport.jrxml"));
            //Data for the report: a collection of User passed as a JRDataSource implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<User>) this.tblAdmin.getItems());
            //Get the name of the admin who printed the report as a parameter
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with the data of the table     
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Error printing");
            alert.setContentText("There was an error printing the information, try again later");
            LOGGER.log(Level.SEVERE,
                    "Error printing report at printReport(): {0}",
                    ex.getMessage());
        }
    }

    /**
     * Method that enables the Commit and Cancel buttons
     */
    private void enableEdition() {
        imgCommit.setDisable(false);
        imgCommit.setOpacity(1);
        imgCancel.setDisable(false);
        imgCancel.setOpacity(1);
    }

    /**
     * Method that disables the Commit and Cancel buttons
     */
    private void disableEdition() {
        imgCommit.setDisable(true);
        imgCommit.setOpacity(0.25);
        imgCancel.setDisable(true);
        imgCancel.setOpacity(0.25);
    }

    /**
     * Sets the user of the view
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
