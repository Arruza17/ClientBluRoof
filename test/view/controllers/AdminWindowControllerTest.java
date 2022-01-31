package view.controllers;

import application.Application;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Yeray Sampedro
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminWindowControllerTest extends ApplicationTest {

    private static String adminLogin = "AdminTest";

    private static final String MAX_CHAR = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    private static ImageView addButton;

    private static ImageView confirmButton;

    private static ImageView cancelButton;

    private static ImageView deleteButton;

    private static TableView table;

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
        adminLogin += new Random().nextInt();
    }

    @Test
    public void preparation_login() {
        write("admin");

        press(KeyCode.TAB).release(KeyCode.TAB);
        write("abcd*1234");

        clickOn("Sign In");
        BorderPane pane = lookup("#bpMain").query();
        verifyThat(pane, isVisible());
    }

    @Test
    public void test00_loadAdminCrudWindow() {
        clickOn("Admin CRUD");
        table = lookup("#tblAdmin").queryTableView();
        verifyThat(table, isVisible());
    }

    @Test
    public void test01_addAdmin() {
        try {
            int rows = table.getItems().size();
            addButton = lookup("#imgAdd").query();
            confirmButton = lookup("#imgCommit").query();
            cancelButton = lookup("#imgCancel").query();
            deleteButton = lookup("#imgDel").query();
            Thread.sleep(100);
            clickOn(addButton);

            write("Admin Test");
            //Go to the next cell
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);

            write(adminLogin);
            //Go to the next cell
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);

            write("admintest@gmail.com");
            //Go to the next cell
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);

            //Select the datepicker
            press(KeyCode.TAB).release(KeyCode.TAB);
            Thread.sleep(100);
            //Open the datepicker
            press(KeyCode.F4).release(KeyCode.F4);
            Thread.sleep(100);
            //Select the date
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            Thread.sleep(100);
            //Confirm the date
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            //Go to the next cell
            press(KeyCode.ENTER).release(KeyCode.ENTER);

            write("+34666666666");
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            clickOn(confirmButton);
            assertNotEquals("Row added", rows, table.getItems().size());
        } catch (InterruptedException ex) {
            Logger.getLogger(AdminWindowControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void test02_editAdmin() {
        try {
            clickOn("Search");
            Thread.sleep(200);
            doubleClickOn("Admin Test");
            /*verifyThat(confirmButton, isEnabled());
            verifyThat(cancelButton, isEnabled());*/
            press(KeyCode.CONTROL);
            press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
            write("Admin update");
            //Go to the next cell
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);

            write("updateAdmin");
            //Go to the next cell
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);

            write("adminupdate@gmail.com");
            //Go to the next cell
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);

            //Select the datepicker
            press(KeyCode.TAB).release(KeyCode.TAB);
            Thread.sleep(100);
            //Open the datepicker
            press(KeyCode.F4).release(KeyCode.F4);
            Thread.sleep(100);
            //Select the date
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            Thread.sleep(100);
            //Confirm the date
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            //Go to the next cell
            press(KeyCode.ENTER).release(KeyCode.ENTER);

            write("+34777777777");
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            clickOn(confirmButton);
            Thread.sleep(200);
            //Go to the next cell
            clickOn("Aceptar");

        } catch (InterruptedException ex) {
            Logger.getLogger(AdminWindowControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void test04_deleteAdmin() {
        try {
            int rows = table.getItems().size();
            Node row = lookup(".table-row-cell").nth(table.getItems().size() - 1).query();
            clickOn(row);
            Thread.sleep(100);
            clickOn(deleteButton);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            assertNotEquals("Row deleted", rows, table.getItems().size());
        } catch (InterruptedException ex) {
            Logger.getLogger(AdminWindowControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
