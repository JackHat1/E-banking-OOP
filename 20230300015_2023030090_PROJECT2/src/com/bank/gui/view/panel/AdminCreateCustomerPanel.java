package com.bank.gui.view.panel;

import com.bank.manager.AccountManager;
import com.bank.manager.UserManager;
import com.bank.model.accounts.Account;
import com.bank.model.accounts.PersonalAccount;
import com.bank.model.users.Individual;

import javax.swing.*;
import java.awt.*;

public class AdminCreateCustomerPanel extends JPanel {

    private final UserManager userManager = new UserManager();
    private final AccountManager accountManager = new AccountManager(userManager);

    public AdminCreateCustomerPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("➕ Δημιουργία Νέου Πελάτη", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField fullNameField = new JTextField(20);
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField vatField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Ονοματεπώνυμο:"), gbc);
        gbc.gridx = 1;
        form.add(fullNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        form.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        form.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("ΑΦΜ:"), gbc);
        gbc.gridx = 1;
        form.add(vatField, gbc);

        JButton createBtn = new JButton("✅ Δημιουργία Πελάτη");
        createBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        createBtn.setBackground(new Color(0, 140, 60));
        createBtn.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        form.add(createBtn, gbc);

        add(form, BorderLayout.CENTER);

        createBtn.addActionListener(e -> {
            String fullName = fullNameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String vat = vatField.getText().trim();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || vat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Συμπλήρωσε όλα τα πεδία.");
                return;
            }

            if (userManager.findByUsername(username) != null) {
                JOptionPane.showMessageDialog(this, "⚠️ Το username υπάρχει ήδη.");
                return;
            }

            Individual newCustomer = new Individual(username, password, fullName, vat);
            userManager.addUser(newCustomer);
            Account account = new PersonalAccount(newCustomer, 0.01);
            accountManager.addAccount(account);

            accountManager.saveAll();

            JOptionPane.showMessageDialog(this, "✅ Ο πελάτης δημιουργήθηκε με IBAN: " + account.getIban());
            fullNameField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            vatField.setText("");
        });
    }
}
