package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.LoginPage;
import main.LoginPageController;
import main.Users;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginPageControllerTest {

    private LoginPage mockLoginPage;
    private Users mockUsers;

    @BeforeEach
    void setUp() {
        mockLoginPage = mock(LoginPage.class);
        mockUsers = mock(Users.class);

        when(mockLoginPage.getUsername()).thenReturn("testUser");
        when(mockLoginPage.getPassword()).thenReturn("testPass");

        // Inject mocks into the controller
        new LoginPageController(mockLoginPage);
        Users.setInstance(mockUsers); // assuming you can override the singleton for testing
    }

    @Test
    void testSuccessfulLogin() {
        when(mockUsers.checkUsername("testUser")).thenReturn(true);
        when(mockUsers.checkPassword("testUser", "testPass")).thenReturn(true);

        // Simulate login button click
        mockLoginPage.getLoginButton().doClick();

        verify(mockLoginPage).showMessage("You're signed in testUser!", "Success");
    }

    @Test
    void testIncorrectPassword() {
        when(mockUsers.checkUsername("testUser")).thenReturn(true);
        when(mockUsers.checkPassword("testUser", "testPass")).thenReturn(false);

        mockLoginPage.getLoginButton().doClick();

        verify(mockLoginPage).showMessage("Password is incorrect", "Login Failed");
    }

    @Test
    void testUnknownUsername() {
        when(mockUsers.checkUsername("testUser")).thenReturn(false);

        mockLoginPage.getLoginButton().doClick();

        verify(mockLoginPage).showMessage("Username not found", "Login Failed");
    }
}

