package view.controllers;

import exceptions.BusinessLogicException;
import exceptions.FieldsEmptyException;
import exceptions.NotValidDateValueException;
import javafx.scene.input.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import model.Facility;
import interfaces.FacilityManager;
import model.FacilityType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class for Facilities.fxml
 *
 * @author jorge
 */
public class FacilitiesController {

    //FXML Objects
    /**
     * ComboBox containing all facility queries
     */
    @FXML
    private ComboBox<String> cb_Facilities;
    @FXML
    private DatePicker dp_Facilities;
    @FXML
    private Spinner<Integer> sp_Facilities;
    /**
     * Button Used to search
     */
    @FXML
    private Button srch_Btn;
    /**
     * ComboBox used to select
     */
    @FXML
    private ComboBox<String> cb_Type;
    @FXML
    private TableView<Facility> tbl_facilities;
    @FXML
    private TableColumn<Facility, Long> id_Column;
    @FXML
    private TableColumn<Facility, String> adq_column;
    @FXML
    private TableColumn<Facility, String> type_column;
    @FXML
    private ImageView iv_add;
    @FXML
    private ImageView iv_check;
    @FXML
    private ImageView iv_minus;
    @FXML
    private ImageView iv_print;
    @FXML
    private ImageView iv_cancel;
    //My Objects
    private static final Logger LOGGER = Logger.getLogger(FacilitiesController.class.getSimpleName());
    private FacilityManager facMan;
    private final String date = "Date";
    private final String type = "Type";
    private final String id = "Id";
    private final String all = "All";
    private ObservableList<Facility> myFacilities;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    boolean adding = false;
    boolean editing = false;
    //Regex which validates date format dd/MM/yyyy
    private final String regexDate = "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";

