/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import enumerations.ServiceType;
import exceptions.BusinessLogicException;
import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
import interfaces.ServicesManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Service;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controller class for services of the admin view .
 *
 * @author Adrián Pérez
 */
public class ServicesController {

    /**
     * Logger instance constant of this controller.
     */
    private static final Logger LOGGER = Logger.getLogger(ServicesController.class.getName());
    /**
     * The business logic interface implementation.
     */
    private ServicesManager serviceManager;
    /**
     * All the values of the cbService ComboBox that are going to be used to
     * select diferent types of queries.
     */
    private final String SELECT_ALL_SERVICES = "All services";

    private final String SELECT_BY_ADDRESS = "By address";

    private final String SELECT_BY_NAME = "By name";

    private final String SELECT_BY_TYPE = "By type";

    /**
     * ComboBox containing the queries.
     */
    @FXML
    private ComboBox<String> cbService;
    /**
     * TextField used for text input queries.
     */
    @FXML
    private TextField tfServices;
    /**
     * Button used to start the queries.
     */
    @FXML
    private Button btnSearchService;
    /**
     * Clickable Imageview to add services.
     */
    @FXML
    private ImageView imgAdd;
    /**
     * Clickable Imageview to delete services.
     */
    @FXML
    private ImageView imgDelete;
    /**
     * Clickable Imageview to commit services.
     */
    @FXML
    private ImageView imgCommit;
    /**
     * Clickable Imageview to cancel services.
     */
    @FXML
    private ImageView imgCancel;
    /**
     * Services TableView.
     */
    @FXML
    private TableView<Service> tbvService;
    /**
     * Address TableColumn.
     */
    @FXML
    private TableColumn<Service, String> tcAddress;
    /**
     * Name TableColumn.
     */
    @FXML
    private TableColumn<Service, String> tcName;
    /**
     * Type TableColumn.
     */
    @FXML
    private TableColumn<Service, String> tcType;
    /**
     * Clickable Imageview to print the displayed services on the table.
     */
    @FXML
    private ImageView imgPrint;
    /**
     * An ObservableList of services used to modify the table items.
     */
    private ObservableList<Service> services;
    /**
     * An ObservableList of Strings used to store the ServiceType enumeration
     * values.
     */
    private ObservableList<String> types;
    /**
     * The table editable ComboBox;
     */
    private ComboBox<String> type;
    /**
     * The ComboBox used in the queries by type.
     */
    @FXML
    private ComboBox<String> cbServiceType;
    /**
     * Used to check if a service is being added.
     */
    private boolean addingService;
    /**
     * Used to check if a cell or a row (when adding service) is commited on the
     * table succesfully.
     */
    private boolean tableCommitting;
    /**
     * Used to check if a cell or a row (when adding service) is commited in the
     * DB succesfully.
     */
    private boolean committingDB;
    /**
     * Used to check if a search is being done.
     */
    private boolean searching;
    /**
     * Used to check if a cell is being edited.
     */
    private boolean editing;
    /**
     * Used to check if a cell or a DB commit is being cancelled.
     */
    private boolean cancelling;
    /**
     * Used to check if a service is being added when a query returns nothing.
     */
    private boolean addingWithNullServices;
    /**
     * a String used to save the query field value when you search.
     */
    private String savedAddress;
    /**
     * a String used to save the query field value when you search.
     */
    private String savedName;
    /**
     * a String used to save the selected value from cbServiceType when you
     * search.
     */
    private String savedServiceType;
    /**
     * a String used to save the old address of the when a cell is edited and
     * you are not adding.
     */
    private String oldAddress;
    /**
     * a String used to save the old address of the when a cell is edited and
     * you are not adding.
     */
    private String oldName;
    /**
     * a String used to save the old address of the when a cell is edited and
     * you are not adding.
     */
    private String oldServiceType;

    /**
     * a String used to save the new committed address.
     */
    private String newAddress;

    /**
     * a String used to save the new committed name.
     */
    private String newName;
    /**
     * a String used to save the new committed type.
     */
    private String newType;
    /**
     * a Integer to save the row index of the edited cell.
     */
    private Integer selectedRow;
    /**
     * a Integer to save the row index of the last committed cell.
     */
    private Integer lastCommittedRow;

    /**
     * initStage method that initialises the stage and its components.
     */
    public void initStage() {
        try {
            LOGGER.info("ServiceWindow Open");

            //Sets the confirm & cancel imgs to not clickable
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
            //Sets the the delete imgs to not clickable
            imgDelete.setDisable(true);
            imgDelete.setOpacity(0.25);

            //Add the combobox values
            ObservableList<String> optionsForCombo;
            optionsForCombo = FXCollections.observableArrayList(
                    SELECT_ALL_SERVICES,
                    SELECT_BY_ADDRESS,
                    SELECT_BY_NAME,
                    SELECT_BY_TYPE
            );
            cbService.setItems(optionsForCombo);

            //Add a listener to check the values of the table items.
            tbvService.getSelectionModel().selectedItemProperty()
                    .addListener(this::handleTableSelectionChanged);

            //Setting new CellValueFactories to each column
            //CVF address
            tcAddress.setCellValueFactory(
                    new PropertyValueFactory<>("address"));

            //CVF name
            tcName.setCellValueFactory(
                    new PropertyValueFactory<>("name"));

            //This is the ComboBox used when you edit cell of the type column.
            type = new ComboBox();

            types = FXCollections.observableArrayList();

            //Getting values
            for (ServiceType st : ServiceType.values()) {
                types.add(st.toString());
            }

            //Setting items
            type.setItems((ObservableList) types);

            //CVF type
            tcType.setCellValueFactory(
                    new PropertyValueFactory<>("type"));

            tbvService.setEditable(true);
            tbvService.setMinWidth(500);
            //SELECT THE FIRST COMBOBOX ITEM BY DEFAULT
            cbService.getSelectionModel().selectFirst();

            //Making table columns editable. 
            setEditableColumns();

            //Loads all the services in the table
            services = loadAllServices();

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("database error.");
            alert.showAndWait();
        }
    }

