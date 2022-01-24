/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import enumerations.ServiceType;
import exceptions.BusinessLogicException;
import interfaces.ServicesManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Service;
import model.ServiceBean;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class ServicesController {

    private static final Logger LOGGER = Logger.getLogger(ServicesController.class.getName());

    private ServicesManager serviceManager;

    private Service service;

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

    public void initStage(Parent root) {
        LOGGER.info("Initializing DwellingWindow stage");
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
        stage.setTitle("DwellingWindow");
        //Sets the window not resizable
        stage.setResizable(false);
        //Shows the stage
        stage.show();
        LOGGER.info("DwellingWindow Open");

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

        //Checks if there are services to print. enables  print imageview when there are and disables it when there not
        if (services != null) {

            imgPrint.setDisable(false);
            imgPrint.setOpacity(1);
        } else {
            imgPrint.setDisable(true);
            imgPrint.setOpacity(0.25);

        }

        stage.show();

    }

    private ObservableList loadAllServices() {

        ObservableList<Service> servicesTableBean = null;
        try {

            List<Service> allServices = serviceManager.findAll();

            servicesTableBean = FXCollections.observableArrayList(allServices);
            tbvService.setItems(servicesTableBean);

        } catch (BusinessLogicException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
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

        LOGGER.info("Adding new empty admin to the table");

        Service s = new Service();

        services.add(new Service());

        TablePosition pos = new TablePosition(tbvService, services.size(), tcAddress);

        tbvService.getFocusModel().focus(pos);

        tbvService.requestFocus();

        imgCommit.setDisable(false);
        imgCommit.setOpacity(1);
        imgCancel.setDisable(false);
        imgCancel.setOpacity(1);
        imgAdd.setDisable(true);
        imgAdd.setOpacity(0.25);
        imgDelete.setDisable(true);
        imgDelete.setOpacity(0.25);

    }

    private void setEditableColumns() {

        //Making name Services table column editable
        tcName.setCellFactory(TextFieldTableCell.<Service>forTableColumn());
        tcName.setOnEditCommit(
                (CellEditEvent<Service, String> t) -> {
                    ((Service) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setName(t.getNewValue());
                    tbvService.getSelectionModel().select(t.getTablePosition().getRow(), tcName);
                    tbvService.edit(t.getTablePosition().getRow(), tcName);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);
                });

        tcName.setOnEditCancel((CellEditEvent<Service, String> t) -> {
            tbvService.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Making address Services table column editable
        tcAddress.setCellFactory(TextFieldTableCell.<Service>forTableColumn());
        tcAddress.setOnEditCommit(
                (CellEditEvent<Service, String> t) -> {
                    ((Service) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setAddress(t.getNewValue());
                    tbvService.getSelectionModel().select(t.getTablePosition().getRow(), tcAddress);
                    tbvService.edit(t.getTablePosition().getRow(), tcAddress);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);
                });

        tcAddress.setOnEditCancel((CellEditEvent<Service, String> t) -> {
            tbvService.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

        //Making Type Services table cell editable
        tcType.setCellFactory(ComboBoxTableCell.forTableColumn(types));
        tcType.setOnEditCommit(
                (CellEditEvent<Service, String> t) -> {
                    ((Service) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setType(t.getNewValue());
                    tbvService.getSelectionModel().select(t.getTablePosition().getRow(), tcType);
                    tbvService.edit(t.getTablePosition().getRow(), tcType);
                    imgCommit.setDisable(false);
                    imgCommit.setOpacity(1);
                    imgCancel.setDisable(false);
                    imgCancel.setOpacity(1);

                });

        tcType.setOnEditCancel((CellEditEvent<Service, String> t) -> {
            tbvService.refresh();
            imgCommit.setDisable(true);
            imgCommit.setOpacity(0.25);
            imgCancel.setDisable(true);
            imgCancel.setOpacity(0.25);
        });

    }

    @FXML
    private void handleChangeComponents(ActionEvent event) {

        switch (cbService.getValue()) {
            case SELECT_ALL_SERVICES:

                spinnerService.setDisable(true);
                tfServices.setDisable(true);
                //Load the Service data loadAllServices() method

                clearServicesTable();

                break;
            case SELECT_BY_ADDRESS:
                spinnerService.setDisable(true);
                tfServices.setDisable(false);

                clearServicesTable();

                break;
            case SELECT_BY_NAME:
                tfServices.setDisable(false);
                spinnerService.setDisable(true);

                clearServicesTable();

                break;

            case SELECT_BY_TYPE:

                spinnerService.setDisable(true);

                clearServicesTable();

                break;
        }
    }

    @FXML
    private void handleButtonSearch(ActionEvent event) {

        switch (cbService.getValue()) {
            case SELECT_ALL_SERVICES:
                clearServicesTable();
                services = loadAllServices();
                if (services != null) {
                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);
                }

                break;
            case SELECT_BY_ADDRESS:
                clearServicesTable();
                services = loadServicesByAddress();

                if (services != null) {
                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);

                }
                break;
            case SELECT_BY_NAME:

                clearServicesTable();
                if (services != null) {
                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);

                }

                break;

            case SELECT_BY_TYPE:

                clearServicesTable();
                if (services != null) {
                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);

                }

                break;
        }

    }

    private ObservableList<Service> loadServicesByAddress() {

        ObservableList<Service> servicesTableBean = null;
        try {

            List<Service> allServices = serviceManager.findServiceByAddress(tfServices.getText());
            servicesTableBean = FXCollections.observableArrayList(allServices);
            tbvService.setItems(servicesTableBean);

        } catch (BusinessLogicException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
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
                System.out.println(s.toString());
            }

            imgPrint.setDisable(true);
            imgPrint.setOpacity(0.25);
        } else {
            imgPrint.setDisable(true);
            imgPrint.setOpacity(0.25);
            System.out.println("no services");
        }
    }

}
