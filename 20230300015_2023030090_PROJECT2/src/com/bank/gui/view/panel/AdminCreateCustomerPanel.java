package com.bank.gui.view.panel;

import com.bank.manager.UserManager;
import com.bank.model.users.*;

import javax.swing.*;
import java.awt.*;

public class AdminCreateCustomerPanel extends JPanel {

    public AdminCreateCustomerPanel(UserManager userManager) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Create Customer", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] roles = {"Individual", "Company"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        JTextField fullNameField = new JTextField(15);
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JTextField vatField = new JTextField(15);
        JButton createBtn = new JButton("Create");
        createBtn.setBackground(new Color(0, 153, 76));
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusPainted(false);

        // === Layout ===
        gbc.gridx = 0; gbc.gridy = 0; form.add(new JLabel("User Type:"), gbc);
        gbc.gridx = 1; form.add(roleBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; form.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; form.add(fullNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; form.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; form.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; form.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; form.add(new JLabel("VAT:"), gbc);
        gbc.gridx = 1; form.add(vatField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        form.add(createBtn, gbc);

        add(form, BorderLayout.CENTER);

        // === Action ===
        createBtn.addActionListener(e -> {
            String role = (String) roleBox.getSelectedItem();
            String fullName = fullNameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String vat = vatField.getText().trim();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || vat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            if (userManager.findByUsername(username) != null) {
                JOptionPane.showMessageDialog(this, "Username already exists.");
                return;
            }

            User newUser;
            if (role.equals("Individual")) {
                newUser = new Individual(username, password, fullName, vat);
            } else {
                newUser = new Company(username, password, fullName, vat);
            }

            userManager.addUser(newUser);
            userManager.saveAll();

            JOptionPane.showMessageDialog(this, "User created successfully!");
            fullNameField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            vatField.setText("");
        });
    }
}