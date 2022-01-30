package view.controllers;

import application.Application;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static org.testfx.matcher.base.NodeMatchers.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
 * Testing class for SignUp view and controller. Tests SignUp view behavior
 * using TestFX framework
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

    private ImageView imgPrint;

    private ObservableList<Dwelling> dwellingsCollectionTable;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");


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
    public void test01_verifyButtons() {
        verifyThat(newButton, isEnabled());
        verifyThat(confirmButton, isDisabled());
        verifyThat(cancelButton, isDisabled());

    }

    @Test
    public void test02_create() throws ParseException {
        clickOn(newButton);
        String newDwellingTitle = "Tartanga test " + new Random().nextInt();
        write(newDwellingTitle);
        press(KeyCode.TAB);
        press(KeyCode.SPACE).release(KeyCode.SPACE);
        clickOn(newDwellingTitle);
        press(KeyCode.ENTER);
        String today = dateFormatter.format(new Date());
        doubleClickOn(today);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("17/11/1997");
        press(KeyCode.ENTER);
        dwellingsCollectionTable = table.getItems();
        //The column that I want to edit
        TableColumn tc = (TableColumn) table.getColumns().get(2);
        table.getSelectionModel().select(dwellingsCollectionTable.size() - 1, tc);
        type(KeyCode.ENTER);
        //table.getFocusModel().focus(dwellingsCollectionTable.size() - 1, tc);
        //table.requestFocus();

        write("25.5");
        //press(KeyCode.ENTER);

    }

    @Test
    public void test03_delete() {
            
    }

}
