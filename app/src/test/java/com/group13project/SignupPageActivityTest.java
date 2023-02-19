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
    public void testValidateFirstName() {
        assertTrue(signupPageActivity.validateFirstName(""));
        assertFalse(signupPageActivity.validateFirstName("John"));
    }

    @Test
    public void testValidateLastName() {
        assertTrue(signupPageActivity.validateLastName(""));
        assertFalse(signupPageActivity.validateLastName("Doe"));
    }

    @Test
    public void testValidateEmailAddress() {
        assertEquals("empty", signupPageActivity.validateEmailAddress(""));
        assertEquals("invalid", signupPageActivity.validateEmailAddress("invalid email"));
        assertEquals("invalid", signupPageActivity.validateEmailAddress("john.doe@"));
        assertEquals("valid", signupPageActivity.validateEmailAddress("john.doe@example.com"));
        assertEquals("valid", signupPageActivity.validateEmailAddress("jane.doe123@subdomain.example.co.uk"));
    }

    @Test
    public void testValidatePhoneNumber() {
        assertEquals("empty", signupPageActivity.validatePhoneNumber(""));
        assertEquals("invalid", signupPageActivity.validatePhoneNumber("invalid phone number"));
        assertEquals("valid", signupPageActivity.validatePhoneNumber("1234567890"));
    }

    @Test
    public void testValidatePassword() {
        assertEquals("empty", signupPageActivity.validatePassword(""));
        assertEquals("short", signupPageActivity.validatePassword("pass"));
        assertEquals("valid", signupPageActivity.validatePassword("password"));
        assertEquals("valid", signupPageActivity.validatePassword("P@ssw0rd"));
    }

    @Test
    public void testValidateConfirmPassword() {
        assertEquals("empty", signupPageActivity.validateConfirmPassword("password", ""));
        assertEquals("incorrect", signupPageActivity.validateConfirmPassword("password", "password2"));
        assertEquals("valid", signupPageActivity.validateConfirmPassword("password", "password"));
    }


}