    /**
     * Initializes the controller class.
     */
    public void initStage() {
        try {

            //Disables at start all query elements except the type of query combo.
            cb_Type.setDisable(true);
            dp_Facilities.setDisable(true);
            sp_Facilities.setDisable(true);
            //Makes table editable
            tbl_facilities.setEditable(true);
            //Disables and changes opacity to check and cancel ImageViews
            iv_check.setDisable(true);
            iv_check.setOpacity(0.25);
            iv_cancel.setDisable(true);
            iv_cancel.setOpacity(0.25);
            iv_check.setDisable(true);
            iv_check.setOpacity(0.25);
            //Loads all comboBox query type data
            ObservableList<String> options
                    = FXCollections.observableArrayList(all, id, type, date);
            cb_Facilities.setItems(options);
            cb_Facilities.getSelectionModel().selectFirst();
            //Selects all facilities
            try {
                List<Facility> allFacilities = facMan.selectAll();
                ObservableList<Facility> facilityTableBean
                        = FXCollections.observableArrayList(allFacilities);
                myFacilities = facilityTableBean;

                if (allFacilities.size() > 0) {
                    //Adds the observable list of Facilities to the table.
                    tbl_facilities.setItems(myFacilities);
                } else {

                    //The imgPrint will be disabled if there are no facilities
                    iv_print.setDisable(true);
                    iv_print.setOpacity(0.25);
                }
            } catch (BusinessLogicException ex) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("AYUDA");
                alert.setHeaderText("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
            //Sets the value factory of the id cells column
            id_Column.setCellValueFactory(
                    new PropertyValueFactory<>("id"));
            //Sets the value factory of the spinner
            sp_Facilities.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));
            //Makes spinner editable
            sp_Facilities.setEditable(true);

            EnumSet<FacilityType> ft = EnumSet.allOf(FacilityType.class);
            ArrayList<String> facilTypeList = new ArrayList<>();
            //Adds all facility types to an ArrayList
            for (FacilityType f : ft) {
                facilTypeList.add(f.toString());
            }
            //Observable list of String with all the types.
            ObservableList<String> optionsType = FXCollections.observableArrayList(facilTypeList);
            //Sets items of the observable list into the type combo
            cb_Type.setItems(optionsType);
            //Sets the first type as selected when starting
            cb_Type.getSelectionModel().selectFirst();
            adq_column.setCellValueFactory(cellData
                    -> new SimpleStringProperty(formatter.format(cellData.getValue().getAdquisitionDate())));
            adq_column.setCellFactory(TextFieldTableCell.<Facility>forTableColumn());
            //Sets the behaviour the column cells will have when commiting an Edit.
            adq_column.setOnEditCommit(
                    (CellEditEvent<Facility, String> t) -> {
                        try {
                            //Checks if the new committed value is empty
                            if (t.getNewValue().isEmpty()) {
                                //LOGGER.warning("The field in the facility adquisition date is empty");
                                throw new FieldsEmptyException();
                            }
                            //Checks if the commited value format is valid
                            if (!t.getNewValue().matches(regexDate)) {
                                LOGGER.severe("The field in the adquisition date doesn't have a valid date");
                                throw new NotValidDateValueException("Date not valid");
                            }
                            //sets date with parsed format
                            ((Facility) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setAdquisitionDate(
                                    formatter.parse(t.getNewValue())
                            );
                            //Sets the check and cancel buttons to clickable
                            iv_check.setDisable(false);
                            iv_check.setOpacity(1);
                            iv_cancel.setDisable(false);
                            iv_cancel.setOpacity(1);
                            iv_minus.setDisable(true);
                            iv_minus.setOpacity(0.25);
                            iv_add.setDisable(true);
                            iv_add.setOpacity(0.25);
                            editing = true;
                        } catch (FieldsEmptyException | NotValidDateValueException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.showAndWait();
                            //Sets the confirm and cancel buttons to not clickable
                            iv_check.setDisable(true);
                            iv_check.setOpacity(0.25);
                            iv_cancel.setDisable(true);
                            iv_cancel.setOpacity(0.25);
                        } catch (ParseException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.setContentText("Valid value:\ndd/MM/yyyy\nex. 17/11/2008");
                            alert.showAndWait();
                            //Sets the check and cancel buttons to not clickable
                            iv_check.setDisable(true);
                            iv_check.setOpacity(0.25);
                            iv_cancel.setDisable(true);
                            iv_cancel.setOpacity(0.25);
                        }

                    });
            //Sets date picker to not editable
            dp_Facilities.setEditable(false);
            //Sets the cell value Factory
            type_column.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getType()));
            //Sets cell factory to a combobox using all options types
            type_column.setCellFactory(ComboBoxTableCell.forTableColumn(optionsType));
            //Sets to type column a cell edit event 
            type_column.setOnEditCommit(
                    (CellEditEvent<Facility, String> t) -> {
                        ((Facility) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setType(t.getNewValue());
                        try {
                            if (t.getNewValue().toString().equalsIgnoreCase("")) {

                                //LOGGER.warning("The field in facility type is empty");
                                throw new FieldsEmptyException();
                            }
                            if (t.getNewValue().toString().equalsIgnoreCase(t.getOldValue().toString())) {
                                //Sets the check and cancel buttons to clickable
                                iv_check.setDisable(false);
                                iv_check.setOpacity(1);
                                iv_cancel.setDisable(false);
                                iv_cancel.setOpacity(1);
                                iv_minus.setDisable(true);
                                iv_minus.setOpacity(0.25);
                                iv_add.setDisable(true);
                                iv_add.setOpacity(0.25);
                                editing = true;

                            }

                        } catch (FieldsEmptyException ex) {
                            Logger.getLogger(FacilitiesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
        } catch (Exception e) {
            //LOGGER.severe(e.getMessage());
            //LOGGER.severe("Error while opening the window");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Couldn't open Facilities window");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    /**
     * Method used when changing cb_Facilities to enable disable components
     *
     * @param action ActionEvent triggered when pressing combo
     */
    @FXML
    void handleCbChange(ActionEvent action) {
        switch (cb_Facilities.getValue()) {
            case id:
                LOGGER.info("Id query selected");
                //Disables type combo box and date picker and enables spinner
                cb_Type.setDisable(true);
                dp_Facilities.setDisable(true);
                sp_Facilities.setDisable(false);
                break;
            case type:
                LOGGER.info("Type query selected");
                //Disables date picker and the spinner and enables type comboBox
                cb_Type.setDisable(false);
                dp_Facilities.setDisable(true);
                sp_Facilities.setDisable(true);

                break;
            case date:
                LOGGER.info("Date query selected");
                //Disables type comboBox and the spinner and enables the date picker.
                cb_Type.setDisable(true);
                dp_Facilities.setDisable(false);
                sp_Facilities.setDisable(true);
                break;
            case all:
                LOGGER.info("All query selected");
                //Disables Type ComboBox,Facilities date picker and Facilities Spinner.
                cb_Type.setDisable(true);
                dp_Facilities.setDisable(true);
                sp_Facilities.setDisable(true);

        }

    }

    /**
     * Method useed to search specific queries made by the client.
     *
     * @param action Action event triggered when pressing Search button.
     */
    @FXML
    void searchAction(ActionEvent action) {
        switch (cb_Facilities.getValue()) {
            //Search by date
            case date:
                if (dp_Facilities.getValue() != null) {
                    try {
                        //Gets date from date picker
                        Date date = Date.from(dp_Facilities.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        //Date format of the date
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        List<Facility> facilities = new ArrayList<>();
                        //Selects by date
                        List<Facility> fs = facMan.selectByDate(simpleDateFormat.format(date).toString());
                        //If the facility List is greater than 0 adds the list to 
                        //facility table and if not shows an alert to notify query its empty
                        if (fs.size() > 0) {
                            for (Facility f : fs) {
                                facilities.add(f);
                            }
                            ObservableList<Facility> facilityTableBean
                                    = FXCollections.observableArrayList(facilities);
                            tbl_facilities.setItems(facilityTableBean);
                            myFacilities = facilityTableBean;
                        } else {
                            LOGGER.info("No results");
                            //Alert notifying facilities with X date not found
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Date not found");
                            alert.setHeaderText("Couldn't find facilities with selected date");
                            alert.setContentText("Try again");
                            alert.showAndWait();
                        }
                    } catch (BusinessLogicException ex) {
                        LOGGER.warning("ERROR,date query failure.");
                    }
                } else {
                    LOGGER.info("Field empty");
                    //Alert notifying date field is empty
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Empty fields");
                    alert.setHeaderText("The adquisition date is null");
                    alert.setContentText("Try again");
                    alert.showAndWait();
                }
                break;
            //Search by type
            case type:
                if (cb_Type.getValue() != null) {
                    try {

                        List<Facility> facilities = new ArrayList<>();
                        //Selects by Type
                        List<Facility> fs = facMan.selectByType(cb_Type.getValue());
                        //If the facility List is greater than 0 adds the list to 
                        //facility table and if not shows an alert to notify query its empty
                        if (fs.size() > 0) {
                            for (Facility f : fs) {
                                facilities.add(f);
                            }
                            ObservableList<Facility> facilityTableBean
                                    = FXCollections.observableArrayList(facilities);
                            tbl_facilities.setItems(facilityTableBean);
                            myFacilities = facilityTableBean;
                        } else {
                            LOGGER.info("No results");
                            //Alert shown whe type not found
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Type not found");
                            alert.setHeaderText("Couldn't find facilities with selected type");
                            alert.setContentText("Try again");
                            alert.showAndWait();
                        }
                    } catch (BusinessLogicException ex) {
                        LOGGER.warning("ERROR,type query failure.");
                    }
                } else {
                    LOGGER.info("No type selected.");
                }
                break;
            //Search by Id
            case id:
                if (sp_Facilities != null) {
                    Long aux = Long.valueOf(sp_Facilities.getValue().toString());
                    try {
                        List<Facility> facilities = new ArrayList<>();
                        //Search by Id
                        Facility fs = facMan.selectById(aux);
                        //If the facility query isn't null adds the facility to the table
                        //If it isn't Shows an Alert notifying the id hasn't been found
                        if (fs != null) {

                            facilities.add(fs);
                            ObservableList<Facility> facilityTableBean
                                    = FXCollections.observableArrayList(facilities);
                            tbl_facilities.setItems(facilityTableBean);
                            myFacilities = facilityTableBean;
                        } else {
                            LOGGER.info("No results");
                            //Alert shown whe Id not found
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Id not found");
                            alert.setHeaderText("Couldn't find facility with selected Id");
                            alert.setContentText("Try again");
                            alert.showAndWait();
                        }

                    } catch (BusinessLogicException ex) {
                        LOGGER.warning("ERROR, id query Failure");
                        //Alert notifying spinner field failure
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Error wrong format");
                        alert.setHeaderText("Spinner has wrong format, please insert only numbers");
                        alert.setContentText("Try again");
                        alert.showAndWait();
                    }

                } else {
                    LOGGER.warning("ERROR, spFacilities is null");
                    //Alert notifying spinner field is empty
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Empty fields");
                    alert.setHeaderText("The spinner field is empty");
                    alert.setContentText("Try again");
                    alert.showAndWait();
                }
                break;
            //Search all
            case all:
                //Loads all data
                loadAll();
                break;

        }
    }

    /**
     * Method used to add one row to the table to later commit.
     *
     * @param event Mouse event triggered when on pressed
     */
    @FXML
    void clickAdd(MouseEvent event) {
        //Disables iv_add and iv_minus and enables check and cancel image view
        iv_add.setDisable(true);
        iv_add.setOpacity(0.25);
        iv_minus.setDisable(true);
        iv_minus.setOpacity(0.25);
        iv_check.setDisable(false);
        iv_check.setOpacity(1);
        iv_cancel.setDisable(false);
        iv_cancel.setOpacity(1);
        //sets adding boolean to true
        adding = true;
        if (myFacilities.isEmpty()) {
            myFacilities = loadAll();
            tbl_facilities.refresh();
        }
        Facility ft = new Facility();
        ft.setId(Long.MIN_VALUE);
        ft.setAdquisitionDate(new Date());
        //Adds row to table
        myFacilities.add(ft);
        //Selects last row of the table
        tbl_facilities.getSelectionModel().select(myFacilities.size() - 1);
        tbl_facilities.layout();
        //Focuses adq_column of last row
        tbl_facilities.getFocusModel().focus(myFacilities.size() - 1, adq_column);
        //Edits adq_column
        tbl_facilities.edit(myFacilities.size() - 1, adq_column);

    }

    /**
     * Method used to print a Jasper report with the displayed data.
     *
     * @param action Mouse event triggered when pressing iv_print.
     */
    @FXML
    void clickPrint(MouseEvent action) {
        try {

            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/reports/AdminFacilityReport.jrxml"));
            //Data for the report: a collection of User passed as a JRDataSource implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Facility>) this.tbl_facilities.getItems());
            //Get the name of the admin who printed the report as a parameter
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with the data of the table     
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setTitle("FacilitiesJasper");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Report");
            alert.setContentText("Report generated");
            alert.showAndWait();
            jasperViewer.setVisible(true);

        } catch (JRException ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Error printing");
            alert.setContentText("There was an error printing the information, try again later");
            //LOGGER.log(Level.SEVERE,
            // "Error printing report at printReport(): {0}",
            // ex.getMessage());
        }
    }

    /**
     * Method used to delete one Facility from the database.
     *
     * @param action MouseEvent triggered when pressing iv_cancel ImageView.
     */
    @FXML
    void clickMinus(MouseEvent action) {
        Facility facTBean = null;
        try {
            //Gets selected item
            facTBean = tbl_facilities.getSelectionModel().getSelectedItem();
            //Alert of confirmation of deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete\n this facility with the following ID:" + facTBean.getId() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    //Deletes the selected dacility by id
                    facMan.remove(facTBean.getId());
                    //Selects all with the deletion updated
                    loadAll();
                } catch (BusinessLogicException ex) {
                    //In case delete fails
                    Alert alert1 = new Alert(AlertType.ERROR);
                    alert1.setTitle("AYUDA");
                    alert1.setHeaderText("Error");
                    alert1.setContentText(ex.getMessage());
                    alert1.showAndWait();
                }
            } else {
                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Facility not deleted");
                alert2.setHeaderText(null);
                alert2.setContentText("Content not deleted");
                alert2.showAndWait();
            }
        } catch (Exception ex) {
            Alert alert2 = new Alert(AlertType.INFORMATION);
            alert2.setTitle("Error while deleting");
            alert2.setHeaderText(null);
            alert2.setContentText("Couldn't delete, please \n select the desired row to delete in the table");
            alert2.showAndWait();
        }

    }

    /**
     * Method used to cancel the updated/created rows.
     *
     * @param action MouseEvent triggered when pressing iv_cancel ImageView
     */
    @FXML
    void clickClose(MouseEvent action) {
        LOGGER.info("Closing");
        //Confirmation Alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to stop creating/updatting?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (adding) {
                //If its adding removes last row and sets adding to false
                myFacilities.remove(myFacilities.size() - 1);
                adding = false;
                //Sets ImageViews iv_add,iv_minus as enabled and iv_check,iv_cancel as disabled
                iv_add.setDisable(false);
                iv_add.setOpacity(1);
                iv_minus.setDisable(false);
                iv_minus.setOpacity(1);
                iv_cancel.setDisable(true);
                iv_cancel.setOpacity(0.25);
                iv_check.setDisable(true);
                iv_check.setOpacity(0.25);
            } else if (editing) {
                //if its editing reloads all and sets editing to false
                loadAll();
                editing = false;
                //Sets ImageViews iv_add,iv_minus as enabled and iv_check,iv_cancel as disabled
                iv_add.setDisable(false);
                iv_add.setOpacity(1);
                iv_minus.setDisable(false);
                iv_minus.setOpacity(1);
                iv_cancel.setDisable(true);
                iv_cancel.setOpacity(0.25);
                iv_check.setDisable(true);
                iv_check.setOpacity(0.25);
            }
        } else {
        }
    }

    /**
     * Method used to commit changes into the database.
     *
     * @param action Mouse event triggered when pressing iv_check
     */
    @FXML
    void clickCheck(MouseEvent action) {
        Date date;
        Facility f = new Facility();
        //Equals a Facility to the selected row
        f = tbl_facilities.getSelectionModel().getSelectedItem();
        //Gets position of index
        int pos = tbl_facilities.getSelectionModel().getSelectedIndex();
        try {
            //If the position of the row equls the last one and the ID value
            //is the minimum starts wit the creating process
            //if not starts with the updating process
            if (pos == myFacilities.size() - 1 && f.getId().equals(Long.MIN_VALUE)) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmation");
                alert.setContentText("Are you sure you want to create\n this facility with the following type and date:" + f.getType() + f.getAdquisitionDate() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (f.getType() != null) {
                        LOGGER.info("Creating...");
                        facMan.create(f);
                        loadAll();
                        iv_check.setDisable(true);
                        iv_check.setOpacity(0.25);
                        iv_cancel.setDisable(true);
                        iv_cancel.setOpacity(0.25);
                        iv_minus.setOpacity(1);
                        iv_minus.setDisable(false);
                        iv_add.setDisable(false);
                        iv_add.setOpacity(1);
                    } else {
                        Alert alert2 = new Alert(AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("One field is empty");
                        alert.showAndWait();
                        LOGGER.warning("Missing field");
                    }
                } else {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Facility not created");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Content not created");
                    alert2.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmation");
                alert.setContentText("Are you sure you want to update\n this facility with the following type and date:" + f.getType() + "  " + f.getAdquisitionDate() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    LOGGER.info("Updating...");
                    facMan.update(f, f.getId());
                    loadAll();
                    iv_check.setDisable(true);
                    iv_check.setOpacity(0.25);
                    iv_cancel.setDisable(true);
                    iv_cancel.setOpacity(0.25);
                    iv_minus.setOpacity(1);
                    iv_minus.setDisable(false);
                    iv_add.setDisable(false);
                    iv_add.setOpacity(1);
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Facility updated");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Content updated");
                    alert2.showAndWait();
                } else {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Facility not updated");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Content not updated");
                    alert2.showAndWait();
                }

            }
        } catch (BusinessLogicException ex) {
            LOGGER.warning("Check Failed");
            Alert excAlert = new Alert(AlertType.INFORMATION);
            excAlert.setTitle("Error");
            excAlert.setContentText("There was an error with the edition of the facility: " + ex.getMessage());
            excAlert.show();

        }
    }

    /**
     * Used to set the FacilityManager
     *
     * @param facMan Facility Manager
     */
    public void setFacMan(FacilityManager facMan) {
        this.facMan = facMan;
    }

    /**
     * Method in which selects all facilities.
     *
     * @return ObservableList of all facilities
     */
    private ObservableList loadAll() {
        ObservableList<Facility> facilityTableBean = null;
        //List of all facilities
        List<Facility> allFacilities;
        try {
            //Gets all facilities
            allFacilities = facMan.selectAll();
            //Adds to the observable list the list
            facilityTableBean = FXCollections.observableArrayList(allFacilities);
            //sets items to the table
            tbl_facilities.setItems(facilityTableBean);

        } catch (BusinessLogicException ex) {
            LOGGER.warning("Business Logic Exception Error");
        }
        tbl_facilities.refresh();
        myFacilities = facilityTableBean;
        //returns the observable list
        return facilityTableBean;
    }
}
