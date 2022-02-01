/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import application.Application;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.base.WindowMatchers.isShowing;

/**
 *
 * @author Adrián
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceControllerTest extends ApplicationTest {

    private ComboBox cbService;

    /**
     * This method sets the primary stage of the javaFx window and sets up the
     * application.
     *
     * @throws TimeoutException
     */
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
        clickOn("#menuServiceCrud");

    }

    @Test
    public void test02_printOk() {

        verifyThat("#imgPrint", isEnabled());
        clickOn("#imgPrint");

    }   

    @Test
    public void test03_cbDisableComponents() {

        clickOn("#cbService");
        clickOn("All services");
        verifyThat("#tfServices", isDisabled());
        verifyThat("#cbServiceType", isDisabled());
        verifyThat("#imgAdd", isEnabled());
        verifyThat("#imgDelete", isDisabled());
        verifyThat("#imgCommit", isDisabled());
        verifyThat("#imgCancel", isDisabled());

        clickOn("#cbService");
        clickOn("By address");
        verifyThat("#tfServices", isEnabled());
        verifyThat("#cbServiceType", isDisabled());
        verifyThat("#imgAdd", isEnabled());
        verifyThat("#imgDelete", isDisabled());
        verifyThat("#imgCommit", isDisabled());
        verifyThat("#imgCancel", isDisabled());

        clickOn("#cbService");
        clickOn("By name");
        verifyThat("#tfServices", isEnabled());
        verifyThat("#cbServiceType", isDisabled());
        verifyThat("#imgAdd", isEnabled());
        verifyThat("#imgDelete", isDisabled());
        verifyThat("#imgCommit", isDisabled());
        verifyThat("#imgCancel", isDisabled());
        clickOn("#cbService");
        clickOn("By type");
        verifyThat("#tfServices", isDisabled());
        verifyThat("#cbServiceType", isEnabled());
        verifyThat("#imgAdd", isEnabled());
        verifyThat("#imgDelete", isDisabled());
        verifyThat("#imgCommit", isDisabled());
        verifyThat("#imgCancel", isDisabled());

    }
    
     @Test
    public void test04_cbDisableCompone() {
        
         

    }

    
    

}
