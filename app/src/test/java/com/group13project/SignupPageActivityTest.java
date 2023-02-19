package com.group13project;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SignupPageActivityTest {
    static SignupPageActivity signupPageActivity;

    @BeforeClass
    public static void setup() {
        signupPageActivity = new SignupPageActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testValidateInputValid() {
        String fName = "John";
        String lName = "Doe";
        String email = "johndoe@example.com";
        String phone = "1234567890";
        String pass = "password";
        String passConfirm = "password";

        boolean result = signupPageActivity.validateInput(fName, lName, email, phone, pass, passConfirm);

        assertTrue(result);
    }

    @Test
    public void testValidateInputInvalidFirstName() {
        String fName = "";
        String lName = "Doe";
        String email = "johndoe@example.com";
        String phone = "1234567890";
        String pass = "password";
        String passConfirm = "password";

        boolean result = signupPageActivity.validateInput(fName, lName, email, phone, pass, passConfirm);

        assertFalse(result);
    }

    @Test
    public void testValidateInputInvalidLastName() {
        String fName = "John";
        String lName = "";
        String email = "johndoe@example.com";
        String phone = "1234567890";
        String pass = "password";
        String passConfirm = "password";

        boolean result = signupPageActivity.validateInput(fName, lName, email, phone, pass, passConfirm);

        assertFalse(result);
    }

    @Test
    public void testValidateInputInvalidEmail() {
        String fName = "John";
        String lName = "Doe";
        String email = "johndoe";
        String phone = "1234567890";
        String pass = "password";
        String passConfirm = "password";

        boolean result = signupPageActivity.validateInput(fName, lName, email, phone, pass, passConfirm);

        assertFalse(result);
    }

    @Test
    public void testValidateInputInvalidPhone() {
        String fName = "John";
        String lName = "Doe";
        String email = "johndoe@example.com";
        String phone = "1234";
        String pass = "password";
        String passConfirm = "password";

        boolean result = signupPageActivity.validateInput(fName, lName, email, phone, pass, passConfirm);

        assertFalse(result);
    }

    @Test
    public void testValidateInputInvalidPassword() {
        String fName = "John";
        String lName = "Doe";
        String email = "johndoe@example.com";
        String phone = "1234567890";
        String pass = "pass";
        String passConfirm = "pass";

        boolean result = signupPageActivity.validateInput(fName, lName, email, phone, pass, passConfirm);

        assertFalse(result);
    }

    @Test
    public void testValidateInputInvalidPasswordConfirm() {
        String fName = "John";
        String lName = "Doe";
        String email = "johndoe@example.com";
        String phone = "1234567890";
        String pass = "password";
        String passConfirm = "password1";

        boolean result = signupPageActivity.validateInput(fName, lName, email, phone, pass, passConfirm);

        assertFalse(result);
    }


}
