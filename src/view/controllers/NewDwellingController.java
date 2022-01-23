package view.controllers;

import exceptions.BussinessLogicException;
import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
import exceptions.NotValidSquareMetersValueException;
import interfaces.DwellingManager;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Dwelling;
import model.User;

/**
 *
 * @author ander
 */
public class NewDwellingController {

    @FXML
    private TextField tfAddress;

    @FXML
    private RadioButton rbWiFiYes;

    @FXML
    private ToggleGroup wiFiGroup;

    @FXML
    private RadioButton rbWiFiNo;

    @FXML
    private DatePicker dpConstructionDate;

    @FXML
    private TextField tfSquareMeters;

    @FXML
    private ImageView imgCancel;

    @FXML
    private ImageView imgAdd;

    private Stage stage;

    private static final Logger LOGGER = Logger.getLogger(NewDwellingController.class.getSimpleName());

    private DwellingManager dwellingManager;

    private User user;

    private final String regex = "^([+]?(\\d+\\.)?\\d+)$";

    /**
     *
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing NewDwelling stage");
        //Creation of a new Scene
        Scene scene = new Scene(root);
        //Save the route of the .css file
        //String css = this.getClass().getResource("/view/resources/styles/CSSLogin.css").toExternalForm();
        //Sets the .css to the Scene
        //scene.getStylesheets().add(css);
        //stage.getIcons().add(new Image("/view/resources/img/BluRoofLogo.png"));
        //Sets the scene to the stage
        stage.setScene(scene);
        //Sets the Title of the Window
        stage.setTitle("NewDwellingWindow");
        //Sets the window not resizable
        stage.setResizable(false);

        stage.show();
        LOGGER.info("NewDwelling-Window Open");
    }

    /**
     *
     * @param event
     */
    @FXML
    void handleAddNewDwelling(MouseEvent event) {
        try {
            if (checkAllFields()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure you want to add this dweeling?");
                alert.setContentText("Are you ok with this?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    RadioButton selectedRadioButton = (RadioButton) wiFiGroup.getSelectedToggle();
                    String toogleGroupValue = selectedRadioButton.getText();
                    boolean hasWifi = (toogleGroupValue.equals("Yes"));
                    Date date = Date.from(dpConstructionDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    /*
                    Dwelling newDwelling = new Dwelling(
                            null, //id
                            new SimpleStringProperty(tfAddress.getText().trim()), //address
                            new SimpleBooleanProperty(hasWifi), //wifi
                            new SimpleDoubleProperty(Double.valueOf(tfSquareMeters.getText().trim())), //squareMeters
                            null,
                            new SimpleObjectProperty<Date>(date)),
                            null,
                            this.user, //owner
                            null);
                     */
                    dwellingManager.add(null);
                }
            }
        } catch (FieldsEmptyException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait();
        } catch (MaxCharactersException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait();
        } catch (NotValidSquareMetersValueException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText("Valid values:\n1\n1.2");
            alert.showAndWait();
        } catch (BussinessLogicException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText("Error while connecting to the server");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    void handleCancelNewDwelling(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you dont want to add this dweeling?");
        alert.setContentText("Are you ok with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            stage.close();
        }
    }

    /**
     * Method that checks if every field is written correctly
     *
     * @return true if everyField is ok
     * @throws FieldsEmptyException if any field has nothing
     * @throws MaxCharactersException if any field has more than 255 chars
     * @throws NotValidSquareMetersValueException if the squareMeters has a not
     * valid String
     */
    private boolean checkAllFields() throws FieldsEmptyException, MaxCharactersException, NotValidSquareMetersValueException {
        //Checks if all the fields are written
        if (tfAddress.getText().trim().isEmpty()
                || dpConstructionDate.getValue() == null
                || tfSquareMeters.getText().trim().isEmpty()) {
            //throw validation Error
            LOGGER.warning("Some fields are empty");
            throw new FieldsEmptyException();
        }
        //Checks if the fields have more than 255 characters
        if (tfAddress.getText().trim().length() > 255
                || tfSquareMeters.getText().trim().length() > 255) {
            //throw validation Error
            LOGGER.warning("Some field/s are more than >255 characters");
            throw new MaxCharactersException();
        }
        //Checks if the squareMeters field is written corretly 
        if (!tfSquareMeters.getText().matches(regex)) {
            //throw validation Error
            LOGGER.warning("The squareMeter value is not valid");
            throw new NotValidSquareMetersValueException("The squareMeter value is not valid");
        }

        return true;
    }

    /**
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @param dwellingManager
     */
    public void setDwellingManager(DwellingManager dwellingManager) {
        this.dwellingManager = dwellingManager;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
