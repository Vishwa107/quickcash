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
    public void testValidateEmailAddress() {
        assertEquals("empty", loginPageActivity.validateEmailAddress(""));
        assertEquals("invalid", loginPageActivity.validateEmailAddress("invalid email"));
        assertEquals("invalid", loginPageActivity.validateEmailAddress("john.doe@"));
        assertEquals("valid", loginPageActivity.validateEmailAddress("john.doe@example.com"));
        assertEquals("valid", loginPageActivity.validateEmailAddress("jane.doe123@subdomain.example.co.uk"));
    }

    @Test
    public void testValidatePassword() {
        assertEquals("empty", loginPageActivity.validatePassword(""));
        assertEquals("short", loginPageActivity.validatePassword("pass"));
        assertEquals("valid", loginPageActivity.validatePassword("password"));
        assertEquals("valid", loginPageActivity.validatePassword("P@ssw0rd"));
    }
}
