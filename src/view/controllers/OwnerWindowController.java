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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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
            //The spinner and the date Picker will be disabled
            dpConstructionDate.setDisable(true);
            spRating.setDisable(true);
            //Sets the the delete imgs to not clickable
            imgDeleteDwelling.setDisable(true);
            imgDeleteDwelling.setOpacity(0.25);
            imgConfirmNewDwelling.setDisable(true);
            imgConfirmNewDwelling.setOpacity(0.25);
            imgCancelNewDwelling.setDisable(true);
            imgCancelNewDwelling.setOpacity(0.25);
            //Load the comboBox data
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
            //Add the editable table
            tableDwelling.setEditable(true);
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colAddress.setCellFactory(TextFieldTableCell.<Dwelling>forTableColumn());
            colAddress.setOnEditCommit(
                    (CellEditEvent<Dwelling, String> t) -> {
                        {
                            try {
                                if (t.getNewValue().trim().isEmpty()) {
                                    //throw validation Error
                                    LOGGER.warning("The address field is empty");
                                    throw new FieldsEmptyException();
                                }
                                if (t.getNewValue().trim().length() > 255) {
                                    //throw validation Error
                                    LOGGER.warning("The field has more than 255 characters");
                                    throw new MaxCharactersException();
                                }

                                ((Dwelling) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setAddress(t.getNewValue());
                                //SETS THE CONFIRM AND CANCEL BUTTONS TO CLICKABLE
                                imgConfirmNewDwelling.setDisable(false);
                                imgConfirmNewDwelling.setOpacity(1);
                                imgCancelNewDwelling.setDisable(false);
                                imgCancelNewDwelling.setOpacity(1);
                            } catch (FieldsEmptyException | MaxCharactersException ex) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Error");
                                alert.setHeaderText(ex.getMessage());
                                alert.showAndWait();
                                //SETS THE CONFIRM AND CANCEL BUTTONS TO NOT CLICKABLE
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
                                //throw validation Error
                                LOGGER.warning("The squareMeters field is empty");
                                throw new FieldsEmptyException();
                            }
                            if (!t.getNewValue().matches(regexDouble)) {
                                //throw validation Error
                                LOGGER.warning("The squareMeter value is not valid");
                                throw new NotValidSquareMetersValueException("The squareMeter value is not valid");
                            }
                            ((Dwelling) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setSquareMeters(Double.valueOf(t.getNewValue()));
                            //SETS THE CONFIRM AND CANCEL BUTTONS TO CLICKABLE
                            imgConfirmNewDwelling.setDisable(false);
                            imgConfirmNewDwelling.setOpacity(1);
                            imgCancelNewDwelling.setDisable(false);
                            imgCancelNewDwelling.setOpacity(1);
                        } catch (FieldsEmptyException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.showAndWait();
                            //SETS THE CONFIRM AND CANCEL BUTTONS TO NOT CLICKABLE
                            imgConfirmNewDwelling.setDisable(true);
                            imgConfirmNewDwelling.setOpacity(0.25);
                            imgCancelNewDwelling.setDisable(true);
                            imgCancelNewDwelling.setOpacity(0.25);
                        } catch (NotValidSquareMetersValueException ex) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(ex.getMessage());
                            alert.setContentText("Valid values:\n1\n1.2");
                            alert.showAndWait();
                            //SETS THE CONFIRM AND CANCEL BUTTONS TO NOT CLICKABLE
                            imgConfirmNewDwelling.setDisable(true);
                            imgConfirmNewDwelling.setOpacity(0.25);
                            imgCancelNewDwelling.setDisable(true);
                            imgCancelNewDwelling.setOpacity(0.25);
                        }
                    });
            colConstructionDate.setCellValueFactory(cellData
                    -> new SimpleStringProperty(dateFormatter.format(cellData.getValue().getConstructionDate())));
            colConstructionDate.setCellFactory(TextFieldTableCell.<Dwelling>forTableColumn());
            colConstructionDate.setOnEditCommit(
                    (CellEditEvent<Dwelling, String> t) -> {
                        try {

                            if (t.getNewValue().isEmpty()) {
                                LOGGER.warning("The field in the construction date is empty");
                                throw new FieldsEmptyException();
                            }
                            if (!t.getNewValue().matches(regexDate)) {
                                LOGGER.severe("The field in the construction date has a not valid date");
                                throw new NotValidDateValueException("Not valid date");
                            }

                            ((Dwelling) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setConstructionDate(
                                    dateFormatter.parse(t.getNewValue())
                            );
                            //SETS THE CONFIRM AND CANCEL BUTTONS TO CLICKABLE
                            imgConfirmNewDwelling.setDisable(false);
                            imgConfirmNewDwelling.setOpacity(1);
                            imgCancelNewDwelling.setDisable(false);
                            imgCancelNewDwelling.setOpacity(1);
                        } catch (FieldsEmptyException | NotValidDateValueException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.showAndWait();
                            //SETS THE CONFIRM AND CANCEL BUTTONS TO NOT CLICKABLE
                            imgConfirmNewDwelling.setDisable(true);
                            imgConfirmNewDwelling.setOpacity(0.25);
                            imgCancelNewDwelling.setDisable(true);
                            imgCancelNewDwelling.setOpacity(0.25);
                        } catch (ParseException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.setContentText("Valid value:\ndd/MM/yyyy\nex. 17/11/2008");
                            alert.showAndWait();
                            //SETS THE CONFIRM AND CANCEL BUTTONS TO NOT CLICKABLE
                            imgConfirmNewDwelling.setDisable(true);
                            imgConfirmNewDwelling.setOpacity(0.25);
                            imgCancelNewDwelling.setDisable(true);
                            imgCancelNewDwelling.setOpacity(0.25);
                        }

                    });

            colRating.setCellValueFactory(
                    new PropertyValueFactory<>("rating"));
            colMoreInfo.setCellValueFactory(
                    new PropertyValueFactory<>("moreInfo"));
            //Load all the dwellings by default
            dwellingsCollectionTable = FXCollections.observableArrayList(dwellingManager.loadAllDwellings());
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
                                imgConfirmNewDwelling.setDisable(false);
                                imgConfirmNewDwelling.setOpacity(1);
                                imgCancelNewDwelling.setDisable(false);
                                imgCancelNewDwelling.setOpacity(1);
                            })
            );
            tableDwelling.setItems(dwellingsCollectionTable);
            //Shows the stage
            stage.show();
            LOGGER.info("Owner/Guest-Window Open");
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("ERROR");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method executed when the components of the comboBox of the filter is
     * changed
     *
     * @param event the ActionEvent
     */
    @FXML
    private void handleChangeComponents(ActionEvent event) {
        switch (cbDwellings.getValue()) {
            case SELECT_ALL_DWELLINGS:
                LOGGER.info("Select All Dwelling choosed");
                dpConstructionDate.setDisable(true);
                spRating.setDisable(true);
                break;
            case SELECT_BY_MIN_CONSTRUCTION_DATE:
                LOGGER.info("Select by min ConstructionDate choosed");
                dpConstructionDate.setDisable(false);
                spRating.setDisable(true);
                break;
            case SELECT_BY_MIN_RATING:
                LOGGER.info("Select by min rating choosed");
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
                    dwellingsCollectionTable = FXCollections.observableArrayList(dwellingManager.loadAllDwellings());

                    break;
                case SELECT_BY_MIN_CONSTRUCTION_DATE:
                    if (dpConstructionDate.getValue() != null) {
                        Date date = Date.from(dpConstructionDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        dwellingsCollectionTable = FXCollections.observableArrayList(dwellingManager.findByDate(simpleDateFormat.format(date).toString()));

                    } else {
                        //Show an Alert when trying to search with the enabled field empty.
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Empty fields");
                        alert.setHeaderText("The construction date is empty");
                        alert.setContentText("Please select a date");
                        alert.showAndWait();
                    }
                    break;
                case SELECT_BY_MIN_RATING:
                    dwellingsCollectionTable = FXCollections.observableArrayList(dwellingManager.findByRating(spRating.getValue()));
                    break;
                default:
                    break;
            }
            //Set the items to the table & refres the table
            tableDwelling.setItems(dwellingsCollectionTable);
            tableDwelling.refresh();
            if (dwellingsCollectionTable.isEmpty()) {
                LOGGER.warning("No dwelling have been found");
                //The imgPrint will be disabled if there are not dwellings
                imgPrint.setDisable(true);
                imgPrint.setOpacity(0.25);
                Alert alert1 = new Alert(AlertType.WARNING);
                alert1.setTitle("No data");
                alert1.setHeaderText("No dwellings");
                alert1.setContentText("No dwellings have been found");
                alert1.showAndWait();
            } else {
                LOGGER.warning("No dwelling have been found");
                //The imgPrint will be enabled if there are dwellings
                imgPrint.setDisable(false);
                imgPrint.setOpacity(1);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Search successful");
                alert.setHeaderText("Dwellings have been found");
                alert.showAndWait();
            }
        } catch (BussinessLogicException e) {
            LOGGER.severe("Error with the server side");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error with the server");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Method executed when the imgViewNewDwelling is clicked
     *
     * @param event the MouseEvent
     */
    @FXML
    private void handleNewDwelling(MouseEvent event) {
        LOGGER.info("Adding a new row to the table");
        //A new line with blank fields will be added
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
     * Method executed when the imgDeleteDwelling is clicked
     *
     * @param event the MouseEvent
     */
    @FXML
    private void handleDeleteDwelling(MouseEvent event) {
        LOGGER.info("Trying to delete a dwelling");
        Dwelling selectedDwelling = tableDwelling.getSelectionModel()
                .getSelectedItem();
        //An alert will be shown informing the owner if they’re sure about deleting that specific dwelling
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("Do you want to delete this dwelling?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                dwellingManager.remove(selectedDwelling.getId());
                dwellingsCollectionTable.remove(selectedDwelling);
                LOGGER.info("A Dwelling have been removed");
                tableDwelling.refresh();
            } catch (BussinessLogicException ex) {
                LOGGER.severe("Error with the server side");
                Alert alert1 = new Alert(AlertType.ERROR);
                alert1.setTitle("AYUDA");
                alert1.setHeaderText("Error");
                alert1.setContentText(ex.getMessage());
                alert1.showAndWait();
            }
        } else {
            LOGGER.info("Dwelling finally not removed by the user");
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
        try {
            LOGGER.info("Beginning printing action...");
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/reports/dwellingreport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Dwelling>) this.tableDwelling.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            LOGGER.severe("Error printing");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error with the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Method to control if there's a item selected in the table
     *
     * @param observableValue the Observable value
     * @param oldValue the old value
     * @param newValue the new value
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
     * Method that is executed when the imgCancel is clicked
     *
     * @param event the mouse event
     */
    @FXML
    private void handleCancelNewDwelling(MouseEvent event) {
        LOGGER.info("Cancelling");
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
            LOGGER.info("Refreshing the table");
            tableDwelling.refresh();
            tableDwelling.getSelectionModel().clearSelection(tableDwelling.getSelectionModel().getSelectedIndex());
        }
    }

    /**
     * Method executed when the confirm imgView is clicked
     *
     * @param event the mousevent
     */
    @FXML
    private void handleConfirmNewDwelling(MouseEvent event) {
        LOGGER.info("Confirm button clicked");
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
            LOGGER.severe("Error while parseing the date");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (BussinessLogicException ex) {
            LOGGER.severe("ERROR WITH THE SERVER SIDE");
            LOGGER.severe("Error with the server side");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error with the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
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
