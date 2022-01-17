package view.controllers;

import com.sun.istack.internal.logging.Logger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.CommentBean;
import model.DwellingBean;
import model.Flat;
import model.Owner;
import model.User;

/**
 * Controller UI class for a single Dwelling view in the user's managements
 * application
 *
 * @author Ander Arruza
 */
public class DwellingController {
    
    private static final Logger LOGGER = Logger.getLogger(DwellingController.class);
    
    private DwellingBean dwelling;
    
    private User user;
    
    private Stage stage;
    @FXML
    private ImageView imgDwellingType;
    
    @FXML
    private Label lblTitle;
    
    @FXML
    private Label lblDwellingType;
    
    @FXML
    private ImageView imgDwelling;
    
    @FXML
    private Label lblOwner;
    
    @FXML
    private ImageView imgCreateComment;
    
    @FXML
    private ImageView imgConfirmComment;
    
    @FXML
    private ImageView imgCancelComment;
    
    @FXML
    private Label lblAdress;
    
    @FXML
    private Label lblsquareMeters;
    
    @FXML
    private Label lblConstructionDate;
    
    @FXML
    private Label lblWifi;
    
    @FXML
    private ImageView imgRating;
    
    @FXML
    private ImageView imgLight_or_Room;
    
    @FXML
    private Label lblLight_or_Room;
    
    @FXML
    private ImageView imgOutlets_or_Bathrooms;
    
    @FXML
    private Label lblOutlets_or_Bathrooms;
    
    @FXML
    private Separator separatorFacilities;
    
    @FXML
    private Hyperlink hlFacilities;
    
    @FXML
    private ImageView imgPrint;
    
    @FXML
    private TableView<?> tblComments;
    
    @FXML
    private TableColumn<?, ?> colUser;
    
    @FXML
    private TableColumn<?, ?> colComment;
    
    @FXML
    private TableColumn<?, ?> colRating;

    /**
     * This method is used to initialize the stage
     *
     * @param root The parent object representing root node of the view graph
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing DwellingWindow stage");
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
        //Load the Dwelling data depending if its a Flat or a Room with the loadDwellingData() method
        loadDwellingData();
        loadAllComments();

        //Shows the stage
        stage.show();
        LOGGER.info("DwellingWindow Open");
    }
    
    private void loadDwellingData() {
        //load all the data and the img
        lblDwellingType.setText(dwelling.getClass().getName());
        lblAdress.setText(dwelling.getAddress());
        lblsquareMeters.setText(String.valueOf(dwelling.getSquareMeters()));
        lblConstructionDate.setText(String.valueOf(dwelling.getConstructionDate()));
        lblWifi.setText((dwelling.getHasWiFi() ? "Yes" : "No"));
        String route = "/view/resources/img/dwelling/rating";
        route = route + Math.round(dwelling.getRating()) + ".png";
        imgRating.setImage(new Image(route));
        if (dwelling instanceof Flat) {
            imgLight_or_Room.setImage(new Image("/view/resources/img/dwelling/nRooms.png"));
            //lblLight_or_Room.setText(String.valueOf(((Flat)dwelling.getnRooms())));
            imgOutlets_or_Bathrooms.setImage(new Image("/view/resources/img/dwelling/toilet.png"));
            //lblOutlets_or_Bathrooms.setText(String.valueOf((Flat)dwelling.getnBathrooms()));
            separatorFacilities.setVisible(true);
            hlFacilities.setVisible(true);
        } else {
            imgLight_or_Room.setImage(new Image("/view/resources/img/dwelling/naturalLight.png"));
            //lblLight_or_Room.setText(String.valueOf(((Room)dwelling.getHasNaturalLight())));
            imgOutlets_or_Bathrooms.setImage(new Image("/view/resources/img/dwelling/outlet.png"));
            //lblOutlets_or_Bathrooms.setText(String.valueOf((Room)dwelling.getnOutlets()));
            separatorFacilities.setVisible(false);
            hlFacilities.setVisible(false);
        }

        //If logged in as a user the Confirm and Cancel images will be disabled
        if (user instanceof Owner) {
            imgConfirmComment.setDisable(true);
            imgCancelComment.setDisable(true);
            //If logged as an owner none of the buttons above the table will be shown
            imgCancelComment.setVisible(false);
            imgConfirmComment.setVisible(false);
            imgCreateComment.setVisible(false);
        } else {
            imgConfirmComment.setDisable(false);
            imgCancelComment.setDisable(false);
            
            imgCancelComment.setVisible(true);
            imgConfirmComment.setVisible(true);
            imgCreateComment.setVisible(true);
            
        }
        
    }
    
    private void loadAllComments() {
        LOGGER.info("GETTING ALL THE COMMENTS FROM THE LOGICAL PART");
        try {
            List<CommentBean> comments = dwelling.getComments();
            //Set factories for cell values
            colUser.setCellValueFactory(
                    new PropertyValueFactory<>("user"));
            colComment.setCellValueFactory(
                    new PropertyValueFactory<>("comment"));
            colRating.setCellValueFactory(
                    new PropertyValueFactory<>("rating"));
        } catch (Exception e) {
            LOGGER.severe("BUSSINESS LOGIC EXCEPTION");
        }
        
    }
    
    @FXML
    void handleAddComment(MouseEvent event) {
        
    }
    
    @FXML
    void handleCancelComment(MouseEvent event) {
        
    }
    
    @FXML
    void handleConfirmComment(MouseEvent event) {
        
    }
    
    @FXML
    /**
     * A JasperReport will be created depending on the data in the table given
     * the user the opportunity to eather save the data or print it.
     */
    void handlePrintComments(MouseEvent event) {
        
    }
    
    @FXML
    /**
     * Method that opens the FlatFacility Window
     */
    void handleViewFacilities(ActionEvent event) {
        
    }
    
}
