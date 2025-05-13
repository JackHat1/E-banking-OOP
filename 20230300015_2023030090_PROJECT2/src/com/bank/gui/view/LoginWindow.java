package com.bank.gui.view;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginWindow() {
        setTitle("E-Banking Login");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Σύνδεση");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);

        setVisible(true);

        loginButton.addActionListener(e -> {
            if (loginListener != null) {
                loginListener.onLogin(
                    usernameField.getText(),
                    new String(passwordField.getPassword())
                );
            }
        });

        

        
    }

    public interface LoginListener {
        void onLogin(String username, String password);
    }
    
    private LoginListener loginListener;
    
    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Σφάλμα", JOptionPane.ERROR_MESSAGE);
    }
    
    
}
