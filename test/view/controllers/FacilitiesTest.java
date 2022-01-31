package view.controllers;

import application.Application;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javax.swing.JFrame;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
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
    private final ImageView iv_add = lookup("iv_add").query();
    private final ImageView iv_minus = lookup("iv_minus").query();
    private final ImageView iv_check = lookup("iv_check").query();
    private final ImageView iv_cancel = lookup("iv_cancel").query();
    private final ComboBox cb_Facilities = lookup("cb_Facilities").queryComboBox();
    private final DatePicker dp_Facilities = lookup("dp_Facilities").query();
    private final Spinner sp_Facilities = lookup("sp_Facilities").query();

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
        jframe.getClass();
        //clickOn(window("FacilitiesJasper"));
        //verifyThat(window("FacilitiesJasper"), WindowMatchers.isFocused());
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
    }
}
