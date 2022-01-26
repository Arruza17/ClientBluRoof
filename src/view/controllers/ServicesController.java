/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import enumerations.ServiceType;
import exceptions.BusinessLogicException;
import exceptions.FieldsEmptyException;
import interfaces.ServicesManager;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Service;

/**
 * FXML Controller class
 *
 * @author Adrián Pérez
 */
public class ServicesController {

    private static final Logger LOGGER = Logger.getLogger(ServicesController.class.getName());

    private ServicesManager serviceManager;

    private Stage stage;

    private final String SELECT_ALL_SERVICES = "Select all services";

    private final String SELECT_BY_ADDRESS = "Select from address";

    private final String SELECT_BY_NAME = "Select from name";

    private final String SELECT_BY_TYPE = "Select from type";

    private ServiceType serviceType;

    @FXML
    private ComboBox<String> cbService;
    @FXML
    private Spinner<Integer> spinnerService;
    @FXML
    private TextField tfServices;
    @FXML
    private Button btnSearchService;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imgAdd;
    @FXML
    private ImageView imgDelete;
    @FXML
    private ImageView imgCommit;
    @FXML
    private ImageView imgCancel;
    @FXML
    private TableView<Service> tbvService;
    @FXML
    private TableColumn<Service, Long> tcId;
    @FXML
    private TableColumn<Service, String> tcAddress;
    @FXML
    private TableColumn<Service, String> tcName;
    @FXML
    private TableColumn<Service, String> tcType;
    @FXML
    private ImageView imgPrint;
    private ObservableList<Service> services;
    private ObservableList<String> types;

    private ComboBox type;
    @FXML
    private ComboBox<?> cbServiceType;

    private boolean addingService;

    private boolean committing;

    private boolean deleting;

    private boolean searching;

    private boolean editing;

    private String savedAddress;

    private String savedName;

    private String savedServiceType;

    public void initStage(Parent root) {
        LOGGER.info("Initializing ServiceWindow stage");
        //Creation of a new Scene
        Scene scene = new Scene(root);
        /*
        //Save the route of the .css file
        String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
        //Sets the .css to the Scene
        scene.getStylesheets().add(css);
        stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
         */
        //Sets the scene to the stage
        stage.setScene(scene);
        //Sets the Title of the Window
        stage.setTitle("ServiceWindow");
        //Sets the window not resizable
        stage.setResizable(false);
        //Shows the stage
        stage.show();
        LOGGER.info("ServiceWindow Open");

        //Sets the confirm & cancel imgs to not clickable
        imgCommit.setDisable(true);
        imgCommit.setOpacity(0.25);
        imgCancel.setDisable(true);
        imgCancel.setOpacity(0.25);
        //Sets the the delete imgs to not clickable
        imgDelete.setDisable(true);
        imgDelete.setOpacity(0.25);

        /*
        if (!spinnerService.isDisabled()) {
            spinnerService.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99));
            spinnerService.setEditable(true);
        }
         */
        //Add the combobox values
        ObservableList<String> optionsForCombo;
        optionsForCombo = FXCollections.observableArrayList(
                SELECT_ALL_SERVICES,
                SELECT_BY_ADDRESS,
                SELECT_BY_NAME,
                SELECT_BY_TYPE
        );
        cbService.setItems(optionsForCombo);

        tbvService.getSelectionModel().selectedItemProperty()
                .addListener(this::handleTableSelectionChanged);

        tcId.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        tcAddress.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        tcName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        type = new ComboBox();

        types = FXCollections.observableArrayList();

        for (ServiceType st : serviceType.values()) {
            types.add(st.toString());
        }

        type.setItems((ObservableList) types);

        tcType.setCellValueFactory(
                new PropertyValueFactory<>("type"));

        tbvService.setEditable(true);
        tbvService.setMinWidth(500);

        //SELECT THE FIRST COMBOBOX ITEM BY DEFAULT
        cbService.getSelectionModel().selectFirst();
        tcAddress.setMaxWidth(100);

        //Making table columns editable. 
        setEditableColumns();

        //Loads all the services in the table
        services = loadAllServices();

        stage.show();

    }

