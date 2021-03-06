package view.controllers;

import application.Application;
import exceptions.EmailFormatException;
import exceptions.FieldsEmptyException;
import exceptions.FullNameFormatException;
import exceptions.LoginFoundException;
import exceptions.MaxCharactersException;
import exceptions.PasswordFormatException;
import exceptions.PassNotEqualException;
import java.util.Random;
import static org.testfx.matcher.base.NodeMatchers.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
 * Testing class for SignUp view and controller. Tests SignUp view behavior
 * using TestFX framework
 *
 * @author Ander Arruza
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTest extends ApplicationTest {

    private final String MAX_CHARACTERS_EXAMPLE = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static final Logger LOGGER = Logger.getLogger(SignUpControllerTest.class.getName());
    /**
     * Needed variable to test if a username in the BD already exists
     */
    private final String USERNAME_IN_EXISTENCE = "test";
    private TextField tfUser;
    private TextField tfFullName;
    private TextField tfEmail;
    private PasswordField passField;
    private PasswordField rptPassword;
    private String username;

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
    public void test01_signUpOk() {
        //Go to the SignUp Window
        clickOn("#hlSignUp");
        writeAllData();
        clickOn("#btnSignUp");
        clickOn("Aceptar");
        verifyThat("User corretly added", isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        //System.out.println(nombreUsuarioCreado);
    }

    @Test
    public void test02_sameUserSignUp() {
        //Make sure that before executing this test USERNAME_IN_EXISTENCE
        //cointains a valid username in the BD
        clickOn("#hlSignUp");
        writeUserInExistence();
        clickOn("#btnSignUp");
        clickOn("Aceptar");
        verifyThat(new LoginFoundException().getMessage(), isVisible());
        //The following enter is to say that you want to add the user
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        //This enter is to accept the alert that the user already exists
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    
    @Test
    public void test03_tfUserWritten() {
        clickOn("#btnCancel");
        clickOn("#hlSignUp");
        writeAllData();
        clickOn(username);
        eraseText(username.length());
        clickOn("#btnSignUp");
        verifyThat(new FieldsEmptyException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);

    }

    @Test
    public void test04_tfFullNameWritten() {
        clickOn("#btnCancel");
        clickOn("#hlSignUp");
        writeAllData();
        tfFullName = lookup("#tfFullName").query();
        clickOn(tfFullName);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        clickOn("#btnSignUp");
        verifyThat(new FieldsEmptyException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(tfFullName);
        write("TextFx Robot");

    }

    @Test
    public void test05_passFieldWritten() {
        passField = lookup("#passField").query();
        clickOn(passField);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        clickOn("#btnSignUp");
        verifyThat(new FieldsEmptyException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#passField");
        write("abcd*1234");

    }

    @Test
    public void test06_rptPasswordWritten() {
        rptPassword = lookup("#rptPassword").query();
        clickOn(rptPassword);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        clickOn("#btnSignUp");
        verifyThat(new FieldsEmptyException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(rptPassword);
        write("abcd*1234");
    }

    @Test
    public void test07_tfEmailWritten() {
        tfEmail = lookup("#tfEmail").query();
        clickOn(tfEmail);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        clickOn("#btnSignUp");
        verifyThat(new FieldsEmptyException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(tfEmail);
        write("robot@robot.rb");
    }

    @Test
    public void test08_tfFullNameNoGap() {
        tfFullName = lookup("#tfFullName").query();
        clickOn(tfFullName);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("NoGapHere");
        clickOn("#btnSignUp");
        verifyThat(new FullNameFormatException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(tfFullName);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("TextFx Robot");
    }

    @Test
    public void test09_passwordNotEqual() {
        passField = lookup("#passField").query();
        rptPassword = lookup("#rptPassword").query();
        clickOn(passField);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("abcd*1234");
        clickOn(rptPassword);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("abcd*12345678");
        clickOn("#btnSignUp");
        verifyThat(new PassNotEqualException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(rptPassword);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("abcd*1234");
    }

    @Test
    public void test10_passwordTooShort() {
        rptPassword = lookup("#rptPassword").query();
        passField = lookup("#passField").query();
        clickOn(passField);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("a");
        clickOn(rptPassword);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("a");
        clickOn("#btnSignUp");
        verifyThat(new PasswordFormatException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(passField);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("abcd*1234");
        clickOn(rptPassword);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("abcd*1234");
    }

    @Test
    public void test11_emailValidFormat() {
        tfEmail = lookup("#tfEmail").query();
        clickOn(tfEmail);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("e");
        clickOn("#btnSignUp");
        verifyThat(new EmailFormatException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(tfEmail);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("robot@robot.rb");

    }

    @Test
    public void test12_tfUserMaxCharacter() {
        clickOn("#btnCancel");
        clickOn("#hlSignUp");
        writeAllData();
        clickOn(username);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write(MAX_CHARACTERS_EXAMPLE);
        clickOn("#btnSignUp");
        verifyThat(new MaxCharactersException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#btnCancel");
        clickOn("#hlSignUp");
        writeAllData();

    }

    @Test
    public void test13_tfFullNameMaxCharacter() {
        tfFullName = lookup("#tfFullName").query();
        clickOn("#tfFullName");
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        tfFullName.setText(MAX_CHARACTERS_EXAMPLE);
        clickOn("#btnSignUp");
        verifyThat(new MaxCharactersException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(tfFullName);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("TextFx Robot");
    }

    @Test
    public void test14_passFieldMaxCharacter() {
        passField = lookup("#passField").query();
        clickOn(passField);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        passField.setText(MAX_CHARACTERS_EXAMPLE);
        clickOn("#btnSignUp");
        verifyThat(new MaxCharactersException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(passField);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("abcd*1234");
    }
    
    @Test
    public void test15_rptPasswordMaxCharacter() {
        rptPassword = lookup("#rptPassword").query();
        clickOn(rptPassword);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        rptPassword.setText(MAX_CHARACTERS_EXAMPLE);
        clickOn("#btnSignUp");
        verifyThat(new MaxCharactersException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(rptPassword);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("abcd*1234");
    }

    @Test
    public void test16_tfEmailMaxCharacter() {
        tfEmail = lookup("#tfEmail").query();
        clickOn(tfEmail);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        tfEmail.setText(MAX_CHARACTERS_EXAMPLE);
        clickOn("#btnSignUp");
        verifyThat(new MaxCharactersException().getMessage(), isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(tfEmail);
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("robot@robot.rb");
    }
     
    private void writeAllData() {
        //Generate a random user
        Random rand = new Random(); //instance of random class
        int upperbound = 999999;
        //Generate random values from 0-999998
        int int_random = rand.nextInt(upperbound);
        username = "robot" + int_random;
        //Guardar la variable para luego
        write(username);
        clickOn("#tfFullName");
        write("TextFx Robot");
        clickOn("#passField");
        write("abcd*1234");
        clickOn("#rptPassword");
        write("abcd*1234");
        clickOn("#tfEmail");
        write("robot@robot.rb");
    }
    private void writeUserInExistence(){
        write(USERNAME_IN_EXISTENCE);
        clickOn("#tfFullName");
        write("TextFx Robot");
        clickOn("#passField");
        write("abcd*1234");
        clickOn("#rptPassword");
        write("abcd*1234");
        clickOn("#tfEmail");
        write("robot@robot.rb");
    }

}
