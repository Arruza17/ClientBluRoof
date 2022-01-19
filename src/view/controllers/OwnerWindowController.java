package view.controllers;

import model.DwellingTableBean;
import exceptions.BussinessLogicException;
import interfaces.DwellingManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import model.Flat;
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
    private ComboBox<String> cbDwellings;
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
    private TableView<DwellingTableBean> tableDwelling;
    @FXML
    private TableColumn<DwellingTableBean, String> colType;
    @FXML
    private TableColumn<DwellingTableBean, String> colAddress;
    @FXML
    private TableColumn<DwellingTableBean, Boolean> colWiFi;
    @FXML
    private TableColumn<DwellingTableBean, Double> colSquareMeters;
    @FXML
    private TableColumn<DwellingTableBean, Date> colConstructionDate;
    @FXML
    private TableColumn<DwellingTableBean, String> colRating;
    @FXML
    private TableColumn<DwellingTableBean, String> colMoreInfo;

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
        //Sets the datePicker and spinner to disabled
        dpConstructionDate.setDisable(true);
        spRating.setDisable(true);
        //Sets the confirm & cancel imgs to not clickable
        imgConfirmComment.setDisable(true);
        imgConfirmComment.setOpacity(0.25);
        imgCancelComment.setDisable(true);
        imgCancelComment.setOpacity(0.25);
        //Sets the the delete imgs to not clickable
        imgDeleteDwelling.setDisable(true);
        imgDeleteDwelling.setOpacity(0.25);
        //Add the combobox values
        ObservableList<String> optionsForCombo
                = FXCollections.observableArrayList(
                        "Select all my dwellings",
                        "Select from min construction date",
                        "Select from min rating"
                );
        cbDwellings.setItems(optionsForCombo);

        //if logged as an owner
        lblTitle.setText("My Dwellings");
        colType.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        colAddress.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        colWiFi.setCellValueFactory(
                new PropertyValueFactory<>("wifi"));
        colSquareMeters.setCellValueFactory(
                new PropertyValueFactory<>("squareMeters"));
        colConstructionDate.setCellValueFactory(
                new PropertyValueFactory<>("constructionDate"));
        colRating.setCellValueFactory(
                new PropertyValueFactory<>("rating"));
        colMoreInfo.setCellValueFactory(
                new PropertyValueFactory<>("moreInfo"));

        List<DwellingTableBean> dwellings = new ArrayList<>();
        try {
            List<Dwelling> allDwellings = dwellingManager.findAll();
            if (allDwellings.size() > 0) {
                for (Dwelling d : allDwellings) {
                    //String type = (d instanceof Flat) ? "Flat" : "Room";
                    dwellings.add(new DwellingTableBean(d, "TYPE"));
                }
                ObservableList<DwellingTableBean> dwellingsTableBean = FXCollections.observableArrayList(dwellings);
                tableDwelling.setItems(dwellingsTableBean);
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

        //Shows the stage
        stage.show();
        LOGGER.info("Owner/Guest-Window Open");
    }

    @FXML
    private void handleChangeComponents(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AYUDA");
        alert.setHeaderText("Cambio de elemento");
        alert.setContentText("A");
        alert.showAndWait();
    }

    @FXML
    private void handleFilterSearch(MouseEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AYUDA");
        alert.setHeaderText("Boton de search clickado");
        alert.setContentText("A");
        alert.showAndWait();

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
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AYUDA");
        alert.setHeaderText("Boton de print");
        alert.setContentText("A");
        alert.showAndWait();
    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void setDwellingManager(DwellingManager dwellingManager) {
        this.dwellingManager = dwellingManager;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
