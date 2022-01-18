package view.controllers;

import model.DwellingTableBean;
import exceptions.BussinessLogicException;
import interfaces.DwellingManager;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Dwelling;
import model.User;

/**
 * FXML Controller class
 *
 * @author Ander Arruza
 */
public class OwnerWindowController {

    private DwellingManager dwellingManager;
    @FXML
    private Label lblTitle;
    @FXML
    private ComboBox<?> cbDwellings;
    @FXML
    private DatePicker dpConstructionDate;
    @FXML
    private Spinner<Float> spRating;
    @FXML
    private ImageView imgSearch;
    @FXML
    private ImageView imgCreateComment;
    @FXML
    private ImageView imgConfirmComment;
    @FXML
    private ImageView imgCancelComment;
    @FXML
    private ImageView imgDeleteDwelling;
    @FXML
    private ImageView imgPrint;
    @FXML
    private TableView<Dwelling> tableDwelling;
    @FXML
    private TableColumn<Dwelling, String> colType;
    @FXML
    private TableColumn<Dwelling, String> colAddress;
    @FXML
    private TableColumn<Dwelling, Boolean> colWiFi;
    @FXML
    private TableColumn<Dwelling, Double> colSquareMeters;
    @FXML
    private TableColumn<Dwelling, Date> colConstructionDate;
    @FXML
    private TableColumn<Dwelling, String> colRating;
    @FXML
    private TableColumn<Dwelling, String> colMoreInfo;

    private static final Logger LOGGER = Logger.getLogger(DwellingController.class.getSimpleName());

    private Dwelling dwelling;

    private User user;

    private Stage stage;

    /**
     * This method is used to initialize the stage
     *
     * @param root The parent object representing root node of the view graph
     */
    public void initStage(Parent root) {

        LOGGER.info("Initializing Owner/Guest-Window stage");
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
        //Add the combobox values
        ObservableList<String> optionsForCombo
                = FXCollections.observableArrayList(
                        "Select ",
                        "2",
                        "3"
                );
        cbDwellings.getItems();

        //if logged as an owner
        lblTitle.setText("My Dwellings");
        colAddress.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        colSquareMeters.setCellValueFactory(
                new PropertyValueFactory<>("squareMeters"));
        colWiFi.setCellValueFactory(
                new PropertyValueFactory<>("hasWiFi"));
        colConstructionDate.setCellValueFactory(
                new PropertyValueFactory<>("constructionDate"));
        colRating.setCellValueFactory(
                new PropertyValueFactory<>("rating"));

        ObservableList<Dwelling> dwellings = null;
        List <DwellingTableBean> dwellingTableBean = null;
        try {
            dwellings = FXCollections.observableArrayList(dwellingManager.findAll());
            
        } catch (BussinessLogicException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("AYUDA");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        
        tableDwelling.setItems(dwellings);

        //Shows the stage
        stage.show();
        LOGGER.info("Owner/Guest-Window Open");
    }

    @FXML
    private void handleChangeComponents(ActionEvent event) {
    }

    @FXML
    private void handleFilterSearch(MouseEvent event) {
    }

    @FXML
    private void handleAddComment(MouseEvent event) {
    }

    @FXML
    private void handleConfirmComment(MouseEvent event) {
    }

    @FXML
    private void handleCancelComment(MouseEvent event) {
    }

    @FXML
    private void handleDeleteDwelling(MouseEvent event) {
    }

    @FXML
    private void handlePrintDwellings(MouseEvent event) {
    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void setDwellingManager(DwellingManager dwellingManager) {
        this.dwellingManager = dwellingManager;
    }
}
