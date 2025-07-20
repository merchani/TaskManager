import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class LoginPageController {
    private LoginPage loginPage;
    private Users users;

    public LoginPageController(LoginPage loginPage){
        this.loginPage = loginPage;
        users = Users.getInstance();
        SQLHandler.getInstance().listAllTables();
        SQLHandler.getInstance().loadUsersFromDatabase();
        


        loginPage.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkUsernamePassword();
            }
        });

        loginPage.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToRegistration();
            }
        });

        loginPage.getPasswordVisibilityButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility();
            }});
        }

    private void checkUsernamePassword(){
        String username = loginPage.getUsername();
        String password = loginPage.getPassword();

        if (users.checkUsername(username)) {
            if (users.checkPassword(username, password)) {
                loginPage.showMessage("You're signed in " + username + "!", "Success");
                new DatabasePage(loginPage.getLoginFrame());
            } else {
                loginPage.showMessage("Password is incorrect", "Login Failed");
            }
        } else {
            loginPage.showMessage("Username not found", "Login Failed");
        }
    }

    private void togglePasswordVisibility() {
        boolean show = loginPage.getPasswordVisibilityButton().isSelected();
        char echoChar = show ? (char) 0 : '\u2022'; // Bullet char
        loginPage.getPasswordTextBox().setEchoChar(echoChar);
        loginPage.getPasswordVisibilityButton().setText(show ? "🔒" : "👁");
    }
    

    private void goToRegistration() {
        new RegistrationPage(); // Reopen login page
        loginPage.getLoginFrame().dispose(); // Close registration fram
    }


    private void setPasswordTextBoxResponse(String response){
        JLabel passwordResponseLabel =  new JLabel(response);
    }

}
