package view.controllers;

import application.Application;
import java.text.ParseException;
import java.util.Random;
import static org.testfx.matcher.base.NodeMatchers.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

    private static final Logger LOGGER = Logger.getLogger(OwnerWindowControllerTest.class.getName());

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
    public void test00_enterInWindow() throws InterruptedException {
        clickOn("#tfUser");
        write("OwnerTest");
        clickOn("#tfPassword");
        write("abcd*1234");
        clickOn("Sign In");
        clickOn("My Dwellings");
    }
    @Ignore
    @Test
    public void test01_verifyButtons() throws InterruptedException {
        ImageView newButton = lookup("#imgCreateNewDwelling").query();
        ImageView confirmButton = lookup("#imgConfirmNewDwelling").query();
        ImageView cancelButton = lookup("#imgCancelNewDwelling").query();
        ImageView deleteButton = lookup("#imgDeleteDwelling").query();
        DatePicker dpConstructionDate = lookup("#dpConstructionDate").query();
        Spinner spinner = lookup("#spRating").query();
        verifyThat(newButton, isEnabled());
        verifyThat(confirmButton, isDisabled());
        verifyThat(cancelButton, isDisabled());
        verifyThat(dpConstructionDate, isDisabled());
        verifyThat(deleteButton, isDisabled());
        verifyThat(spinner, isDisabled());
    }
    @Ignore
    @Test
    public void test02_filterByMinDate() throws InterruptedException {
        ComboBox cb = lookup("#cbDwellings").queryComboBox();
        DatePicker datePicker = lookup("#dpConstructionDate").query();
        Spinner spinner = lookup("#spRating").query();
        ImageView search = lookup("#imgSearch").query();
        clickOn(cb);
        clickOn("Min construction date");
        verifyThat(spinner, isDisabled());
        clickOn(datePicker);
        press(KeyCode.F4).release(KeyCode.F4);
        //Select the date
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        //Confirm the date
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(search);
        verifyThat("Dwellings have been found", isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }
    @Ignore
    @Test
    public void test03_filterByMinRating() throws InterruptedException {
        ComboBox cb = lookup("#cbDwellings").queryComboBox();
        Spinner spinner = lookup("#spRating").query();
        DatePicker datePicker = lookup("#dpConstructionDate").query();
        ImageView search = lookup("#imgSearch").query();
        clickOn(cb);
        clickOn("Min rating");
        verifyThat(datePicker, isDisabled());
        clickOn(spinner);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        clickOn(search);
        verifyThat("Dwellings have been found", isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }
    @Ignore
    @Test
    public void test04_filterAll() throws InterruptedException {
        ComboBox cb = lookup("#cbDwellings").queryComboBox();
        Spinner spinner = lookup("#spRating").query();
        DatePicker datePicker = lookup("#dpConstructionDate").query();
        ImageView search = lookup("#imgSearch").query();
        clickOn(cb);
        clickOn("All my dwellings");
        verifyThat(datePicker, isDisabled());
        verifyThat(spinner, isDisabled());
        clickOn(search);
        verifyThat("Dwellings have been found", isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Test
    public void test05_create() throws ParseException {
        ImageView newButton = lookup("#imgCreateNewDwelling").query();
        ImageView confirmButton = lookup("#imgConfirmNewDwelling").query();
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

    @Test
    public void test06_badSquareMetersFormat() throws InterruptedException {
        doubleClickOn(newDwellingAddress);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        write("vabyweiubyeoa");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        Thread.sleep(2000);
        verifyThat("The squareMeter value is not valid", isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Test
    public void test07_badDateFormat() throws InterruptedException {
        doubleClickOn(newDwellingAddress);
        Thread.sleep(2000);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        Thread.sleep(2000);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        Thread.sleep(2000);
        write("vabyweiubyeoa");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        verifyThat("Not valid date", isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }
    @Ignore
    @Test
    public void test08_deleteCancel() throws InterruptedException {
        ImageView deleteButton = lookup("#imgDeleteDwelling").query();
        clickOn(newDwellingAddress);
        clickOn(deleteButton);
        clickOn("Cancel");
        press(KeyCode.ENTER).release(KeyCode.ENTER);

    }

    @Test
    public void test10_deleteConfirm() throws InterruptedException {
        ImageView deleteButton = lookup("#imgDeleteDwelling").query();
        clickOn(newDwellingAddress);
        clickOn(deleteButton);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }
    @Test
    public void test99_serverDown() throws InterruptedException {
        ImageView search = lookup("#imgSearch").query();
        clickOn(search);
        verifyThat("Error with the server", isVisible());
    }

}
