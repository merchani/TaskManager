import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPage {
    public Users users;
    private JTextField usernameTextBox;
    private JPasswordField passwordTextBox;
    private JButton registerButton;
    private JButton loginButton;
    private JToggleButton passwordVisibilityButton;

    JFrame frame;
    JPanel usernameTextPanel;
    JPanel passwordTextPanel;
    JPanel userPassButtContainer;
    JPanel titleContainer;



    public LoginPage(){
        Font labelFont = new Font("Serif bold", Font.BOLD, 16);
        Font inputFont = new Font("Serif bold", Font.PLAIN, 14);
        Font titleFont = new Font("Serif bold", Font.BOLD, 40);


        
        // Create the main frame
        frame = new JFrame("Planner: Login");
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Create a panel with a button
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        loginButton.setFont(labelFont);
        buttonPanel.add(loginButton);

        // Create a panel with a button
        registerButton = new JButton("Register Here");
        registerButton.setFont(labelFont);
        buttonPanel.add(registerButton);

        //create title
        JLabel titleLabel = new JLabel("The Planner");
        titleLabel.setFont(titleFont);
        titleContainer = new JPanel();
        titleContainer.add(titleLabel);

        // Create text field for username
        JLabel usernameLabel =  new JLabel("Please enter your username");
        usernameLabel.setFont(labelFont);
        usernameTextBox = new JTextField(1);
        usernameTextBox.setBackground(new Color(238, 238, 238));
        usernameTextBox.setFont(inputFont);
        usernameTextPanel = new JPanel();
        usernameTextPanel.add(usernameLabel);
        usernameTextPanel.add(usernameTextBox);
        usernameTextPanel.setLayout(new GridLayout(2,1));


        // Create text field for password
        JLabel passwordLabel =  new JLabel("Please enter your password");
        passwordLabel.setFont(labelFont);
        passwordTextBox = new JPasswordField(15);
        passwordTextBox.setBackground(new Color(238, 238, 238));
        passwordTextBox.setFont(inputFont);
        passwordVisibilityButton = new JToggleButton("👁");
        passwordVisibilityButton.setFont(labelFont);
        passwordTextPanel = new JPanel();
        passwordTextPanel.add(passwordLabel);
        JPanel passJPanel = new JPanel();
        passJPanel.add(passwordTextBox);
        passJPanel.add(passwordVisibilityButton);
        passJPanel.setLayout(new BoxLayout(passJPanel, BoxLayout.X_AXIS));
        passwordTextPanel.add(passJPanel);
        passwordTextPanel.setLayout(new GridLayout(2,1));

        userPassButtContainer = new JPanel();
        userPassButtContainer.add(usernameTextPanel);
        userPassButtContainer.add(passwordTextPanel);
        userPassButtContainer.add(buttonPanel);
        userPassButtContainer.setLayout(new  GridLayout(3,1,0,30));
        userPassButtContainer.setBounds(0,0,0,0);

        
        frame.setLayout(new FlowLayout());
        JPanel gridPanel = new JPanel(new GridLayout(5,1));
        JPanel divPanel =  new JPanel();
        JPanel divPanel2 =  new JPanel();
        JPanel divPanel3 =  new JPanel();

        gridPanel.add(titleContainer);
        gridPanel.add(userPassButtContainer);
        gridPanel.add(divPanel2);
        gridPanel.add(divPanel3);
        frame.add(gridPanel);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true); 

        new LoginPageController(this);

    }


    public String getUsername(){
        return usernameTextBox.getText();
    }

    public JFrame getLoginFrame(){
        return frame;
    }

    public String getPassword(){
        return new String (passwordTextBox.getPassword());
    }

    public JPasswordField getPasswordTextBox(){
        return passwordTextBox;
    }

    public JButton getLoginButton(){
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JToggleButton getPasswordVisibilityButton() {
        return passwordVisibilityButton;
    }


    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

}
