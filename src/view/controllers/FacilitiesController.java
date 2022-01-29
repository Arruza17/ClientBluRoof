/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import exceptions.BusinessLogicException;
import exceptions.FieldsEmptyException;
import javafx.scene.input.MouseEvent;
import java.net.URL;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.T;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.BussinessLogicException;
import model.Facility;
import model.FacilityManager;
import model.FacilityType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author jorge
 */
public class FacilitiesController {

    private Stage stage;
    @FXML
    private ComboBox<String> cb_Facilities;
    @FXML
    private DatePicker dp_Facilities;
    private final String regexDate = "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";
    @FXML
    private Spinner<Integer> sp_Facilities;
    @FXML
    private Button srch_Btn;
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
    private TableColumn<Facility, String> more_Info_Column;
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
    private FacilityManager facMan;
    private final String date = "Date";
    private final String type = "Type";
    private final String id = "Id";
    private ObservableList<Facility> myFacilities;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {
      
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Facilities Window");
        cb_Type.setDisable(true);
        dp_Facilities.setDisable(true);
        tbl_facilities.setEditable(true);
        iv_check.setDisable(true);
        iv_check.setOpacity(0.25);
        iv_cancel.setDisable(true);
        iv_cancel.setOpacity(0.25);
        iv_check.setDisable(true);
        iv_check.setOpacity(0.25);
        ObservableList<String> options
                = FXCollections.observableArrayList(id, type, date);
        cb_Facilities.setItems(options);
        cb_Facilities.getSelectionModel().selectFirst();
        tbl_facilities.getSelectionModel().selectedItemProperty()
                .addListener(this::handleTableSelectionChanged);
       
        try {
            List<Facility> allFacilities = facMan.selectAll();
            ObservableList<Facility> facilityTableBean
                        = FXCollections.observableArrayList(allFacilities);
                myFacilities = facilityTableBean;
                System.out.println(myFacilities.get(0).getId());
                System.out.println(myFacilities.get(0).getAdquisitionDate());
                System.out.println(myFacilities.get(0).getType());
               
            if (allFacilities.size() > 0) {
        
            } else {
                
                //The imgPrint will be disabled if there are not dwellings
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

        id_Column.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        sp_Facilities.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99));
        sp_Facilities.setEditable(true);

        EnumSet<FacilityType> ft = EnumSet.allOf(FacilityType.class);
        ArrayList<String> facilTypeList = new ArrayList<>();

        for (FacilityType f : ft) {
            facilTypeList.add(f.toString());
        }
        ObservableList<String> optionsType = FXCollections.observableArrayList(facilTypeList);
        cb_Type.setItems(optionsType);
        /*adq_column.setCellValueFactory(cellData
                -> new SimpleObjectProperty(cellData.getValue().getAdqDate()));*/
        adq_column.setCellValueFactory(cellData
                -> new SimpleStringProperty(formatter.format(cellData.getValue().getAdquisitionDate())));
        adq_column.setCellFactory(TextFieldTableCell.<Facility>forTableColumn());
        adq_column.setOnEditCommit(
                    (CellEditEvent<Facility, String> t) -> {
                        try {

                            if (t.getNewValue().isEmpty()) {
                                LOGGER.warning("The field in the construction date is empty");
                                throw new FieldsEmptyException();
                            }
                            if (!t.getNewValue().matches(regexDate)) {
                                LOGGER.severe("The field in the construction date has a not valid date");
                                //throw new NotValidDateValueException("Not valid date");
                            }

                            ((Facility) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setAdquisitionDate(
                                    formatter.parse(t.getNewValue())
                            );
                            //SETS THE CONFIRM AND CANCEL BUTTONS TO CLICKABLE
                            //imgConfirmNewDwelling.setDisable(false);
                            //imgConfirmNewDwelling.setOpacity(1);
                            //imgCancelNewDwelling.setDisable(false);
                            //imgCancelNewDwelling.setOpacity(1);
                        } catch (FieldsEmptyException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.showAndWait();
                            //SETS THE CONFIRM AND CANCEL BUTTONS TO NOT CLICKABLE
                            //imgConfirmNewDwelling.setDisable(true);
                            //imgConfirmNewDwelling.setOpacity(0.25);
                            //imgCancelNewDwelling.setDisable(true);
                            //imgCancelNewDwelling.setOpacity(0.25);
                        } catch (ParseException e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setHeaderText(e.getMessage());
                            alert.setContentText("Valid value:\ndd/MM/yyyy\nex. 17/11/2008");
                            alert.showAndWait();
                            //SETS THE CONFIRM AND CANCEL BUTTONS TO NOT CLICKABLE
                            //imgConfirmNewDwelling.setDisable(true);
                            //imgConfirmNewDwelling.setOpacity(0.25);
                            //imgCancelNewDwelling.setDisable(true);
                            //imgCancelNewDwelling.setOpacity(0.25);
                        }

                    });

        type_column.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getType()));
        type_column.setCellFactory(ComboBoxTableCell.forTableColumn(optionsType));
        type_column.setOnEditCommit(
                (CellEditEvent<Facility, String> t) -> {
                    ((Facility) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setType(t.getNewValue());
                });
        tbl_facilities.setItems(myFacilities);
        stage.show();
       
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void handleCbChange(ActionEvent action) {
        switch (cb_Facilities.getValue()) {
            case id:
                cb_Type.setDisable(true);
                dp_Facilities.setDisable(true);
                sp_Facilities.setDisable(false);
                break;
            case type:
                cb_Type.setDisable(false);
                dp_Facilities.setDisable(true);
                sp_Facilities.setDisable(true);

                break;
            case date:
                cb_Type.setDisable(true);
                dp_Facilities.setDisable(false);
                sp_Facilities.setDisable(true);
                break;

        }

    }

    @FXML
    void searchAction(ActionEvent action) {
        switch (cb_Facilities.getValue()) {
            case date:
                if (dp_Facilities.getValue() != null) {
                    try {
                        Date date = Date.from(dp_Facilities.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        List<Facility> facilities = new ArrayList<>();
                        List<Facility> fs = facMan.selectByDate(simpleDateFormat.format(date).toString());
                        if (fs.size() > 0) {
                            for (Facility f : fs) {
                                facilities.add(new Facility());
                            }
                            ObservableList<Facility> facilityTableBean
                                    = FXCollections.observableArrayList(facilities);
                            tbl_facilities.setItems(facilityTableBean);
                        }
                    } catch (BusinessLogicException ex) {
                        Logger.getLogger(FacilitiesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //THROW NEW EXCEPTION OF FIELDS EMPTY
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Empty fields");
                    alert.setHeaderText("The construction date is null");
                    alert.setContentText("Try again");
                    alert.showAndWait();
                }
                break;
            case type:
                if (cb_Type.getValue() != null) {
                    try {

                        List<Facility> facilities = new ArrayList<>();
                        List<Facility> fs = facMan.selectByType(cb_Type.getValue());
                        if (fs.size() > 0) {
                            for (Facility f : fs) {
                                facilities.add(new Facility());
                            }
                            ObservableList<Facility> facilityTableBean
                                    = FXCollections.observableArrayList(facilities);
                            tbl_facilities.setItems(facilityTableBean);
                        }
                    } catch (BusinessLogicException ex) {
                    }
                } else {

                }
                break;
            case id:
                if (sp_Facilities != null) {
                    Long aux = Long.valueOf(sp_Facilities.getValue().toString());
                    try {
                        List<Facility> facilities = new ArrayList<>();
                        Facility fs = facMan.selectById(aux);
                        if (fs != null) {

                            facilities.add(fs);
                            ObservableList<Facility> facilityTableBean
                                    = FXCollections.observableArrayList(facilities);
                            tbl_facilities.setItems(facilityTableBean);
                        }

                    } catch (BusinessLogicException ex) {
                        Logger.getLogger(FacilitiesController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                break;
        }
    }

    @FXML
    void clickAdd(MouseEvent event) {
        iv_add.setDisable(true);
        iv_add.setOpacity(0.25);
        iv_minus.setDisable(true);
        iv_minus.setOpacity(0.25);
        iv_check.setDisable(false);
        iv_check.setOpacity(1);
        iv_cancel.setDisable(false);
        iv_cancel.setOpacity(1);
        
        Facility ft = new Facility();
        ft.setId(Long.MIN_VALUE);
        ft.setAdquisitionDate(new Date());
        myFacilities.add(ft);

       tbl_facilities.getSelectionModel().select(myFacilities.size() - 1);
        tbl_facilities.layout();
        tbl_facilities.getFocusModel().focus(myFacilities.size() - 1, adq_column);
        tbl_facilities.edit(myFacilities.size() - 1, adq_column);

    }

    @FXML
    void pressComboBox(ActionEvent action) {
    }

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
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Error printing");
            alert.setContentText("There was an error printing the information, try again later");
            LOGGER.log(Level.SEVERE,
                    "Error printing report at printReport(): {0}",
                    ex.getMessage());
        }
    }

    @FXML
    void clickMinus(MouseEvent action) {
        Facility facTBean = tbl_facilities.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to delete\n this facility with the following ID:" + facTBean.getId() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                facMan.remove(facTBean.getId());
                //TODO .remove(facTBean);
                tbl_facilities.refresh();
            } catch (BussinessLogicException ex) {
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
    }

    @FXML
    void clickClose(MouseEvent action) {

    }

    @FXML
    void clickCheck(MouseEvent action) {
        Date date;
        Facility f = new Facility();
        f = tbl_facilities.getSelectionModel().getSelectedItem();
        int pos = tbl_facilities.getSelectionModel().getSelectedIndex();
        try {
              
            if (pos == myFacilities.size() - 1 && f.getId().equals(Long.MIN_VALUE)) {
                facMan.create(f);
                tbl_facilities.refresh();
                
            } else {
                
                facMan.update(f,f.getId());
                tbl_facilities.refresh();
               

            }
        } catch (BusinessLogicException ex) {
            Alert excAlert = new Alert(AlertType.INFORMATION);
            excAlert.setTitle("Error");
            excAlert.setContentText("There was an error with the edition of the service: " + ex.getMessage());
            excAlert.show();
           
        }
    }

    public void setFacMan(FacilityManager facMan) {
        this.facMan = facMan;
    }

    private void handleTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {

    }

}