import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class RegistrationController {
    private RegistrationPage registrationPage;
    private Users users;


    public RegistrationController(RegistrationPage registrationPage){
        this.registrationPage = registrationPage;
        users = Users.getInstance();

        registrationPage.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });
        registrationPage.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToLogin();
            }
        });

        registrationPage.getPasswordVisibilityButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility();
            }
        });

        registrationPage.getConfirmPasswordVisibilityButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleConfirmPasswordVisibility();
            }
        });   

    }

    private void handleRegistration() {
        User user = new User();
        SQLHandler.getInstance().loadUsersFromDatabase();
        String forename = registrationPage.getForename();
        String surname = registrationPage.getSurname();
        String email = registrationPage.getEmail();
        String username = registrationPage.getUsername();
        String password = registrationPage.getPassword();
        String confirmPassword = registrationPage.getConfirmPassword();



        // Validation
        if (forename.isEmpty() || surname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            registrationPage.showMessage("All fields are required.", "Validation Error");

            return;
        }

        if (!isValidEmail(email)) {
            registrationPage.showMessage("Invalid email format.", "Validation Error");
            return;
        }

        if (!isStrongPassword(password)) {
            registrationPage.showMessage("Password must be at least 8 characters with a mix of upper, lower, number, and symbol.", "Validation Error");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            registrationPage.showMessage("Passwords do not match.", "Validation Error");
            return;
        }
        
        SQLHandler.getInstance().loadUsersFromDatabase(); // Refresh in-memory list
        if (users.checkUsername(username)) {
            registrationPage.showMessage("Username already taken.", "Validation Error");
            return;
        }
        user.setForename(forename);
        user.setSurname(surname);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(confirmPassword);


        // Register User
        users.addUser(user);
        boolean success = SQLHandler.getInstance().insertUser(user.getId(),forename,surname,email,username,password);
        if (success) {
            registrationPage.showMessage("Registration successful! You will return to the login page.", "Success");

            returnToLogin();
        } else {
            registrationPage.showMessage("Registration failed. Please try again.", "Database Error");
        }

    }

    private boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email);
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
               Pattern.compile("[A-Z]").matcher(password).find() &&
               Pattern.compile("[a-z]").matcher(password).find() &&
               Pattern.compile("[0-9]").matcher(password).find() &&
               Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
    }
    private void returnToLogin() {
        new LoginPage(); // Reopen login page
        registrationPage.getRegistrationFrame().dispose(); // Close registration fram
    }

    private void togglePasswordVisibility() {
        boolean show = registrationPage.getPasswordVisibilityButton().isSelected();
        char echoChar = show ? (char) 0 : '\u2022';
        registrationPage.getPasswordTextBox().setEchoChar(echoChar);
        registrationPage.getPasswordVisibilityButton().setText(show ? "🔒" : "👁");
    }

    private void toggleConfirmPasswordVisibility() {
        boolean show = registrationPage.getConfirmPasswordVisibilityButton().isSelected();
        char echoChar = show ? (char) 0 : '\u2022';
        registrationPage.getConfirmPasswordTextBox().setEchoChar(echoChar);
        registrationPage.getConfirmPasswordVisibilityButton().setText(show ? "🔒" : "👁");
    }




}
