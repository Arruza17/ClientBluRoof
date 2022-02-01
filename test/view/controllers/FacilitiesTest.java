package view.controllers;

import application.Application;
import java.awt.Window;
import java.text.SimpleDateFormat;
import static java.time.Instant.now;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.robot.Motion;

/**
 *
 * @author jorge
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FacilitiesTest extends ApplicationTest {

    private final ImageView iv_print = lookup("#iv_print").query();
    private final ImageView iv_add = lookup("#iv_add").query();
    private final ImageView iv_minus = lookup("#iv_minus").query();
    private final ImageView iv_check = lookup("#iv_check").query();
    private final ImageView iv_cancel = lookup("#iv_cancel").query();
    private final ComboBox cb_Facilities = lookup("#cb_Facilities").queryComboBox();
    private final DatePicker dp_Facilities = lookup("#dp_Facilities").query();
    private final Spinner sp_Facilities = lookup("#sp_Facilities").query();
    private static String currentDate=null;
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
    }

    @Test
    public void test01_login() {
        clickOn("#tfUser");
        write("admin");
        clickOn("#tfPassword");
        write("abcd*1234");
        clickOn("#primaryButton");
        clickOn("#menuFacilitiesCrud");
    }

    @Test
    public void test02_PrintWorking() {
        clickOn("#iv_print");
        Window[] jframe=Window.getWindows();
        verifyThat("Report generated", isVisible());
        clickOn("Aceptar");
        closeCurrentWindow();
    }

    @Test
    public void test03_combosCorrectlyDisabled() {
        clickOn("#cb_Facilities");
        clickOn("All");
        verifyThat("#cb_Type", isDisabled());
        verifyThat("#sp_Facilities", isDisabled());
        verifyThat("#dp_Facilities", isDisabled());
        clickOn("#cb_Facilities");
        clickOn("Id");
        verifyThat("#sp_Facilities", isEnabled());
        verifyThat("#cb_Type", isDisabled());
        verifyThat("#dp_Facilities", isDisabled());
        clickOn("#cb_Facilities");
        clickOn("Type");
        verifyThat("#cb_Type", isEnabled());
        verifyThat("#sp_Facilities", isDisabled());
        verifyThat("#dp_Facilities", isDisabled());
        clickOn("#cb_Facilities");
        clickOn("Date");
        verifyThat("#cb_Type", isDisabled());
        verifyThat("#sp_Facilities", isDisabled());
        verifyThat("#dp_Facilities", isEnabled());
    }

    @Test
    public void test04_selectAll() {
        clickOn("#cb_Facilities");
        clickOn("All");
        clickOn("Search");
        //verifyThat("#adq_column", );
    }

    @Test
    public void test05_selectByDate(){
        clickOn("#cb_Facilities");
        clickOn("Date");
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.F4).release(KeyCode.F4);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("Search");
    }

    @Test
    public void test06_selectByType(){
        clickOn("#cb_Facilities");
        clickOn("Type");
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.F4).release(KeyCode.F4);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("Search");
    }

    @Test
    public void test07_selectById(){
        clickOn("#cb_Facilities");
        clickOn("Id");
        press(KeyCode.TAB).release(KeyCode.TAB);
        clickOn("Search");
    }

    @Test
    public void test08_addFacilityCorrect(){
            clickOn("#iv_add");
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            press(KeyCode.RIGHT).release(KeyCode.RIGHT);
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            press(KeyCode.RIGHT).release(KeyCode.RIGHT);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            press(KeyCode.TAB).release(KeyCode.TAB);
            press(KeyCode.F4).release(KeyCode.F4);
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            
            clickOn("#iv_check");
            clickOn("Aceptar");
             SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
             Date now= new Date();
             String today=simpleDateFormat.format(now);
             currentDate=today;
            verifyThat(today,isVisible());
    }

    @Test
    public void test09_deleteFacilityCorrect(){
        if(iv_cancel.isDisabled()){
            clickOn("#type_column");
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            clickOn("#iv_minus");
            verifyThat("Aceptar",isVisible());
            clickOn("Aceptar");
        }else{
            clickOn("#iv_cancel");
            clickOn("#Aceptar");
             clickOn("#type_column");
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            clickOn("#iv_minus");
            verifyThat("Aceptar",isVisible());
            clickOn("Aceptar");
        }
    }

    @Test
    public void test10_modifyFacilityCorrect(){
    if(iv_check.isDisabled()){
        doubleClickOn(currentDate);
        write("12/12/1999");
        press(KeyCode.ENTER);
        clickOn("#iv_check");
        clickOn("Aceptar");
        verifyThat("Content updated", isVisible());
        clickOn("Aceptar");
    }
    }

     @Test
    public void test11_closeButton(){
    if(iv_cancel.isDisabled()){
        clickOn("#iv_add");
        clickOn("#iv_cancel");
        verifyThat("Are you sure you want to stop creating/updatting?", isVisible());
        clickOn("Aceptar");
    }else{
        clickOn("#iv_cancel");
        verifyThat("Are you sure you want to stop creating/updatting?", isVisible());
        clickOn("Aceptar");
    }
    }
    
}
