package view.controllers;

import exceptions.NotValidDateValueException;
import exceptions.BussinessLogicException;
import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
import exceptions.NotValidSquareMetersValueException;
import interfaces.DwellingManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Dwelling;
import model.Owner;
import model.User;

/**
 * FXML Controller class for OwnerWindow.fxml
 *
 * @author Ander Arruza
 */
public class OwnerWindowController {

    //ALL THE FXML OBJECTS
    @FXML
    private Label lblTitle;
    @FXML
    private ComboBox<String> cbDwellings;
    @FXML
    private DatePicker dpConstructionDate;
    @FXML
    private Spinner<Integer> spRating;
    @FXML
    private ImageView imgSearch;
    @FXML
    private ImageView imgCreateNewDwelling;
    @FXML
    private ImageView imgConfirmNewDwelling;
    @FXML
    private ImageView imgCancelNewDwelling;
    @FXML
    private ImageView imgDeleteDwelling;
    @FXML
    private ImageView imgPrint;
    @FXML
    private TableView<Dwelling> tableDwelling;
    @FXML
    private TableColumn<Dwelling, String> colAddress;
    @FXML
    private TableColumn<Dwelling, Boolean> colWiFi;
    @FXML
    private TableColumn<Dwelling, String> colSquareMeters;
    @FXML
    private TableColumn<Dwelling, String> colConstructionDate;
    @FXML
    private TableColumn<Dwelling, String> colRating;
    @FXML
    private TableColumn<Dwelling, String> colMoreInfo;
    //MY OBJECTS
    private static final Logger LOGGER = Logger.getLogger(DwellingController.class.getSimpleName());

    private User user;

    private Stage stage;

    private DwellingManager dwellingManager;

    private final String SELECT_ALL_DWELLINGS = "All my dwellings";

    private final String SELECT_BY_MIN_CONSTRUCTION_DATE = "Min construction date";

    private final String SELECT_BY_MIN_RATING = "Min rating";

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    private ObservableList<Dwelling> dwellingsCollectionTable;
    /**
     * Regex validating a float value, such as 1 or 1.2
     */
    private final String regexDouble = "^((\\d+\\.)?\\d+)$";
    /**
     * Regex valudating a date in a dd/MM/yyyy format
     */
    private final String regexDate = "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";

