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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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

    private TextField tfServices;
    private String maxCharSample = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

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
    public void test02_printOK() {

        try {
            verifyThat("#imgPrint", isEnabled());
            clickOn("#imgPrint");

            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServiceControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void test03_cbServiceDisableComponents() {

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
    public void test04_selectAll() {

        clickOn("#cbService");
        clickOn("All services");
        clickOn("#btnSearchService");

    }

    @Test
    public void test05_selectByAddress() {

        clickOn("#cbService");
        clickOn("By address");
        clickOn("#tfServices");
        write("Bilbao, Gran via avenue");
        clickOn("#btnSearchService");

    }

    @Test
    public void test06_selectByName() {

        clickOn("#cbService");
        clickOn("By name");
        clickOn("#tfServices");
        write("El corte Inglés");
        clickOn("#btnSearchService");

    }

    @Test
    public void test07_selectByType() {

        clickOn("#cbService");
        clickOn("By type");
        clickOn("#cbServiceType");
        clickOn("TRAVELLING");
        clickOn("#btnSearchService");

    }

    @Test
    public void test08_addServiceCorrect() {

        try {
            clickOn("#imgAdd");
            write("Tartanga street, 15");
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.RIGHT).release(KeyCode.RIGHT);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            write("CIFP Tartanga");
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.RIGHT).release(KeyCode.RIGHT);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.TAB).release(KeyCode.TAB);
            Thread.sleep(100);
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.TAB).release(KeyCode.TAB);
            Thread.sleep(100);
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.TAB).release(KeyCode.TAB);
            Thread.sleep(100);
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            Thread.sleep(100);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            press(KeyCode.TAB).release(KeyCode.TAB);
            Thread.sleep(100);
            press(KeyCode.DOWN).release(KeyCode.DOWN);
            Thread.sleep(100);
            clickOn("#imgCommit");
        } catch (InterruptedException ex) {
            Logger.getLogger(ServiceControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void test09_editServiceCorrect() {
        try {

            doubleClickOn("Madrid, Lavapies street");
            press(KeyCode.CONTROL);
            press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
            eraseText(1);
            write("Bilbao, Indautxu street");
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            doubleClickOn("Manolo's pedicure");
            press(KeyCode.CONTROL);
            press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
            eraseText(1);
            write("GAME videogame shop");
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            Thread.sleep(100);
            doubleClickOn("HEALTH");
            clickOn("SHOPPING");
            clickOn("#imgCommit");
            Thread.sleep(2000);
            verifyThat("#imgCommit", isDisabled());
            verifyThat("#imgCancel", isDisabled());

        } catch (InterruptedException ex) {
            Logger.getLogger(ServiceControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void test10_deleteServiceCorrect() {

        clickOn("#tcAddress");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        clickOn("#imgDelete");
        verifyThat("Aceptar", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void test11_deleteServiceCancelled() {

        clickOn("#tcAddress");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        clickOn("#imgDelete");
        verifyThat("Cancelar", isVisible());
        clickOn("Cancelar");
        sleep(2000);
        clickOn("Aceptar");
    }

    @Test
    public void test12_tfServicesEmptyFieldException() {

        clickOn("#cbService");
        clickOn("By address");
        clickOn("#btnSearchService");
        verifyThat("The search field is empty", isVisible());
        clickOn("Aceptar");

    }

    @Test
    public void test13_tfServicesMaxCharactersException() {

        try {
            clickOn("#cbService");
            clickOn("By address");
            tfServices = lookup("#tfServices").query();
            clickOn(tfServices);

            tfServices.setText(maxCharSample);
            clickOn("#btnSearchService");
            verifyThat("Search field has a length higher than 255 characters.", isVisible());
            Thread.sleep(1500);
            clickOn("Aceptar");
        } catch (InterruptedException ex) {
            Logger.getLogger(ServiceControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