    private ObservableList loadAllServices() {

        ObservableList<Service> servicesTableBean = null;
        try {

            List<Service> allServices = serviceManager.findAll();

            servicesTableBean = FXCollections.observableArrayList(allServices);
            tbvService.setItems(servicesTableBean);

            if (allServices.size() < 1) {
                imgPrint.setDisable(true);
                imgPrint.setOpacity(0.25);

            } else {
                imgPrint.setDisable(false);
                imgPrint.setOpacity(1);

            }

        } catch (BusinessLogicException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("No services have been found");
            alert.showAndWait();
        }

        tbvService.refresh();
        return servicesTableBean;

    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void setServiceManager(ServicesManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    private void handleTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {

        if (newValue != null) {
            imgDelete.setDisable(false);
            imgDelete.setOpacity(1);

        } else {
            imgDelete.setDisable(true);
            imgDelete.setOpacity(0.25);
        }
    }

    @FXML
    private void handleServiceCreation(MouseEvent event) {

        LOGGER.info("Adding new empty service to the table");

        Service s = new Service();
        s.setId(Long.MIN_VALUE);
        services.add(s);

        tbvService.getSelectionModel().select(services.size() - 1);
        tbvService.layout();
        tbvService.getFocusModel().focus(services.size() - 1, tcAddress);
        tbvService.edit(services.size() - 1, tcAddress);

        addingService = true;

        //Checks if you are editing or creating, and disables all the components of the ui
        handleEditModeComponents();

    }

    private void setEditableColumns() {

        //Making name Services table column editable
        tcName.setCellFactory(TextFieldTableCell.<Service>forTableColumn());

        tcName.setOnEditStart(
                (CellEditEvent<Service, String> t) -> {
                    editing = true;
                    handleEditModeComponents();
                });

        tcName.setOnEditCommit(
                (CellEditEvent<Service, String> t) -> {
                    ((Service) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setName(t.getNewValue());
                    tbvService.getSelectionModel().select(t.getTablePosition().getRow(), tcName);
                    tbvService.edit(t.getTablePosition().getRow(), tcName);

                    committing = true;
                    editing = false;
                    if (addingService) {

                        handleEditModeComponents();

                        imgCommit.setDisable(true);
                        imgCommit.setOpacity(0.25);

                    } else {

                        handleEditModeComponents();

                    }

                });

        tcName.setOnEditCancel((CellEditEvent<Service, String> t) -> {
            editing = false;
            handleEditModeComponents();
            tbvService.refresh();

        });

        //Making address Services table column editable
        tcAddress.setCellFactory(TextFieldTableCell.<Service>forTableColumn());
        tcAddress.setOnEditStart(
                (CellEditEvent<Service, String> t) -> {
                    editing = true;
                    handleEditModeComponents();
                });
        tcAddress.setOnEditCommit(
                (CellEditEvent<Service, String> t) -> {
                    ((Service) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setAddress(t.getNewValue());
                    tbvService.getSelectionModel().select(t.getTablePosition().getRow(), tcAddress);
                    tbvService.edit(t.getTablePosition().getRow(), tcAddress);

                    committing = true;
                    editing = false;
                    if (addingService) {

                        handleEditModeComponents();

                        imgCommit.setDisable(true);
                        imgCommit.setOpacity(0.25);

                    } else {

                        handleEditModeComponents();

                    }

                });

        tcAddress.setOnEditCancel((CellEditEvent<Service, String> t) -> {
            editing = false;
            handleEditModeComponents();
            tbvService.refresh();

        });

        //Making Type Services table cell editable
        tcType.setCellFactory(ComboBoxTableCell.forTableColumn(types));
        tcType.setOnEditStart(
                (CellEditEvent<Service, String> t) -> {
                    editing = true;
                    handleEditModeComponents();
                });
        tcType.setOnEditCommit(
                (CellEditEvent<Service, String> t) -> {
                    ((Service) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setType(t.getNewValue());
                    tbvService.getSelectionModel().select(t.getTablePosition().getRow(), tcType);
                    tbvService.edit(t.getTablePosition().getRow(), tcType);
                    committing = true;
                    editing = false;
                    handleEditModeComponents();
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);

                });

        tcType.setOnEditCancel((CellEditEvent<Service, String> t) -> {
            editing = false;
            handleEditModeComponents();
            tbvService.refresh();

        });

    }

    @FXML
    private void handleChangeComponents(ActionEvent event) {

        switch (cbService.getValue()) {
            case SELECT_ALL_SERVICES:

                cbServiceType.setPrefWidth(0);
                cbServiceType.setPrefHeight(0);
                cbServiceType.setVisible(false);
                cbServiceType.setDisable(true);

                spinnerService.setDisable(true);
                tfServices.setDisable(true);
                tfServices.setVisible(true);
                tfServices.setPrefWidth(157);
                tfServices.setPrefHeight(31);

                break;
            case SELECT_BY_ADDRESS:

                cbServiceType.setPrefWidth(0);
                cbServiceType.setPrefHeight(0);
                cbServiceType.setVisible(false);
                cbServiceType.setDisable(true);

                spinnerService.setDisable(true);
                tfServices.setDisable(false);
                tfServices.setVisible(true);
                tfServices.setPrefWidth(157);
                tfServices.setPrefHeight(31);

                break;
            case SELECT_BY_NAME:

                cbServiceType.setPrefWidth(0);
                cbServiceType.setPrefHeight(0);
                cbServiceType.setVisible(false);
                cbServiceType.setDisable(true);

                tfServices.setDisable(false);
                spinnerService.setDisable(true);
                tfServices.setVisible(true);
                tfServices.setPrefWidth(157);
                tfServices.setPrefHeight(31);

                break;

            case SELECT_BY_TYPE:

                spinnerService.setDisable(true);
                cbServiceType.setVisible(true);
                cbServiceType.setDisable(false);
                cbServiceType.setPrefWidth(157);
                cbServiceType.setPrefHeight(31);
                cbServiceType.setItems((ObservableList) types);

                tfServices.setPrefWidth(0);
                tfServices.setPrefHeight(0);
                tfServices.setVisible(false);
                tfServices.setDisable(true);

                break;
        }
    }

    @FXML
    private void handleButtonSearch(ActionEvent event) {

        searching = true;
        saveQueryParameters();
        updateServicesTable();
        tfServices.setText("");

    }

    private ObservableList<Service> loadServicesByAddress() {

        ObservableList<Service> servicesTableBean = null;

        try {

            if (tfServices.getText() != null || committing) {

                List<Service> allServices = serviceManager.findServiceByAddress(savedAddress);

                if (allServices.size() < 1) {
                    imgPrint.setDisable(true);
                    imgPrint.setOpacity(0.25);

                } else {
                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);
                    servicesTableBean = FXCollections.observableArrayList(allServices);
                    tbvService.setItems(servicesTableBean);

                }
            } else {

                throw new FieldsEmptyException();
            }

        } catch (BusinessLogicException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("The search field is empty");
            alert.showAndWait();
        } catch (FieldsEmptyException ex) {
            Logger.getLogger(ServicesController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("The search field is empty");
            alert.showAndWait();

        }

        tbvService.refresh();
        return servicesTableBean;

    }

    private void clearServicesTable() {

        if (services != null) {
            tbvService.getItems().removeAll(services);
            tbvService.refresh();

            for (Service s : services) {

                services.remove(s);
            }

            imgPrint.setDisable(true);
            imgPrint.setOpacity(0.25);
        } else {
            imgPrint.setDisable(true);
            imgPrint.setOpacity(0.25);

        }
    }

    private ObservableList<Service> loadServicesByName() {

        ObservableList<Service> servicesTableBean = null;
        try {

            if (tfServices.getText() != null || committing) {

                List<Service> allServices = serviceManager.findServiceByName(savedName);
                servicesTableBean = FXCollections.observableArrayList(allServices);
                tbvService.setItems(servicesTableBean);

                if (allServices.size() < 1) {
                    imgPrint.setDisable(true);
                    imgPrint.setOpacity(0.25);
                } else {
                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);

                }

            } else {

                throw new FieldsEmptyException();

            }

        } catch (BusinessLogicException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("Database error");
            alert.showAndWait();

        } catch (FieldsEmptyException ex) {
            Logger.getLogger(ServicesController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("The search field is empty");
            alert.showAndWait();
        }

        tbvService.refresh();
        return servicesTableBean;
    }

    @FXML
    private void handleServiceCommit(MouseEvent event) {

        Service service = tbvService.getSelectionModel().getSelectedItem();
        int pos = tbvService.getSelectionModel().getSelectedIndex();
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
            LOGGER.log(Level.SEVERE, "BusinessLogicException thrown at handleTableCommit(): {0}", ex.getMessage());
        }

        editing = false;

        imgAdd.setDisable(false);
        imgAdd.setOpacity(1);
        imgCommit.setDisable(true);
        imgCommit.setOpacity(0.25);
        imgCancel.setDisable(true);
        imgCancel.setOpacity(0.25);

        cbService.setDisable(false);

        tfServices.setDisable(false);
        tbvService.setDisable(false);

        committing = true;

        updateServicesTable();

    }

    private ObservableList<Service> loadServicesByType() {

        ObservableList<Service> servicesTableBean = null;
        try {

            if (cbServiceType.getValue() != null || committing) {

                List<Service> allServices = serviceManager.findServiceByType(savedServiceType);

                if (allServices.size() < 1) {
                    imgPrint.setDisable(true);
                    imgPrint.setOpacity(0.25);

                } else {
                    servicesTableBean = FXCollections.observableArrayList(allServices);

                    tbvService.setItems(servicesTableBean);

                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);

                }
            } else {

                throw new FieldsEmptyException();

            }
        } catch (BusinessLogicException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("The search field is empty");
            alert.showAndWait();

        } catch (FieldsEmptyException ex) {
            Logger.getLogger(ServicesController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Error");
            alert.setContentText("The service type comboBox is empty");
            alert.showAndWait();
        }

        tbvService.refresh();
        return servicesTableBean;

    }

    @FXML
    private void handleDeleteRow(MouseEvent event) {
        Service service = tbvService.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to delete\n this Service with the following ID:" + service.getId() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                serviceManager.deleteService(service.getId());
                deleting = true;
                updateServicesTable();
                tbvService.refresh();

            } catch (BusinessLogicException ex) {
                Alert alert1 = new Alert(AlertType.ERROR);
                alert1.setTitle("AYUDA");
                alert1.setHeaderText("Error");
                alert1.setContentText(ex.getMessage());
                alert1.showAndWait();

            }

        } else {
            Alert alert3 = new Alert(AlertType.INFORMATION);
            alert3.setTitle("Service not deleted");
            alert3.setHeaderText(null);
            alert3.setContentText("Content not deleted");
            alert3.showAndWait();
        }
    }

    private void updateServicesTable() {

        //Updates the table data depending on the query selected on the cbService comboBox.
        //updates for edit,delete and queries
        if (!addingService && committing || deleting || searching) {

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
            deleting = false;
            committing = false;
            searching = false;
            //updates when a row is added
        } else if (addingService && committing) {

            cbService.getSelectionModel().select(0);
            clearServicesTable();
            services = loadAllServices();

            addingService = false;
            committing = false;

        }

        committing = false;
        searching = false;

    }

    private void handleEditModeComponents() {

        if (editing) {

            imgAdd.setDisable(true);
            imgAdd.setOpacity(0.25);
            imgDelete.setDisable(true);
            imgDelete.setOpacity(0.25);

            cbService.setDisable(true);
            cbServiceType.setDisable(true);
            btnSearchService.setDisable(true);
            btnBack.setDisable(true);

            tfServices.setDisable(true);

        }

        if (editing && addingService) {
            imgCancel.setDisable(false);
            imgCancel.setOpacity(1);
        }

        if (committing && !addingService) {

            imgCommit.setDisable(false);
            imgCommit.setOpacity(1);
            imgCancel.setDisable(false);
            imgCancel.setOpacity(1);
        } else {

            imgAdd.setDisable(false);
            imgAdd.setOpacity(1);
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);

            btnSearchService.setDisable(false);
            btnBack.setDisable(false);
            cbService.setDisable(false);
            cbServiceType.setDisable(false);

            tfServices.setDisable(false);
        }
    }