    /**
     * This method loads all the services and disables or enables the print
     * imageView depending if there are 0, 1 or more services.
     *
     * @return an ObservableList with the services.
     */
    private ObservableList loadAllServices() {

        //Initialising an observable list to be returned.
        ObservableList<Service> servicesTableBean = null;
        try {

            //Getting all the services with the business logic method.
            List<Service> allServices = serviceManager.findAll();

            //Load the services into the servicesTableBean ObservableList
            servicesTableBean = FXCollections.observableArrayList(allServices);
            //Set the ObservableList to the table.
            tbvService.setItems(servicesTableBean);

            //Checking if there are services to be printed.
            if (allServices.size() < 1) {
                imgPrint.setDisable(true);
                imgPrint.setOpacity(0.25);

                //if searching and not services are found.
                if (searching) {
                    throw new BusinessLogicException("No services have been found");
                }

            } else {
                imgPrint.setDisable(false);
                imgPrint.setOpacity(1);

                if (searching) {
                    LOGGER.log(Level.INFO, "query done: list all services with :{0} results", allServices.size());

                }
            }
            //Exceptions catch.
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, "BusinessLogicException thrown at loadAllServices(): {0}", ex.getMessage());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("No services have been found");
            alert.showAndWait();
        }
        //table refresh.
        tbvService.refresh();
        return servicesTableBean;
    }

    /**
     * ServiceManager interfaceImplementation setter.
     *
     * @param serviceManager
     */
    public void setServiceManager(ServicesManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    /**
     * This method is used to check if the selected item of the table is not
     * null. Used to enable the imgDelete ImageView when is needed.
     *
     * @param observableValue The Observable value.
     * @param oldValue Last selected table item.
     * @param newValue Currently selected item.
     */
    private void handleTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {

        //enables delete ImageView if new value is not null.
        if (newValue != null && !tableCommitting) {
            imgDelete.setDisable(false);
            imgDelete.setOpacity(1);
            //disables it when a service is being added.
            if (addingService) {
                imgDelete.setDisable(true);
                imgDelete.setOpacity(0.25);
            }
            //disables delete ImageView if new value is null.
        } else {
            imgDelete.setDisable(true);
            imgDelete.setOpacity(0.25);
        }
    }

    /**
     * This method handles the actions caused when imgAdd is pressed.
     *
     * @param event the MouseEvent onPressed event.
     */
    @FXML
    private void handleServiceCreation(MouseEvent event) {

        if (services != null) {

            LOGGER.info("Adding new empty service to the table");

            addingService = true;
            updateServicesTable();

            //service creation
            Service s = new Service();
            s.setId(Long.MIN_VALUE);
            services.add(s);

            tbvService.getSelectionModel().select(services.size() - 1);
            tbvService.layout();
            tbvService.getFocusModel().focus(services.size() - 1, tcAddress);
            tbvService.edit(services.size() - 1, tcAddress);

            tcAddress.setSortable(false);
            tcName.setSortable(false);
            tcType.setSortable(false);

            //clickable imageviews handling.           
            handleTableImageViews();

        } else {

            LOGGER.info("Adding the first empty service to the table");

            addingWithNullServices = true;
            updateServicesTable();

            //service creation
            Service s = new Service();
            s.setId(Long.MIN_VALUE);
            //adding a new row
            services.add(s);

            //changing focus to the first column cell.           
            tbvService.getSelectionModel().select(services.size() - 1);
            tbvService.layout();
            tbvService.getFocusModel().focus(services.size() - 1, tcAddress);
            tbvService.edit(services.size() - 1, tcAddress);

            addingService = true;

            tcAddress.setSortable(false);
            tcName.setSortable(false);
            tcType.setSortable(false);

            handleTableImageViews();

        }
    }

    /**
     * This method set all the columns to editable. ATTENTION: - This method
     * handles all the different states (editing,creating,commiting + cancelling
     * types). - Sets the 3 different types of CellEditEvents.
     */
    private void setEditableColumns() {

        //Making name Services table column editable
        tcName.setCellFactory(TextFieldTableCell.<Service>forTableColumn());

        //CellEditEvent lambdas to manage the diferent states of a cell
        //controlling the events when the cell of this column is started to be edited.
        tcName.setOnEditStart(
                (CellEditEvent<Service, String> t) -> {

                    //startediting
                    editing = true;
                    //reseting selected row, could be used for adding or for editing conditions.
                    selectedRow = null;

                    //this condition is used to control the cell committing.
                    //if you edited and you committed into the table you won't be able to start editing in another row cell within confirmation.
                    if (!addingService && tableCommitting && lastCommittedRow != null && tbvService.getSelectionModel().getSelectedIndex() != lastCommittedRow) {
                        editing = true;
                        //momentaneously disabling.
                        imgDelete.setDisable(true);
                        imgDelete.setOpacity(0.25);
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Confirmation");
                        alert.setContentText("Do you want to cancel the last commit? \n if not press cancel and press commit button.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            //not editing now.
                            editing = false;
                            //not committing.
                            tableCommitting = false;
                            //default components enabling
                            enableDefaultComponents();

                            //now we check if the old values that are saved before committing into the table are not null
                            //if not,the last commit is cancelled and the old values are set again in each cell of the last committed row.
                            if (oldAddress != null) {

                                services.get(lastCommittedRow).setAddress(oldAddress);
                                oldAddress = null;
                                tbvService.getSelectionModel().clearSelection();
                                tbvService.refresh();
                            }
                            if (oldName != null) {
                                services.get(lastCommittedRow).setName(oldName);
                                oldName = null;
                                tbvService.getSelectionModel().clearSelection();
                                tbvService.refresh();
                            }

                            if (oldServiceType != null) {
                                services.get(lastCommittedRow).setType(oldServiceType);
                                oldServiceType = null;
                                tbvService.getSelectionModel().clearSelection();
                                tbvService.refresh();
                            }
                        } else {

                            //if you press cancel it means you dont want to lose your last commit
                            //the table selection will change to the last committed row if uou want to do some changes, cancel it or commit.
                            tbvService.getSelectionModel().select(lastCommittedRow);
                            tbvService.refresh();

                        }

                    } else {
                        // reseting old name for a new edit
                        oldName = null;
                        selectedRow = t.getTablePosition().getRow();

                        if (!addingService) {

                            //saving the old value of this edit
                            if (t.getOldValue() != null) {

                                oldName = t.getOldValue();
                                editing = true;
                            }
                        }

                        //this condition is used to control the cell adding.
                        //if you are adding a service and you committed into the table you won't be able to start editing in another row cell within a cancel confirmation.
                        if (addingService && !cancelling) {

                            if (tbvService.getSelectionModel().getSelectedIndex() != services.size() - 1) {

                                Alert Cancelalert = new Alert(AlertType.CONFIRMATION);
                                Cancelalert.setHeaderText(null);
                                Cancelalert.setTitle("Confirmation");
                                if (addingService) {
                                    Cancelalert.setContentText("Are you sure you want to cancel creating the last selected service?");
                                } else {

                                    Cancelalert.setContentText("Are you sure you want to cancel editing this cell?");
                                }
                                Optional<ButtonType> result = Cancelalert.showAndWait();
                                if (result.get() == ButtonType.OK) {

                                    if (addingService) {
                                        services.remove(services.size() - 1);
                                        addingService = false;

                                    }
                                    cancelling = true;
                                    editing = false;
                                    tableCommitting = false;

                                    enableDefaultComponents();

                                    updateServicesTable();

                                    LOGGER.info("creation cancelled");

                                } else {

                                    LOGGER.info("creation not cancelled");

                                    editing = false;

                                    tbvService.getSelectionModel().clearSelection();
                                    tbvService.getSelectionModel().focus(services.size() - 1);
                                    tbvService.getSelectionModel().select(services.size() - 1);
                                    tbvService.refresh();
                                }
                            }
                        }

                        handleTableImageViews();
                    }
                });

        tcName.setOnEditCommit((CellEditEvent<Service, String> t) -> {
            ((Service) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setName(t.getNewValue());
            tbvService.getSelectionModel().select(t.getTablePosition().getRow(), tcName);
            tbvService.edit(t.getTablePosition().getRow(), tcName);
            if (!addingService) {
                lastCommittedRow = null;
            }

            try {
                editing = false;
                if (t.getNewValue().trim().isEmpty()) {
                    //throw validation Error
                    LOGGER.warning("The name field is empty");
                    updateServicesTable();
                    enableDefaultComponents();
                    throw new FieldsEmptyException();
                }
                if (t.getNewValue().trim().length() > 255) {
                    //throw validation Error
                    LOGGER.warning("The field has more than 255 characters");
                    updateServicesTable();
                    enableDefaultComponents();
                    throw new MaxCharactersException();
                }

                if (!addingService && oldName != null) {
                    editing = false;
                    if (oldName.equals(t.getNewValue())) {
                        if (!tableCommitting) {
                            tableCommitting = false;
                        }
                    } else if (!oldName.equals(t.getNewValue())) {

                        tableCommitting = true;
                        lastCommittedRow = selectedRow;
                    }
                    handleTableImageViews();

                } else if (addingService) {
                    newName = t.getNewValue();
                    handleAddCommitting();
                    handleTableImageViews();
                }
            } catch (FieldsEmptyException | MaxCharactersException ex) {
                LOGGER.log(Level.WARNING, null, ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(ex.getMessage());
                alert.showAndWait();
                //SETS THE CONFIRM AND CANCEL BUTTONS TO NOT CLICKABLE
                imgCommit.setDisable(true);
                imgCommit.setOpacity(0.25);
                imgCancel.setDisable(true);
                imgCancel.setOpacity(0.25);

            }
        });

        tcName.setOnEditCancel((CellEditEvent<Service, String> t) -> {

            if (!cancelling) {
                handleOnEditCancel();
            }
        });

        //Making address Services table column editable        
        tcAddress.setCellFactory(TextFieldTableCell.<Service>forTableColumn());
        //CellEditEvent lambdas to manage the diferent states of a cell
        tcAddress.setOnEditStart(
                (CellEditEvent<Service, String> t) -> {
                    editing = true;
                    selectedRow = null;
                    if (!addingService && tableCommitting && lastCommittedRow != null && tbvService.getSelectionModel().getSelectedIndex() != lastCommittedRow) {
                        editing = false;
                        editing = true;

                        imgDelete.setDisable(true);
                        imgDelete.setOpacity(0.25);
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Confirmation");
                        alert.setContentText("Do you want to cancel the last commit? \n if not press cancel and press commit button.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            editing = false;
                            tableCommitting = false;
                            enableDefaultComponents();

                            if (oldAddress != null) {

                                services.get(lastCommittedRow).setAddress(oldAddress);
                                oldAddress = null;
                                tbvService.getSelectionModel().clearSelection();
                                tbvService.refresh();
                            }
                            if (oldName != null) {
                                services.get(lastCommittedRow).setName(oldName);
                                oldName = null;
                                tbvService.getSelectionModel().clearSelection();
                                tbvService.refresh();
                            }

                            if (oldServiceType != null) {
                                services.get(lastCommittedRow).setType(oldServiceType);
                                oldServiceType = null;
                                tbvService.getSelectionModel().clearSelection();
                                tbvService.refresh();
                            }

                        } else {

                            tbvService.getSelectionModel().select(lastCommittedRow);
                            tbvService.refresh();
                        }
                    } else {

                        oldAddress = null;
                        selectedRow = t.getTablePosition().getRow();
                        if (!addingService) {
                            editing = true;

                            if (t.getOldValue() != null) {

                                oldAddress = t.getOldValue();

                            }
                        }

                        if (addingService && !cancelling) {

                            if (tbvService.getSelectionModel().getSelectedIndex() != services.size() - 1) {

                                Alert Cancelalert = new Alert(AlertType.CONFIRMATION);
                                Cancelalert.setHeaderText(null);
                                Cancelalert.setTitle("Confirmation");
                                if (addingService) {
                                    Cancelalert.setContentText("Are you sure you want to cancel creating the last selected service?");
                                } else {

                                    Cancelalert.setContentText("Are you sure you want to cancel editing this cell?");
                                }
                                Optional<ButtonType> result = Cancelalert.showAndWait();
                                if (result.get() == ButtonType.OK) {

                                    if (addingService) {
                                        addingService = false;
                                        services.remove(services.size() - 1);

                                    }
                                    cancelling = true;
                                    editing = false;
                                    tableCommitting = false;

                                    enableDefaultComponents();

                                    updateServicesTable();

                                    LOGGER.info("creation cancelled");

                                } else {

                                    LOGGER.info("creation not cancelled");

                                    editing = false;

                                    tbvService.getSelectionModel().clearSelection();
                                    tbvService.getSelectionModel().focus(services.size() - 1);
                                    tbvService.getSelectionModel().select(services.size() - 1);
                                    tbvService.refresh();

                                }

                            }

                        }

                        handleTableImageViews();

                    }
                });

        tcAddress.setOnEditCommit((CellEditEvent<Service, String> t) -> {
            ((Service) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setAddress(t.getNewValue());
            tbvService.getSelectionModel().select(t.getTablePosition().getRow(), tcAddress);
            tbvService.edit(t.getTablePosition().getRow(), tcAddress);
            editing = false;

            if (!addingService) {
                lastCommittedRow = null;
            }

            try {

                if (t.getNewValue().trim().isEmpty()) {
                    //throw validation Error
                    LOGGER.warning("The address field is empty");
                    updateServicesTable();
                    enableDefaultComponents();
                    throw new FieldsEmptyException();
                }
                if (t.getNewValue().trim().length() > 255) {
                    //throw validation Error
                    LOGGER.warning("The field has more than 255 characters");
                    updateServicesTable();
                    enableDefaultComponents();
                    throw new MaxCharactersException();
                }

                if (!addingService && oldAddress != null) {

                    if (oldAddress.equals(t.getNewValue())) {
                        if (!tableCommitting) {
                            tableCommitting = false;
                        }
                    } else if (!oldAddress.equals(t.getNewValue())) {

                        tableCommitting = true;
                        lastCommittedRow = selectedRow;
                    }
                    handleTableImageViews();

                } else if (addingService) {

                    newAddress = t.getNewValue();
                    handleAddCommitting();
                    handleTableImageViews();
                }
            } catch (FieldsEmptyException | MaxCharactersException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(ex.getMessage());
                alert.showAndWait();
                //SETS THE CONFIRM AND CANCEL BUTTONS TO NOT CLICKABLE
                imgCommit.setDisable(true);
                imgCommit.setOpacity(0.25);
                imgCancel.setDisable(true);
                imgCancel.setOpacity(0.25);
            }

        });

        tcAddress.setOnEditCancel((CellEditEvent<Service, String> t) -> {

            if (!cancelling) {
                handleOnEditCancel();
            }

        });

        //Making Type Services table cell editable
        tcType.setCellFactory(ComboBoxTableCell.forTableColumn(types));
        //CellEditEvent lambdas to manage the diferent states of a cell
        tcType.setOnEditStart(
                (CellEditEvent<Service, String> t) -> {
                    editing = true;
                    selectedRow = null;

                    if (!addingService && tableCommitting && lastCommittedRow != null && tbvService.getSelectionModel().getSelectedIndex() != lastCommittedRow) {
                        editing = true;

                        imgDelete.setDisable(true);
                        imgDelete.setOpacity(0.25);
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Confirmation");
                        alert.setContentText("Do you want to cancel the last commit? \n if not press cancel and press commit button.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            editing = false;
                            tableCommitting = false;
                            enableDefaultComponents();

                            if (oldAddress != null) {

                                services.get(lastCommittedRow).setAddress(oldAddress);
                                oldAddress = null;
                                tbvService.getSelectionModel().clearSelection();
                                tbvService.refresh();
                            }
                            if (oldName != null) {
                                services.get(lastCommittedRow).setName(oldName);
                                oldName = null;
                                tbvService.getSelectionModel().clearSelection();
                                tbvService.refresh();
                            }

                            if (oldServiceType != null) {
                                services.get(lastCommittedRow).setType(oldServiceType);
                                oldServiceType = null;
                                tbvService.getSelectionModel().clearSelection();
                                tbvService.refresh();
                            }

                        } else {

                            tbvService.getSelectionModel().select(lastCommittedRow);
                            tbvService.refresh();

                        }

                    } else {

                        oldServiceType = null;
                        selectedRow = t.getTablePosition().getRow();

                        if (!addingService) {

                            if (t.getOldValue() != null) {

                                oldServiceType = t.getOldValue();
                                editing = true;
                            }
                        }
                        if (addingService && !cancelling) {

                            if (tbvService.getSelectionModel().getSelectedIndex() != services.size() - 1) {

                                Alert Cancelalert = new Alert(AlertType.CONFIRMATION);
                                Cancelalert.setHeaderText(null);
                                Cancelalert.setTitle("Confirmation");
                                Cancelalert.setContentText("Are you sure you want to cancel creating the last selected service?");
                                Optional<ButtonType> result = Cancelalert.showAndWait();
                                if (result.get() == ButtonType.OK) {

                                    services.remove(services.size() - 1);
                                    addingService = false;

                                    System.out.println("view.controllers.ServicesController.setEditableColumns()");

                                    cancelling = true;
                                    editing = false;
                                    tableCommitting = false;

                                    enableDefaultComponents();

                                    updateServicesTable();

                                    LOGGER.info("creation cancelled");

                                } else {

                                    LOGGER.info("creation not cancelled");

                                    editing = false;

                                    tbvService.getSelectionModel().clearSelection();
                                    tbvService.getSelectionModel().focus(services.size() - 1);
                                    tbvService.getSelectionModel().select(services.size() - 1);
                                    tbvService.refresh();

                                }

                            }

                        }

                        handleTableImageViews();
                    }
                });
        tcType.setOnEditCommit((CellEditEvent<Service, String> t) -> {
            ((Service) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setType(t.getNewValue());
            tbvService.getSelectionModel().select(t.getTablePosition().getRow(), tcType);
            tbvService.edit(t.getTablePosition().getRow(), tcType);

            editing = false;

            if (!addingService) {
                lastCommittedRow = null;
            }

            editing = false;
            if (!addingService && oldServiceType != null) {

                if (oldServiceType.equals(t.getNewValue())) {
                    if (!tableCommitting) {
                        tableCommitting = false;
                    }
                } else {
                    tableCommitting = true;
                    lastCommittedRow = selectedRow;
                }
                handleTableImageViews();
            } else if (addingService) {

                newType = t.getNewValue();
                handleAddCommitting();
                handleTableImageViews();
            }

        });

        tcType.setOnEditCancel((CellEditEvent<Service, String> t) -> {

            if (!cancelling) {

                handleOnEditCancel();
            }

        });

    }

    /**
     * This method checks the value of the cbService ComboBox and changes
     * components depending on it.
     *
     * @param event the MouseEvent onPressed event.
     */
    @FXML
    private void handleChangeComponents(ActionEvent event) {

        switch (cbService.getValue()) {
            case SELECT_ALL_SERVICES:
                clearServicesTable();
                cbServiceType.setPrefWidth(0);
                cbServiceType.setPrefHeight(0);
                cbServiceType.setVisible(false);
                cbServiceType.setDisable(true);

                tfServices.setDisable(true);
                tfServices.setVisible(true);
                tfServices.setPrefWidth(157);
                tfServices.setPrefHeight(31);

                break;
            case SELECT_BY_ADDRESS:
                clearServicesTable();
                cbServiceType.setPrefWidth(0);
                cbServiceType.setPrefHeight(0);
                cbServiceType.setVisible(false);
                cbServiceType.setDisable(true);

                tfServices.setDisable(false);
                tfServices.setVisible(true);
                tfServices.setPrefWidth(157);
                tfServices.setPrefHeight(31);

                break;
            case SELECT_BY_NAME:
                clearServicesTable();
                cbServiceType.setPrefWidth(0);
                cbServiceType.setPrefHeight(0);
                cbServiceType.setVisible(false);
                cbServiceType.setDisable(true);

                tfServices.setDisable(false);

                tfServices.setVisible(true);
                tfServices.setPrefWidth(157);
                tfServices.setPrefHeight(31);
                clearServicesTable();
                break;

            case SELECT_BY_TYPE:
                clearServicesTable();

                cbServiceType.setVisible(true);
                cbServiceType.setDisable(false);
                cbServiceType.setPrefWidth(157);
                cbServiceType.setPrefHeight(31);
                cbServiceType.setItems((ObservableList) types);
                cbServiceType.getSelectionModel().select(0);

                tfServices.setPrefWidth(0);
                tfServices.setPrefHeight(0);
                tfServices.setVisible(false);
                tfServices.setDisable(true);
                clearServicesTable();
                break;
        }
    }

    /**
     * Sets the searching boolean to true and saves the queryParameters obtained
     * in the textfield. updateServicesTable()is executed in order to load
     * services taking into account the selected value in the cbService
     * ComboBox. cleans the tfServices after all.
     *
     * @param event the MouseEvent onPressed event.
     */
    @FXML
    private void handleButtonSearch(ActionEvent event) {

        searching = true;
        saveQueryParameters();
        updateServicesTable();
        tfServices.setText("");

    }

    /**
     * This method loads all the services by address and disables or enables the
     * print imageView depending if there are 0, 1 or more services.
     *
     * Receives the address as a String from the tfUser Textfield and its
     * exceptions are controlled
     *
     * @return an ObservableList with the services.
     */
    private ObservableList<Service> loadServicesByAddress() {

        ObservableList<Service> servicesTableBean = null;

        try {

            if (!savedAddress.equals("") && savedAddress.length() <= 255 || tableCommitting) {

                List<Service> allServices = serviceManager.findServiceByAddress(savedAddress);

                if (allServices.size() < 1) {
                    imgPrint.setDisable(true);
                    imgPrint.setOpacity(0.25);

                    if (searching) {
                        throw new BusinessLogicException("No services have been found");
                    }

                } else {
                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);
                    servicesTableBean = FXCollections.observableArrayList(allServices);
                    tbvService.setItems(servicesTableBean);

                    if (searching) {
                        LOGGER.log(Level.INFO, "query done: list services by address with :{0} results", allServices.size());

                    }

                }
            } else {

                if (searching) {

                    if (savedAddress.length() > 255) {

                        throw new MaxCharactersException();

                    } else if (savedAddress.equals("")) {

                        throw new FieldsEmptyException();

                    }
                }
            }
        } catch (FieldsEmptyException ex) {
            LOGGER.log(Level.WARNING, "FieldsEmptyException thrown at loadServicesByAddress(): {0}", ex.getMessage());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("The search field is empty");
            alert.showAndWait();

            tfServices.requestFocus();

        } catch (MaxCharactersException ex) {
            LOGGER.log(Level.WARNING, "MaxCharactersException thrown at loadServicesByAddress(): {0}", ex.getMessage());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("Search field has a length higher than 255 characters.");
            alert.showAndWait();

            tfServices.requestFocus();

        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.INFO, "BusinessLogicException thrown at loadServicesByAddress(): {0}", ex.getMessage());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            tfServices.requestFocus();
        }
        tbvService.refresh();
        return servicesTableBean;
    }

    /**
     * Removes all the services of the table.
     */
    private void clearServicesTable() {

        if (services != null) {

            tbvService.getItems().removeAll(services);
            tbvService.refresh();

            services.forEach((s) -> {
                services.remove(s);
            });

            imgPrint.setDisable(true);
            imgPrint.setOpacity(0.25);
        } else {
            imgPrint.setDisable(true);
            imgPrint.setOpacity(0.25);

        }
    }

    /**
     * This method loads all the services by name and disables or enables the
     * print imageView depending if there are 0, 1 or more services.
     *
     * Receives the name as a String from the tfUser Textfield and its
     * exceptions are controlled
     *
     * @return an ObservableList with the services.
     */
    private ObservableList<Service> loadServicesByName() {

        ObservableList<Service> servicesTableBean = null;
        addingWithNullServices = false;
        try {

            if (!savedName.equals("") && savedName.length() <= 255 || tableCommitting) {

                List<Service> allServices = serviceManager.findServiceByName(savedName);
                servicesTableBean = FXCollections.observableArrayList(allServices);
                tbvService.setItems(servicesTableBean);

                if (allServices.size() < 1) {
                    imgPrint.setDisable(true);
                    imgPrint.setOpacity(0.25);
                    if (searching) {
                        throw new BusinessLogicException("No services have been found");
                    }

                } else {
                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);

                    LOGGER.log(Level.INFO, "query done: list services by name with :{0} results", allServices.size());
                }

            } else {

                if (searching) {

                    if (savedName.length() > 255) {

                        throw new MaxCharactersException();

                    } else if (savedName.equals("")) {
                        throw new FieldsEmptyException();
                    }

                }

            }

        } catch (FieldsEmptyException ex) {
            LOGGER.log(Level.INFO, "FieldsEmptyException thrown at loadServicesByName(): {0}", ex.getMessage());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("The search field is empty");
            alert.showAndWait();

            tfServices.requestFocus();
        } catch (MaxCharactersException ex) {
            LOGGER.log(Level.INFO, "MaxCharactersException thrown at loadServicesByName(): {0}", ex.getMessage());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("Search field has a length higher than 255 characters.");
            alert.showAndWait();

            tfServices.requestFocus();

        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.INFO, "BusinessLogicException thrown at loadServicesByName(): {0}", ex.getMessage());

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

            tfServices.requestFocus();
        }

        tbvService.refresh();
        return servicesTableBean;
    }

    /**
     * This method commits to the DB the additions and modifications of the
     * table. receives the index of the lastComittedRow in order to check if it
     * is an addition or a modification. uses updateServicesTable() after all.
     *
     * @param event the onPressed MouseEvent.
     */
    @FXML
    private void handleServiceCommit(MouseEvent event) {

        Service service = tbvService.getItems().get(lastCommittedRow);
        int pos = lastCommittedRow;

        try {
            if (pos == services.size() - 1 && service.getId().equals(Long.MIN_VALUE)) {

                serviceManager.createService(service);
                tbvService.refresh();
                LOGGER.info("Creation of new service");
            } else {
                serviceManager.updateService(service);
                tbvService.refresh();
                LOGGER.info("Update service");

            }
        } catch (BusinessLogicException ex) {
            Alert excAlert = new Alert(AlertType.INFORMATION);
            excAlert.setTitle("Error");
            excAlert.setContentText("There was an error with the edition of the service: " + ex.getMessage());
            excAlert.show();
            LOGGER.log(Level.SEVERE, "BusinessLogicException thrown at handleServiceCommit(): {0}", ex.getMessage());
        }

        lastCommittedRow = null;
        editing = false;
        tableCommitting = false;

        enableDefaultComponents();

        committingDB = true;

        updateServicesTable();

    }

    /**
     * This method loads all the services by type and disables or enables the
     * print imageView depending if there are 0, 1 or more services.
     *
     * Receives the type as a String from the cbServiceType cbServiceType and
     * its exceptions are controlled
     *
     * @return an ObservableList with the services.
     */
    private ObservableList<Service> loadServicesByType() {

        ObservableList<Service> servicesTableBean = null;
        addingWithNullServices = false;
        try {

            if (savedServiceType != null || tableCommitting) {

                List<Service> allServices = serviceManager.findServiceByType(savedServiceType);

                if (allServices.size() < 1) {
                    imgPrint.setDisable(true);
                    imgPrint.setOpacity(0.25);

                    if (searching) {
                        throw new BusinessLogicException("No services have been found");
                    }

                } else {
                    servicesTableBean = FXCollections.observableArrayList(allServices);

                    tbvService.setItems(servicesTableBean);

                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);

                    LOGGER.log(Level.INFO, "query done: list services by type with :{0} results", allServices.size());

                }
            }

        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.INFO, "BusinessLogicException thrown at loadServicesByType(): {0}", ex.getMessage());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

        }

        tbvService.refresh();
        return servicesTableBean;

    }

    /**
     * Action event handler for modify button. Deletes the selected service from
     * the DB and table.
     *
     * @param event the MouseEvent onPressed event.
     */
    @FXML
    private void handleDeleteRow(MouseEvent event) {

        //getting the service from the selected item of the table.
        Service service = tbvService.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to delete\n this Service with the following ID:" + service.getId() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                serviceManager.deleteService(service.getId());
                LOGGER.log(Level.INFO, "Service with the ID: {0} was correctly deleted.", service.getId());
                updateServicesTable();
                tbvService.refresh();

            } catch (BusinessLogicException ex) {
                LOGGER.log(Level.SEVERE, "BusinessLogicException thrown at handleDeleteRow(): {0}", ex.getMessage());
                Alert alert1 = new Alert(AlertType.ERROR);
                alert1.setTitle("AYUDA");
                alert1.setHeaderText("Error");
                alert1.setContentText(ex.getMessage());
                alert1.showAndWait();

            }

        } else {

            LOGGER.log(Level.INFO, "Service with the ID: {0} was not deleted.", service.getId());

            Alert alert3 = new Alert(AlertType.INFORMATION);
            alert3.setTitle("Service not deleted");
            alert3.setHeaderText(null);
            alert3.setContentText("Content not deleted");
            alert3.showAndWait();
        }
    }

    /**
     * This method updates the table depending on the states of the CRUD.
     */
    private void updateServicesTable() {

        //Updates the table data depending on the query selected on the cbService comboBox.
        //updates for additions
        if (addingService || addingWithNullServices && !searching) {

            cbService.setDisable(false);
            cbService.getSelectionModel().select(0);
            clearServicesTable();
            services = loadAllServices();

            if (committingDB) {
                addingService = false;
                addingWithNullServices = false;
                committingDB = false;
            }
            //updates for edit,delete and queries
        } else {

            switch (cbService.getValue()) {
                case SELECT_ALL_SERVICES:
                    clearServicesTable();
                    services = loadAllServices();

                    break;
                case SELECT_BY_ADDRESS:
                    clearServicesTable();
                    services = loadServicesByAddress();
                    break;
                case SELECT_BY_NAME:
                    clearServicesTable();
                    services = loadServicesByName();
                    break;

                case SELECT_BY_TYPE:

                    clearServicesTable();
                    services = loadServicesByType();

                    break;
            }

        }
        //disabling the variables to return the initial state.
        editing = false;
        cancelling = false;
        searching = false;
        tableCommitting = false;
        committingDB = false;
        searching = false;
    }

    /**
     * This method enables or disables the clickable ImageViews depending on the
     * different states of the CRUD.
     */
    private void handleTableImageViews() {

        if (editing) {

            imgAdd.setDisable(true);
            imgAdd.setOpacity(0.25);
            imgDelete.setDisable(true);
            imgDelete.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);

            cbService.setDisable(true);
            cbServiceType.setDisable(true);
            btnSearchService.setDisable(true);

            tfServices.setDisable(true);

        }

        if (addingService) {
            imgAdd.setDisable(true);
            imgAdd.setOpacity(0.25);
        }

        if (addingService && !editing) {

            imgCancel.setDisable(false);
            imgCancel.setOpacity(1);

        }

        //Control tableView upper Buttons when editing a cell and adding a service row      
        if (tableCommitting && !editing) {

            imgCommit.setDisable(false);
            imgCommit.setOpacity(1);
            imgCancel.setDisable(false);
            imgCancel.setOpacity(1);

        }

        if (!tableCommitting && !addingService && !editing) {

            enableDefaultComponents();

        }
    }

    /**
     * This method cancels the commit from the selected row when imgCancel is
     * pressed.
     *
     * @param event the onPressed MouseEvent.
     */
    @FXML
    private void handleCancelCommit(MouseEvent event) {

        Service selectedService = tbvService.getSelectionModel().getSelectedItem();

        Alert Cancelalert = new Alert(AlertType.CONFIRMATION);
        Cancelalert.setHeaderText(null);
        Cancelalert.setTitle("Confirmation");
        if (addingService) {
            Cancelalert.setContentText("Are you sure you want to cancel creating the service with the following ID:" + selectedService.getId() + "?");
        } else {

            Cancelalert.setContentText("Are you sure you want to cancel editing the service with the following ID:" + selectedService.getId() + "?");
        }
        Optional<ButtonType> result = Cancelalert.showAndWait();
        if (result.get() == ButtonType.OK) {

            if (addingService) {
                services.remove(services.size() - 1);
                addingService = false;
            }
            cancelling = true;
            editing = false;

            updateServicesTable();
            tbvService.getSelectionModel().clearSelection();
            enableDefaultComponents();

        } else {
            if (addingService) {
                LOGGER.info("commit cancelled");

                tbvService.getSelectionModel().select(services.size() - 1);

            } else {

                tbvService.getSelectionModel().select(lastCommittedRow);

                LOGGER.info("update cancelled");
            }

        }
    }

    /**
     * This method saves in Strings the values of the query components. It is
     * interesting to store them becuase they are used to refresh the table data
     * of a query when you do a modification.
     */
    private void saveQueryParameters() {

        savedAddress = null;
        savedName = null;
        savedServiceType = null;

        switch (cbService.getValue()) {

            case SELECT_BY_ADDRESS:

                savedAddress = tfServices.getText();
                break;
            case SELECT_BY_NAME:
                savedName = tfServices.getText();
                break;

            case SELECT_BY_TYPE:

                savedServiceType = cbServiceType.getValue();
                break;
        }
    }

    /**
     * This method enables or disables the components as they were when the
     * cbService value was selected. Is used just to reset all the imageViews
     * after doing different actions of the CRUD.
     */
    private void enableDefaultComponents() {
        //enabling sorting
        tcAddress.setSortable(true);
        tcName.setSortable(true);
        tcType.setSortable(true);

        //generic ImageViews
        imgCommit.setDisable(true);
        imgCommit.setOpacity(0.25);
        imgCancel.setDisable(true);
        imgCancel.setOpacity(0.25);
        imgDelete.setDisable(true);
        imgDelete.setOpacity(0.25);
        imgAdd.setDisable(false);
        imgAdd.setOpacity(1);
        btnSearchService.setDisable(false);
        cbService.setDisable(false);

        switch (cbService.getValue()) {
            case SELECT_ALL_SERVICES:

                //both disabled because there are no parameters.
                cbServiceType.setDisable(true);
                tfServices.setDisable(true);

                break;
            case SELECT_BY_TYPE:

                //tfServices disabled and cbServiceType enabled to query by type.
                cbServiceType.setDisable(false);
                tfServices.setDisable(true);

                break;
            default:

                //tfServices enabled and cbServiceType disabled to do the other queries.
                cbServiceType.setDisable(true);
                tfServices.setDisable(false);

                break;
        }
    }

    /**
     * This method prints a JasperReport with the displayed data in the table.
     *
     * @param event the onPressed MouseEvent .
     */
    @FXML
    private void handlePrintReport(MouseEvent event) {
        try {
            LOGGER.info("Beginning printing action...");
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/reports/AdminServiceReport.jrxml"));
            //Data for the report: a collection of User passed as a JRDataSource implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Service>) this.tbvService.getItems());
            //Get the name of the admin who printed the report as a parameter
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with the data of the table     
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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
     * This method is called in the CellEditEvent OnEditCancel. used to manage 2
     * types of cell edit cancel.
     */
    private void handleOnEditCancel() {

        //updates the table again when a cell edit is cancelled. Only in the case that you are editing a service.
        if (!addingService && editing) {

            cancelling = true;
            editing = false;
            tableCommitting = false;

            enableDefaultComponents();

            updateServicesTable();

            LOGGER.info("update cancelled");

        }
        if (addingService) {
            //to manage imageviews while adding a row.
            handleTableImageViews();
        }
    }

    /**
     * This method enables the table committing in order to enable the
     * imgCommit. imgCommit is enabled if the commited values accomplish the
     * requisites. If not exceptions are thrown in the CellEditEvent
     * OnEditCommit.
     */
    private void handleAddCommitting() {

        if (newAddress != null && newName != null && newType != null && newAddress.trim().length() <= 255 && newName.trim().length() <= 255 && newType.trim().length() <= 255) {

            //enabling tableCommiting to indicate that the row is ready to be committed.
            //in handleTableImageViews enables imgCommit and imgCancel if =true.
            tableCommitting = true;

            //we set the last committed row the one that we are currently creating
            lastCommittedRow = selectedRow;

            //reseting the values for a next addition
            newAddress = null;
            newName = null;
            newType = null;

        }
    }

}
