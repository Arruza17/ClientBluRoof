/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.fxml;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Facility;
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
    private Spinner<?> sp_Facilities;
    @FXML
    private Button srch_Btn;
    @FXML
    private TableView<?> tbl_facilities;
    @FXML
    private TableColumn<?, ?> id_Column;
    @FXML
    private TableColumn<?, ?> adq_column;
    @FXML
    private TableColumn<?, ?> type_column;
    @FXML
    private TableColumn<?, ?> more_Info_Column;
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
        iv_cancel.setDisable(true);
        iv_check.setDisable(true);
        ObservableList<String> options
                = FXCollections.observableArrayList("Id", "Type", "Date");
        cb_Facilities.setItems(options);
        cb_Facilities.setValue("Id");
        ObservableList<Facility> facilities=null;
        List<FacilityTableBean> factBean=null;
        id_Column.setCellFactory(new PropertyValueFactory<>("id"));
        adq_column.setCellValueFactory(new PropertyValueFactory<>("adqDate"));
        type_column.setCellFactory(new PropertyValueFactory<>("type"));
        
        
        
        
        
        stage.show();
    }
@FXML
void searchAction(ActionEvent action){
    
}
@FXML
void pressComboBox(ActionEvent action){
}
@FXML
void clickPrint(MouseEvent action){

}
@FXML
void clickAdd(MouseEvent action){
}
@FXML
void clickMinus(MouseEvent action){
}
@FXML
void clickClose(MouseEvent action){}
@FXML
void clickCheck(MouseEvent action){
}
}