    /**
     * Method for initializing OwnerWindowController Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        try {
            LOGGER.info("Initializing Owner/Guest-Window stage");
            //Creation of a new Scene
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
            //Sets the scene to the stage
            stage.setScene(scene);
            //Sets the Title of the Window
            stage.setTitle("DwellingWindow");
            //Sets the window not resizable
            stage.setResizable(false);
            //Sets the column RATING & SQUARE METERS ro center-right
            colRating.setStyle("-fx-alignment: CENTER-RIGHT;");
            colConstructionDate.setStyle("-fx-alignment: CENTER;");
            colSquareMeters.setStyle("-fx-alignment: CENTER-RIGHT;");
            //Sets the column More info to center
            colMoreInfo.setStyle("-fx-alignment: CENTER;");
            //Sets the datePicker and spinner to disabled
            dpConstructionDate.setDisable(true);
            spRating.setDisable(true);
            //Sets the the delete imgs to not clickable
            imgDeleteDwelling.setDisable(true);
            imgDeleteDwelling.setOpacity(0.25);
            imgConfirmNewDwelling.setDisable(true);
            imgConfirmNewDwelling.setOpacity(0.25);
            imgCancelNewDwelling.setDisable(true);
            imgCancelNewDwelling.setOpacity(0.25);
            //Add the combobox values
            ObservableList<String> optionsForCombo;
            optionsForCombo = FXCollections.observableArrayList(
                    SELECT_ALL_DWELLINGS,
                    SELECT_BY_MIN_CONSTRUCTION_DATE,
                    SELECT_BY_MIN_RATING
            );
            cbDwellings.setItems(optionsForCombo);
            //Add listener to the rows selected
            tableDwelling.getSelectionModel().selectedItemProperty()
                    .addListener(this::handleTableSelectionChanged);

            //if logged as an owner
            lblTitle.setText("My Dwellings");
            //Select the first comboBox item by default
            cbDwellings.getSelectionModel().selectFirst();
            //Load all the dwellings by default

            //Add the editable table
            tableDwelling.setEditable(true);
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colAddress.setCellFactory(TextFieldTableCell.<Dwelling>forTableColumn());
            colAddress.setOnEditCommit(
                    (CellEditEvent<Dwelling, String> t) -> {
                        {
                            try {
                                if (t.getNewValue().isEmpty()) {
                                    throw new FieldsEmptyException();
                                }
                                if (t.getNewValue().length() > 255) {
                                    throw new MaxCharactersException();
                                }

                                ((Dwelling) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setAddress(t.getNewValue());
                                imgConfirmNewDwelling.setDisable(false);
                                imgConfirmNewDwelling.setOpacity(1);
                                imgCancelNewDwelling.setDisable(false);
                                imgCancelNewDwelling.setOpacity(1);
                            } catch (FieldsEmptyException | MaxCharactersException ex) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Error");
                                alert.setHeaderText(ex.getMessage());
                                alert.showAndWait();
                                imgConfirmNewDwelling.setDisable(true);
                                imgConfirmNewDwelling.setOpacity(0.25);
                                imgCancelNewDwelling.setDisable(true);
                                imgCancelNewDwelling.setOpacity(0.25);
                            }
                        }

                    });

            colSquareMeters.setCellValueFactory(cellData
                    -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSquareMeters())));
            colSquareMeters.setCellFactory(TextFieldTableCell.<Dwelling>forTableColumn());
            colSquareMeters.setOnEditCommit(
                    (CellEditEvent<Dwelling, String> t) -> {
                        try {
                            if (t.getNewValue().isEmpty()) {
                                LOGGER.warning("The field is empty");
                                throw new FieldsEmptyException();
                            }
                            if (!t.getNewValue().matches(regexDouble)) {
                                //throw validation Error
                                LOGGER.warning("The squareMeter value is not valid");
                                throw new NotValidSquareMetersValueException("The squareMeter value is not valid");
                            }
                            ((Dwelling) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setSquareMeters(Double.valueOf(t.getNewValue()));
                            imgConfirmNewDwelling.setDisable(false);
                            imgConfirmNewDwelling.setOpacity(1);
                            imgCancelNewDwelling.setDisable(false);
                            imgCancelNewDwelling.setOpacity(1);
                        } catch (FieldsEmptyException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.showAndWait();
                        } catch (NotValidSquareMetersValueException ex) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(ex.getMessage());
                            alert.setContentText("Valid values:\n1\n1.2");
                            alert.showAndWait();
                        }
                    });
            colConstructionDate.setCellValueFactory(cellData
                    -> new SimpleStringProperty(dateFormatter.format(cellData.getValue().getConstructionDate())));
            colConstructionDate.setCellFactory(TextFieldTableCell.<Dwelling>forTableColumn());
            colConstructionDate.setOnEditCommit(
                    (CellEditEvent<Dwelling, String> t) -> {
                        try {

                            if (t.getNewValue().isEmpty()) {
                                LOGGER.warning("The field is empty");
                                throw new FieldsEmptyException();
                            }

                            ((Dwelling) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setConstructionDate(
                                    dateFormatter.parse(t.getNewValue())
                            );
                            imgConfirmNewDwelling.setDisable(false);
                            imgConfirmNewDwelling.setOpacity(1);
                            imgCancelNewDwelling.setDisable(false);
                            imgCancelNewDwelling.setOpacity(1);
                        } catch (FieldsEmptyException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.showAndWait();
                        } catch (ParseException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.setContentText("Valid value:\ndd/MM/yyyy\nex. 17/11/2008");
                            alert.showAndWait();
                        }

                    });

            colRating.setCellValueFactory(
                    new PropertyValueFactory<>("rating"));
            colMoreInfo.setCellValueFactory(
                    new PropertyValueFactory<>("moreInfo"));
            //CARGAR LOS DATOS EN LA TABLA
            dwellingsCollectionTable = FXCollections.observableArrayList(dwellingManager.findAll());
            /*
                            Add a listener for checkbox value changes noting that the
                            CheckBoxTableCell renders the CheckBox 'live', meaning that the 
                            CheckBox is always interactive. A side-effect of this is that the 
                            usual editing callbacks (such as on edit commit) will not be called. 
                            If you want to be notified of changes, it is recommended to directly 
                            observe the boolean properties that are manipulated by the CheckBox 
                            (see description for CheckBoxTableCell in javadoc)
                            So we iterate on table items adding listeners for property being 
                            represented by the checkbox.
                            We use the lambda implementation to access the dwelling object in
                            whichthe status property is.
             */
            //First, set the column cell factory:
            colWiFi.setCellFactory(
                    CheckBoxTableCell.<Dwelling>forTableColumn(colWiFi));
            //then, set the value cell factory:
            colWiFi.setCellValueFactory(
                    new PropertyValueFactory<>("hasWiFi"));
            dwellingsCollectionTable.forEach(
                    d -> d.hasWiFiProperty()
                            .addListener((observable, oldValue, newValue) -> {
                                LOGGER.log(Level.INFO,
                                        "Status property changed.newvalue {0}",
                                        newValue.toString());
                                LOGGER.log(Level.INFO,
                                        "User modified: {0}",
                                        d.getHasWiFi());
                            })
            );
            tableDwelling.setItems(dwellingsCollectionTable);

