package view.controllers;

import application.Application;
import exceptions.FieldsEmptyException;
import exceptions.MaxCharactersException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import static org.testfx.matcher.base.NodeMatchers.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import model.Dwelling;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

/**
 * Testing class for OwnerWindowController view and controller. Tests
 * ownerWindow view behavio using TestFX framework
 *
 * @author Ander Arruza
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OwnerWindowControllerTest extends ApplicationTest {

    private final String MAX_CHARACTERS_EXAMPLE = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static final Logger LOGGER = Logger.getLogger(OwnerWindowControllerTest.class.getName());
    /**
     * Needed variable to test if a username in the BD already exists
     */
    private final ImageView newButton = lookup("#imgCreateNewDwelling").query();

    private final ImageView confirmButton = lookup("#imgConfirmNewDwelling").query();

    private final ImageView cancelButton = lookup("#imgCancelNewDwelling").query();

    private final ImageView deleteButton = lookup("#imgDeleteDwelling").query();

    private final DatePicker dpConstructionDate = lookup("#dpConstructionDate").query();

    private final TableView table = lookup("#tableDwelling").queryTableView();

    private final ComboBox comboFilter = lookup("#cbDwellings").queryComboBox();

    private final Spinner spinner = lookup("#spRating").query();

    private ImageView imgPrint;

    private ObservableList<Dwelling> dwellingsCollectionTable;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    private static String newDwellingAddress;


    /*
        @Override
        public void start(Stage stage) throws Exception {
        new Application().start(stage);
    }
     */
    /**
     *
     * @throws TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);

    }

    @Test
    public void test01_verifyButtons() throws InterruptedException {
        clickOn("#tfUser");
        write("OwnerTest");
        clickOn("#tfPassword");
        write("abcd*1234");
        clickOn("Sign In");
        clickOn("My Dwellings");
        //verifyThat(newButton, isEnabled());
        //verifyThat(confirmButton, isDisabled());
        //verifyThat(cancelButton, isDisabled());
        //verifyThat(dpConstructionDate, isDisabled());
        //verifyThat(spinner, isDisabled());
    }

    @Test
    public void test02_filterByMinDate() throws InterruptedException {
        ComboBox cb = lookup("#cbDwellings").queryComboBox();
        DatePicker datePicker = lookup("#dpConstructionDate").query();
        ImageView search = lookup("#imgSearch").query();
        clickOn(cb);
        clickOn("Min construction date");
        clickOn(datePicker);
        press(KeyCode.F4).release(KeyCode.F4);
        //Select the date
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        //Confirm the date
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(search);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }
        @Test
    public void test03_filterByMinRating() throws InterruptedException {
        ComboBox cb = lookup("#cbDwellings").queryComboBox();
        Spinner spinner = lookup("#spRating").query();
        ImageView search = lookup("#imgSearch").query();
        clickOn(cb);
        clickOn("Min rating");
        clickOn(spinner);
        
        clickOn(search);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }
    

    @Ignore
    @Test
    public void test03_create() throws ParseException {
        clickOn(newButton);
        newDwellingAddress = "Tartanga test " + new Random().nextInt();
        write(newDwellingAddress);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.SPACE).release(KeyCode.SPACE);
        clickOn(newDwellingAddress);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        write("25.5");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("17/11/1997");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(confirmButton);

    }

    @Ignore
    @Test
    public void test03_modidyAddress() {
        doubleClickOn(newDwellingAddress);
        newDwellingAddress = "Tartanga test " + new Random().nextInt();
        write(newDwellingAddress);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(confirmButton);
    }

    @Ignore
    @Test
    public void test04_modidyAddressEmpty() throws InterruptedException {
        doubleClickOn(newDwellingAddress);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        verifyThat(new FieldsEmptyException().getMessage(), isVisible());
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
    }

    @Ignore
    @Test
    public void test05_modidyAddress_255() throws InterruptedException {
        doubleClickOn(newDwellingAddress);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write(MAX_CHARACTERS_EXAMPLE);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        verifyThat(new MaxCharactersException().getMessage(), isVisible());
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);

    }

}
