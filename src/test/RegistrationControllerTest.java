package test;

import main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class RegistrationControllerTest {

    private RegistrationPage mockPage;
    private Users mockUsers;
    private SQLHandler mockSQL;

    private RegistrationController controller;

    @BeforeEach
    void setUp() {
        mockPage = mock(RegistrationPage.class);
        mockUsers = mock(Users.class);
        mockSQL = mock(SQLHandler.class);

        // Inject mocks into singletons
        Users.setInstance(mockUsers);
        SQLHandler.setInstance(mockSQL);

        controller = new RegistrationController(mockPage);
    }

    @Test
    void testEmptyFieldsValidation() {
        when(mockPage.getForename()).thenReturn("");
        when(mockPage.getSurname()).thenReturn("");
        when(mockPage.getEmail()).thenReturn("");
        when(mockPage.getUsername()).thenReturn("");
        when(mockPage.getPassword()).thenReturn("");
        when(mockPage.getConfirmPassword()).thenReturn("");

        controller.handleRegistration();

        verify(mockPage).showMessage("All fields are required.", "Validation Error");
    }

    @Test
    void testInvalidEmailValidation() {
        when(mockPage.getForename()).thenReturn("Ian");
        when(mockPage.getSurname()).thenReturn("Merchant");
        when(mockPage.getEmail()).thenReturn("invalid-email");
        when(mockPage.getUsername()).thenReturn("ianmerchant");
        when(mockPage.getPassword()).thenReturn("Aa1!aaaa");
        when(mockPage.getConfirmPassword()).thenReturn("Aa1!aaaa");

        controller.handleRegistration();

        verify(mockPage).showMessage("Invalid email format.", "Validation Error");
    }

    @Test
    void testPasswordMismatchValidation() {
        when(mockPage.getForename()).thenReturn("Ian");
        when(mockPage.getSurname()).thenReturn("Merchant");
        when(mockPage.getEmail()).thenReturn("ian@example.com");
        when(mockPage.getUsername()).thenReturn("ianmerchant");
        when(mockPage.getPassword()).thenReturn("Aa1!aaaa");
        when(mockPage.getConfirmPassword()).thenReturn("Aa1!bbbb");

        controller.handleRegistration();

        verify(mockPage).showMessage("Passwords do not match.", "Validation Error");
    }

    @Test
    void testUsernameAlreadyTaken() {
        when(mockPage.getForename()).thenReturn("Ian");
        when(mockPage.getSurname()).thenReturn("Merchant");
        when(mockPage.getEmail()).thenReturn("ian@example.com");
        when(mockPage.getUsername()).thenReturn("ianmerchant");
        when(mockPage.getPassword()).thenReturn("Aa1!aaaa");
        when(mockPage.getConfirmPassword()).thenReturn("Aa1!aaaa");

        when(mockUsers.checkUsername("ianmerchant")).thenReturn(true);

        controller.handleRegistration();

        verify(mockPage).showMessage("Username already taken.", "Validation Error");
    }

    @Test
    void testSuccessfulRegistration() {
        when(mockPage.getForename()).thenReturn("Ian");
        when(mockPage.getSurname()).thenReturn("Merchant");
        when(mockPage.getEmail()).thenReturn("ian@example.com");
        when(mockPage.getUsername()).thenReturn("ianmerchant");
        when(mockPage.getPassword()).thenReturn("Aa1!aaaa");
        when(mockPage.getConfirmPassword()).thenReturn("Aa1!aaaa");

        when(mockUsers.checkUsername("ianmerchant")).thenReturn(false);
        when(mockSQL.insertUser(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);

        controller.handleRegistration();

        verify(mockPage).showMessage("Registration successful! You will return to the login page.", "Success");
    }

    @Test
    void testDatabaseFailure() {
        when(mockPage.getForename()).thenReturn("Ian");
        when(mockPage.getSurname()).thenReturn("Merchant");
        when(mockPage.getEmail()).thenReturn("ian@example.com");
        when(mockPage.getUsername()).thenReturn("ianmerchant");
        when(mockPage.getPassword()).thenReturn("Aa1!aaaa");
        when(mockPage.getConfirmPassword()).thenReturn("Aa1!aaaa");

        when(mockUsers.checkUsername("ianmerchant")).thenReturn(false);
        when(mockSQL.insertUser(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(false);

        controller.handleRegistration();

        verify(mockPage).showMessage("Registration failed. Please try again.", "Database Error");
    }
}
