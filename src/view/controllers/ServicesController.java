/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import exceptions.BussinessLogicException;
import interfaces.ServicesManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Service;
import model.ServiceBean;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class ServicesController {

    private ServicesManager serviceManager;

    private Service service;

    private Stage stage;

    private final String SELECT_ALL_SERVICES = "Select all services";

    private final String SELECT_BY_ADDRESS = "Select from address";

    private final String SELECT_BY_NAME = "Select from name";

    private final String SELECT_BY_TYPE = "Select from type";

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
    private TableView<ServiceBean> tbvService;
    @FXML
    private TableColumn<ServiceBean, Long> tcId;
    @FXML
    private TableColumn<ServiceBean, String> tcAddress;
    @FXML
    private TableColumn<ServiceBean, String> tcName;
    @FXML
    private TableColumn<ServiceBean, String> tcType;
    @FXML
    private ImageView imgPrint;

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
        //Load the Dwelling data depending if its a Flat or a Room with the loadDwellingData() method
        loadAllServices();

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
                new PropertyValueFactory<>("wifi"));
        tcName.setCellValueFactory(
                new PropertyValueFactory<>("squareMeters"));
        tcType.setCellValueFactory(
                new PropertyValueFactory<>("constructionDate"));
       
        //SELECT THE FIRST COMBOBOX ITEM BY DEFAULT
        cbService.getSelectionModel().selectFirst();
        tcAddress.setMaxWidth(100);
        List<ServiceBean> services = new ArrayList<>();
        try {
            List<Service> allServices = serviceManager.findAll();
            if (allServices.size() > 0) {
                for (Service s : allServices) {
                    //String type = (d instanceof Flat) ? "Flat" : "Room";
                    services.add(new ServiceBean(s));
                }
                ObservableList<ServiceBean> servicesTableBean = FXCollections.observableArrayList(services);
                tbvService.setItems(servicesTableBean);
            } else {
                //The imgPrint will be disabled if there are not dwellings
                imgPrint.setDisable(true);
                imgPrint.setOpacity(0.25);
            }

        } catch (BussinessLogicException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        
        
    }

    private void loadAllServices() {

    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void setServiceManager(ServicesManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    private void handleChangeComponents(ActionEvent event) {
        switch (cbService.getValue()) {
            case SELECT_ALL_SERVICES:
                spinnerService.setDisable(true);
                tfServices.setDisable(true);
                break;
            case SELECT_BY_ADDRESS:
                spinnerService.setDisable(true);
                tfServices.setDisable(false);
                break;
            case SELECT_BY_NAME:
                spinnerService.setDisable(true);
                tfServices.setDisable(false);
                break;

            case SELECT_BY_TYPE:

                spinnerService.setDisable(true);
                tfServices.setDisable(false);
                break;
        }

    }

    private void handleTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {

        if (newValue != null) {
            imgDelete.setDisable(true);
            imgDelete.setOpacity(1);

        } else {
            imgDelete.setDisable(false);
            imgDelete.setOpacity(0.25);
        }
    }
    
}
