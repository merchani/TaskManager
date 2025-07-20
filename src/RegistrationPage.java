
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationPage {
    private JTextField forenameTextBox;
    private JTextField surnameTextBox;
    private JTextField emailTextBox;
    private JTextField usernameTextBox;
    private JPasswordField passwordTextBox;
    private JPasswordField confirmPasswordTextBox;
    private JToggleButton passwordVisibilityButton;
    private JToggleButton confirmPasswordVisibilityButton;


    private JButton registerButton;
    private JButton cancelButton;
    private JFrame frame;


    public RegistrationPage(){
        Font labelFont = new Font("Serif bold", Font.BOLD, 16);
        Font inputFont = new Font("Serif bold", Font.PLAIN, 14);
        
        Color textColor = new Color(33, 33, 33);


        frame = new JFrame();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Planner: Registration");
        frame.setLocationRelativeTo(null);

        // Input Labels
        JLabel forenameLabel = new JLabel("Please enter your First Name");
        JLabel surnameLabel = new JLabel("Please enter your Surname");
        JLabel emailLabel = new JLabel("Please enter your Email");
        JLabel usernameLabel = new JLabel("Please enter your Username");
        JLabel passwordLabel = new JLabel("Please enter your Password");
        JLabel confirmPasswordLabel = new JLabel("Please re-enter your Password");
        forenameLabel.setFont(labelFont);
        surnameLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        confirmPasswordLabel.setFont(labelFont);
        forenameLabel.setForeground(textColor);
        surnameLabel.setForeground(textColor);
        emailLabel.setForeground(textColor);
        usernameLabel.setForeground(textColor);
        passwordLabel.setForeground(textColor);
        confirmPasswordLabel.setForeground(textColor);


        // Input Textboxes and give color
        forenameTextBox =  new JTextField();
        forenameTextBox.setBackground(new Color(238, 238, 238));
        forenameTextBox.setFont(inputFont);
        surnameTextBox =  new JTextField();
        surnameTextBox.setBackground(new Color(238, 238, 238));
        surnameTextBox.setFont(inputFont);
        emailTextBox = new JTextField();
        emailTextBox.setBackground(new Color(238, 238, 238));
        emailTextBox.setFont(inputFont);
        usernameTextBox =  new JTextField();
        usernameTextBox.setBackground(new Color(238, 238, 238));
        usernameTextBox.setFont(inputFont);
        passwordTextBox =  new JPasswordField();
        passwordTextBox.setBackground(new Color(238, 238, 238));
        passwordTextBox.setFont(inputFont);
        confirmPasswordTextBox = new JPasswordField();
        confirmPasswordTextBox.setBackground(new Color(238, 238, 238));
        confirmPasswordTextBox.setFont(inputFont);

        // Input password toggles
        passwordVisibilityButton = new JToggleButton("👁");
        confirmPasswordVisibilityButton = new JToggleButton("👁");


        // Input Panels
        JPanel forenamePanel = new JPanel(new GridLayout(3, 1));
        JPanel surnamePanel = new JPanel(new GridLayout(3, 1));
        JPanel emailPanel = new JPanel(new GridLayout(3, 1));
        JPanel usernamePanel = new JPanel(new GridLayout(3, 1));
        JPanel passwordPanel = new JPanel(new GridLayout(3, 1));
        JPanel confirmPasswordPanel = new JPanel(new GridLayout(3, 1));

        // Add components to Panels
        forenamePanel.add(forenameLabel);
        forenamePanel.add(forenameTextBox);
        surnamePanel.add(surnameLabel);
        surnamePanel.add(surnameTextBox);
        emailPanel.add(emailLabel);
        emailPanel.add(emailTextBox);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextBox);
        

        // add components to password panel
        JPanel passJPanel = new JPanel();
        passJPanel.add(passwordTextBox);
        passJPanel.add(passwordVisibilityButton);
        passJPanel.setLayout(new BoxLayout(passJPanel, BoxLayout.X_AXIS));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passJPanel);

        // add components to confirmPassword panel
        JPanel confirmPassJPanel = new JPanel();
        confirmPassJPanel.add(confirmPasswordTextBox);
        confirmPassJPanel.add(confirmPasswordVisibilityButton);
        confirmPassJPanel.setLayout(new BoxLayout(confirmPassJPanel, BoxLayout.X_AXIS));
        confirmPasswordPanel.add(confirmPasswordLabel);
        confirmPasswordPanel.add(confirmPassJPanel);

        // Buttons and adding components to the Button Panel
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(cancelButton);
        buttonPanel.add(registerButton);

        // Container and adding components to the Container
        JPanel panelContainer = new JPanel(new GridLayout(7, 0, 0, 10));
        panelContainer.setBorder(new EmptyBorder(50,0,0,0));
        panelContainer.add(forenamePanel);
        panelContainer.add(surnamePanel);
        panelContainer.add(emailPanel);
        panelContainer.add(usernamePanel);
        panelContainer.add(passwordPanel);
        panelContainer.add(confirmPasswordPanel);
        panelContainer.add(buttonPanel);

        frame.setLayout(new FlowLayout());
        frame.add(panelContainer);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        frame.setVisible(true);
        
        new RegistrationController(this);
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getRegisterButton(){
        return registerButton;
    }

    public JFrame getRegistrationFrame(){
        return frame;
    }

    public String getForename(){
        return forenameTextBox.getText();
    }

    public String getSurname(){
        return surnameTextBox.getText();
    }

    public String getEmail(){
        return emailTextBox.getText();
    }

    public String getUsername(){
        return usernameTextBox.getText();
    }

    public String getPassword() {
        return new String(passwordTextBox.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordTextBox.getPassword());
    }
    
    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public JPasswordField getPasswordTextBox() {
        return passwordTextBox;
    }

    public JPasswordField getConfirmPasswordTextBox() {
        return confirmPasswordTextBox;
    }

    public JToggleButton getPasswordVisibilityButton() {
        return passwordVisibilityButton;
    }

    public JToggleButton getConfirmPasswordVisibilityButton() {
        return confirmPasswordVisibilityButton;
    }

    
}
