package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.LoginPage;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTest {

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage();
    }

    @Test
    void testUsernameFieldIsAccessible() {
        loginPage.getLoginButton().doClick(); // simulate click if needed
        loginPage.getUsernameTextBox().setText("testUser");
        assertEquals("testUser", loginPage.getUsername());
    }

    @Test
    void testPasswordFieldIsAccessible() {
        loginPage.getPasswordTextBox().setText("secret");
        assertEquals("secret", loginPage.getPassword());
    }

    @Test
    void testLoginButtonExists() {
        assertNotNull(loginPage.getLoginButton());
        assertEquals("Login", loginPage.getLoginButton().getText());
    }

    @Test
    void testRegisterButtonExists() {
        assertNotNull(loginPage.getRegisterButton());
        assertEquals("Register Here", loginPage.getRegisterButton().getText());
    }

    @Test
    void testPasswordVisibilityButtonExists() {
        assertNotNull(loginPage.getPasswordVisibilityButton());
    }
}