    @FXML
    private void handleCancelCommit(MouseEvent event) {

        Service selectedService = tbvService.getSelectionModel().getSelectedItem();

        Alert Cancelalert = new Alert(AlertType.CONFIRMATION);
        Cancelalert.setHeaderText(null);
        Cancelalert.setTitle("Confirmation");
        if (addingService) {
            Cancelalert.setContentText("Are you sure you want to cancel creating the service with the following ID:" + selectedService.getId() + "?");
        }
        Cancelalert.setContentText("Are you sure you want to cancel editing the service with the following ID:" + selectedService.getId() + "?");
        Optional<ButtonType> result = Cancelalert.showAndWait();
        if (result.get() == ButtonType.OK) {

            if (addingService) {
                services.remove(services.size() - 1);
                addingService = false;

            }
            editing = false;

            updateServicesTable();

            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
            imgAdd.setDisable(false);
            imgAdd.setOpacity(1);
            tbvService.setItems(services);
            tbvService.refresh();
            tbvService.getSelectionModel().clearSelection(tbvService.getSelectionModel().getSelectedIndex());

        } else {
            if (addingService) {
                LOGGER.info("commit cancelled");
            }
            LOGGER.info("update cancelled");
        }

    }

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

                savedServiceType = cbServiceType.getValue().toString();
                break;
        }
    }

}