            //ESTABLECIDOS
            //Shows the stage
            stage.show();
            LOGGER.info("Owner/Guest-Window Open");
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            e.printStackTrace();
            //ALERT
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void handleChangeComponents(ActionEvent event) {
        switch (cbDwellings.getValue()) {
            case SELECT_ALL_DWELLINGS:
                dpConstructionDate.setDisable(true);
                spRating.setDisable(true);
                break;
            case SELECT_BY_MIN_CONSTRUCTION_DATE:
                dpConstructionDate.setDisable(false);
                spRating.setDisable(true);
                break;
            case SELECT_BY_MIN_RATING:
                dpConstructionDate.setDisable(true);
                spRating.setDisable(false);
                break;
            default:
                break;
        }

    }

    /**
     * Method that takes the filter values to load the data calling the logical
     * part
     *
     * @param event the mouse event
     */
    @FXML
    private void handleFilterSearch(MouseEvent event) {
        try {

            if (tableDwelling.getItems().size() > 1) {
                dwellingsCollectionTable.clear();
            }

            switch (cbDwellings.getValue()) {
                case SELECT_ALL_DWELLINGS:
                    dwellingsCollectionTable = FXCollections.observableArrayList(dwellingManager.findAll());
                    //The imgPrint will be disabled if there are not dwellings
                    imgPrint.setDisable(false);
                    imgPrint.setOpacity(1);
                    //The imgPrint will be disabled if there are not dwellings
                    imgPrint.setDisable(true);
                    imgPrint.setOpacity(0.25);

                    break;
                case SELECT_BY_MIN_CONSTRUCTION_DATE:
                    if (dpConstructionDate.getValue() != null) {
                        Date date = Date.from(dpConstructionDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        dwellingsCollectionTable = FXCollections.observableArrayList(dwellingManager.findByDate(simpleDateFormat.format(date).toString()));

                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Search successful");
                        alert.setHeaderText("Dwellings have been found");
                        alert.showAndWait();
                    } else {

                        //THROW NEW EXCEPTION OF FIELDS EMPTY
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Empty fields");
                        alert.setHeaderText("The construction date is null");
                        alert.setContentText("Try again");
                        alert.showAndWait();
                    }
                    break;
                case SELECT_BY_MIN_RATING:
                    dwellingsCollectionTable = FXCollections.observableArrayList(dwellingManager.findByRating(spRating.getValue()));

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Search successful");
                    alert.setHeaderText("Dwellings have been found");
                    alert.showAndWait();

                    Alert alert2 = new Alert(AlertType.ERROR);
                    alert2.setTitle("Empty fields");
                    alert2.setHeaderText("ERRROR AAAA");
                    alert2.setContentText("Try again");
                    alert2.showAndWait();

                    break;
                default:
                    break;
            }
            if (dwellingsCollectionTable.isEmpty()) {
                Alert alert1 = new Alert(AlertType.WARNING);
                alert1.setTitle("No data");
                alert1.setHeaderText("No dwellings");
                alert1.setContentText("No dwellings found with " + spRating.getValue().toString() + " rating or more");
                alert1.showAndWait();
            }
            tableDwelling.setItems(dwellingsCollectionTable);
            tableDwelling.refresh();
        } catch (BussinessLogicException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while searching");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Method executed when the imgViewNewDwelling is clicked
     *
     * @param event
     */
    @FXML
    private void handleNewDwelling(MouseEvent event) {
        imgConfirmNewDwelling.setDisable(false);
        imgConfirmNewDwelling.setOpacity(1);
        imgCancelNewDwelling.setDisable(false);
        imgCancelNewDwelling.setOpacity(1);
        Dwelling dwelling = new Dwelling();
        dwelling.setConstructionDate(new Date());
        dwelling.setId(Long.MIN_VALUE);
        dwellingsCollectionTable.add(dwelling);
        tableDwelling.getSelectionModel().select(dwellingsCollectionTable.size() - 1);
        tableDwelling.layout();
        tableDwelling.getFocusModel().focus(dwellingsCollectionTable.size() - 1, colAddress);
        tableDwelling.edit(dwellingsCollectionTable.size() - 1, colAddress);

    }

    /**
     *
     * @param event
     */
    @FXML
    private void handleDeleteDwelling(MouseEvent event) {
        Dwelling selectedDwelling = tableDwelling.getSelectionModel()
                .getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("Do you want to delete this dwelling?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                dwellingManager.remove(selectedDwelling.getId());
                dwellingsCollectionTable.remove(selectedDwelling);
                tableDwelling.refresh();
            } catch (BussinessLogicException ex) {
                Alert alert1 = new Alert(AlertType.ERROR);
                alert1.setTitle("AYUDA");
                alert1.setHeaderText("Error");
                alert1.setContentText(ex.getMessage());
                alert1.showAndWait();
            }
        } else {
            Alert alert3 = new Alert(AlertType.INFORMATION);
            alert3.setTitle("Dwelling not deleted");
            alert3.setHeaderText(null);
            alert3.setContentText(null);
            alert3.showAndWait();
        }
    }

    /**
     * Method that is executed when the imgView print is clicked
     *
     * @param event
     */
    @FXML
    private void handlePrintDwellings(MouseEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AYUDA");
        alert.setHeaderText("Boton de print");
        alert.setContentText("A");
        alert.showAndWait();
    }

    /**
     * Method to control if there's a item selected in the table
     *
     * @param observableValue
     * @param oldValue
     * @param newValue
     */
    private void handleTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {

        if (newValue != null) {
            imgDeleteDwelling.setDisable(false);
            imgDeleteDwelling.setOpacity(1);

        } else {
            imgDeleteDwelling.setDisable(true);
            imgDeleteDwelling.setOpacity(0.25);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void handleCancelNewDwelling(MouseEvent event) {

        int pos = tableDwelling.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Cancel");
        alert.setContentText("You are about to stop the edition\nAre you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        //try {
        if (result.get() == ButtonType.OK) {
            if (pos == dwellingsCollectionTable.size() - 1 && dwellingsCollectionTable.get(pos).getId() == Long.MIN_VALUE) {
                LOGGER.info("Cancel creation");
                dwellingsCollectionTable.remove(dwellingsCollectionTable.size() - 1);
            } else {
                LOGGER.info("Cancel update");
            }
            imgConfirmNewDwelling.setDisable(true);
            imgConfirmNewDwelling.setOpacity(0.25);
            imgCancelNewDwelling.setDisable(true);
            imgCancelNewDwelling.setOpacity(0.25);
            imgCreateNewDwelling.setDisable(false);
            imgCreateNewDwelling.setOpacity(1);
            imgDeleteDwelling.setDisable(true);
            imgDeleteDwelling.setOpacity(0.25);
            tableDwelling.setItems(dwellingsCollectionTable);
            tableDwelling.refresh();
            tableDwelling.getSelectionModel().clearSelection(tableDwelling.getSelectionModel().getSelectedIndex());
        }
    }

    /**
     * Method executed when the confirm imgView is clicked
     *
     * @param event
     */
    @FXML
    private void handleConfirmNewDwelling(MouseEvent event) {

        int pos = tableDwelling.getSelectionModel().getSelectedIndex();
        try {
            if (pos == dwellingsCollectionTable.size() - 1 && dwellingsCollectionTable.get(pos).getId() == Long.MIN_VALUE) {

                Dwelling dwelling = new Dwelling();
                Dwelling dtb = dwellingsCollectionTable.get(pos);
                dwelling.setAddress(dtb.getAddress());
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateFormatter.format(dtb.getConstructionDate()));
                dwelling.setConstructionDate(date1);
                dwelling.setHasWiFi(dtb.getHasWiFi());
                dwelling.setHost((Owner) user);
                dwelling.setId(0L);
                dwelling.setRating(Float.valueOf(0));
                dwelling.setSquareMeters(dtb.getSquareMeters());
                dwellingManager.add(dwelling);

            } else {
                Dwelling dwelling = new Dwelling();
                Dwelling dtb = dwellingsCollectionTable.get(pos);
                dwelling.setAddress(dtb.getAddress());
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateFormatter.format(dtb.getConstructionDate()));
                dwelling.setConstructionDate(date1);
                dwelling.setHasWiFi(dtb.getHasWiFi());
                dwelling.setHost((Owner) user);
                dwelling.setId(dtb.getId());
                dwelling.setRating(Float.valueOf(0));
                dwelling.setSquareMeters(dtb.getSquareMeters());
                dwellingManager.update(dwelling);

            }
            imgConfirmNewDwelling.setDisable(true);
            imgConfirmNewDwelling.setOpacity(0.25);
            imgCancelNewDwelling.setDisable(true);
            imgCancelNewDwelling.setOpacity(0.25);
            imgCreateNewDwelling.setDisable(false);
            imgCreateNewDwelling.setOpacity(1);
            imgDeleteDwelling.setDisable(true);
            imgDeleteDwelling.setOpacity(0.25);
            tableDwelling.setItems(dwellingsCollectionTable);
            tableDwelling.refresh();
            tableDwelling.getSelectionModel().clearSelection(tableDwelling.getSelectionModel().getSelectedIndex());
        } catch (ParseException ex) {
            LOGGER.severe("The date value is not valid while creating/editing");
        } catch (BussinessLogicException ex) {
            LOGGER.severe("ERROR WITH THE SERVER SIDE");
        }

    }

    /**
     * Method that sets the stage
     *
     * @param primaryStage the stage to set
     */
    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    /**
     * Method that sets the dwelling manager (logic)
     *
     * @param dwellingManager the dwellingManager to set
     */
    public void setDwellingManager(DwellingManager dwellingManager) {
        this.dwellingManager = dwellingManager;
    }

    /**
     * Method that sets the user
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
