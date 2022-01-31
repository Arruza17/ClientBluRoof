/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import application.Application;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author jorge
 */
public class FacilitiesTest extends ApplicationTest{
    private final ImageView printButton=lookup("#iv_print").query();
    private final ImageView iv_add=lookup("iv_add").query();
    private final ImageView iv_minus=lookup("iv_minus").query();
    private final ImageView iv_check=lookup("iv_check").query();
    private final ImageView iv_cancel=lookup("iv_cancel").query();
    private final ComboBox cb_Facilities=lookup("cb_Facilities").query();
    private final DatePicker dp_Facilities=lookup("dp_Facilities").query();
    private final Spinner sp_Facilities=lookup("sp_Facilities").query();
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
    }
    @Test
    public void test01_loginAndPrint(){
    
    }
}
