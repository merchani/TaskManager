package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.RegistrationPage;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationPageTest {

    private RegistrationPage registrationPage;

    @BeforeEach
    void setUp() {
        registrationPage = new RegistrationPage();
    }

    @Test
    void testTextFieldsReturnCorrectValues() {
        registrationPage.getPasswordTextBox().setText("pass123");
        registrationPage.getConfirmPasswordTextBox().setText("pass123");
        registrationPage.getUsernameTextBox().setText("ianmerchant");
        registrationPage.getEmailTextBox().setText("ian@example.com");

        assertEquals("pass123", registrationPage.getPassword());
        assertEquals("pass123", registrationPage.getConfirmPassword());
        assertEquals("ianmerchant", registrationPage.getUsername());
        assertEquals("ian@example.com", registrationPage.getEmail());
    }

    @Test
    void testButtonsExist() {
        assertNotNull(registrationPage.getRegisterButton());
        assertNotNull(registrationPage.getCancelButton());
    }

    @Test
    void testPasswordVisibilityButtonsExist() {
        assertNotNull(registrationPage.getPasswordVisibilityButton());
        assertNotNull(registrationPage.getConfirmPasswordVisibilityButton());
    }
}

