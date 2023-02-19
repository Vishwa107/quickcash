package com.group13project;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginPageActivityTest {
    static LoginPageActivity loginPageActivity;

    @BeforeClass
    public static void setup() {
        loginPageActivity = new LoginPageActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testValidInput() {
        String email = "test@example.com";
        String password = "password123";
        boolean result = loginPageActivity.validateInputLogin(email, password);
        assertTrue(result);
    }

    @Test
    public void testInvalidEmail() {
        String email = "invalid_email";
        String password = "password123";
        boolean result = loginPageActivity.validateInputLogin(email, password);
        assertFalse(result);
    }

    @Test
    public void testEmptyEmail() {
        String email = "";
        String password = "password123";
        boolean result = loginPageActivity.validateInputLogin(email, password);
        assertFalse(result);
    }

    @Test
    public void testEmptyPassword() {
        String email = "test@example.com";
        String password = "";
        boolean result = loginPageActivity.validateInputLogin(email, password);
        assertFalse(result);
    }

    @Test
    public void testShortPassword() {
        String email = "test@example.com";
        String password = "pass";
        boolean result = loginPageActivity.validateInputLogin(email, password);
        assertFalse(result);
    }
}
