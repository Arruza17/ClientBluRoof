package view.controllers;

import interfaces.DwellingManager;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javax.ws.rs.core.GenericType;
import model.DwellingBean;
import model.Owner;
import model.User;
import restful.DwellingRestfulClient;

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
    private ComboBox<?> CBDwellings;
    @FXML
    private DatePicker dpConstructionDate;
    @FXML
    private Spinner<?> spRating;
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
    private TableView<DwellingBean> tableDwelling;
    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colWiFi;
    @FXML
    private TableColumn<?, ?> colSquareMeters;
    @FXML
    private TableColumn<?, ?> colConstructionDate;
    @FXML
    private TableColumn<?, ?> colRating;
    @FXML
    private TableColumn<?, ?> colMoreInfo;

    private static final Logger LOGGER = Logger.getLogger(DwellingController.class.getSimpleName());

    private DwellingBean dwelling;

    private User user;

    private Stage stage;


    /**
     * This method is used to initialize the stage
     *
     * @param root The parent object representing root node of the view graph
     */
    public void initStage(Parent root) {

        DwellingRestfulClient dwe = new DwellingRestfulClient();
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

        //if logged as an owner
        lblTitle.setText("My Dwellings");
        colAddress.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        colWiFi.setCellValueFactory(
                new PropertyValueFactory<>("squareMeters"));
        colConstructionDate.setCellValueFactory(
                new PropertyValueFactory<>("constructionDate"));
        colRating.setCellValueFactory(
                new PropertyValueFactory<>("rating"));
        ObservableList<DwellingBean> dwellings = FXCollections
                .observableArrayList(dwe.findAll(
                        new GenericType<List<DwellingBean>>() {
                }));
        //tableDwelling.setItems(dwellings);

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
