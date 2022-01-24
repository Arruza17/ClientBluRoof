/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.fxml;

import exceptions.BusinessLogicException;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Facility;
import model.FacilityManager;
import model.FacilityTableBean;

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
    @FXML
    private TextField tf_Facilities;
    @FXML
    private Spinner<Integer> sp_Facilities;
    @FXML
    private Button srch_Btn;
    @FXML
    private TableView<FacilityTableBean> tbl_facilities;
    @FXML
    private TableColumn<FacilityTableBean, Long> id_Column;
    @FXML
    private TableColumn<FacilityTableBean, Date> adq_column;
    @FXML
    private TableColumn<FacilityTableBean, String> type_column;
    @FXML
    private TableColumn<FacilityTableBean, String> more_Info_Column;
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

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Facilities Window");
        tf_Facilities.setDisable(true);
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
        List<FacilityTableBean> facilities = new ArrayList<>();
        try {
            List<Facility> allFacilities = facMan.selectAll();
            if (allFacilities.size() > 0) {
                for (Facility f : allFacilities) {
                    facilities.add(new FacilityTableBean(f));
                }
                ObservableList<FacilityTableBean> facilityTableBean
                        = FXCollections.observableArrayList(facilities);
                tbl_facilities.setItems(facilityTableBean);
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
        adq_column.setCellValueFactory(
                new PropertyValueFactory<>("adqDate"));
        type_column.setCellValueFactory(
                new PropertyValueFactory<>("type"));

        more_Info_Column.setCellValueFactory(
                new PropertyValueFactory<>("moreInfo"));
        sp_Facilities.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99));
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void handleCbChange(ActionEvent action) {
        switch (cb_Facilities.getValue()) {
            case id:
                tf_Facilities.setDisable(true);
                dp_Facilities.setDisable(true);
                sp_Facilities.setDisable(false);
                break;
            case type:
                tf_Facilities.setDisable(false);
                dp_Facilities.setDisable(true);
                sp_Facilities.setDisable(true);
                break;
            case date:
                tf_Facilities.setDisable(true);
                dp_Facilities.setDisable(false);
                sp_Facilities.setDisable(true);
                break;

        }

    }

    @FXML
    void searchAction(ActionEvent action) {
        switch (cb_Facilities.getValue()) {
            case date:/*
                if (dp_Facilities.getValue() != null) {
                    try {
                        Date date = Date.from(dp_Facilities.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        List<Facility> fs = facMan.selectByDate(date);
                        System.out.println(fs.size());
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
                }*/
                break;
            case type:
                if (!tf_Facilities.getText().trim().equalsIgnoreCase("")) {
                    try {

                        List<FacilityTableBean> facilities = new ArrayList<>();
                        List<Facility> fs = facMan.selectByType(tf_Facilities.getText());
                        if (fs.size() > 0) {
                            for (Facility f : fs) {
                                facilities.add(new FacilityTableBean(f));
                            }
                            ObservableList<FacilityTableBean> facilityTableBean
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
                        List<FacilityTableBean> facilities = new ArrayList<>();
                        Facility fs = facMan.selectById(aux);
                        if (fs != null) {

                            facilities.add(new FacilityTableBean(fs));
                            ObservableList<FacilityTableBean> facilityTableBean
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
    private void clickAdd(MouseEvent event) {
        Facility f = null;
        FacilityTableBean tb = new FacilityTableBean(f);
        tbl_facilities.getItems().add(tb);
    }

    @FXML
    void pressComboBox(ActionEvent action) {
    }

    @FXML
    void clickPrint(MouseEvent action) {

    }

    @FXML
    void clickMinus(MouseEvent action) {

    }

    @FXML
    void clickClose(MouseEvent action) {
    }

    @FXML
    void clickCheck(MouseEvent action) {
    }

    public void setFacMan(FacilityManager facMan) {
        this.facMan = facMan;
    }

    private void handleTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {

    }
}
