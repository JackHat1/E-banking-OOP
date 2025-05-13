package com.bank.gui.view;

import javax.swing.*;
import java.awt.*;
import com.bank.manager.*;
import com.bank.model.accounts.*;
import com.bank.model.users.*;
import com.bank.model.transactions.*;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerDashboard extends JFrame {
    private User user;
    private AccountManager accountManager;
    private TransactionManager transactionManager = new TransactionManager();

    public CustomerDashboard(User user, AccountManager accountManager) {
        this.user = user;
        this.accountManager = accountManager;

        getContentPane().setBackground(new Color(30, 30, 30));
        setTitle("Πελάτης - E-Banking");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("👋 Καλωσήρθες, " + user.getFullName(), JLabel.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        add(welcomeLabel);

        addStyledButton("1️⃣ Προβολή Λογαριασμών", e -> showAccounts());
        addStyledButton("2️⃣ Κατάθεση", e -> showDepositDialog());
        addStyledButton("3️⃣ Ανάληψη", null);
        addStyledButton("4️⃣ Μεταφορά", null);
        addStyledButton("5️⃣ Πληρωμή RF", null);
        addStyledButton("6️⃣ Προβολή Κινήσεων", null);

        JButton logoutBtn = addStyledButton("🚪 Έξοδος", e -> {
            dispose();
            new LoginWindow();
        });

        setVisible(true);
    }

    private JButton addStyledButton(String title, java.awt.event.ActionListener action) {
        JButton btn = new JButton(title);
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        if (action != null) btn.addActionListener(action);
        add(btn);
        return btn;
    }

    private void showAccounts() {
        StringBuilder msg = new StringBuilder("📄 Λογαριασμοί:\n\n");
        accountManager.getAllAccounts().stream()
            .filter(acc -> acc.getOwner().equals(user))
            .forEach(acc -> msg.append("- ").append(acc.getIban())
                              .append(" | Υπόλοιπο: ").append(acc.getBalance()).append(" €\n"));

        JOptionPane.showMessageDialog(this, msg.toString(), "Οι λογαριασμοί μου", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showDepositDialog() {
        List<Account> myAccounts = accountManager.getAllAccounts().stream()
            .filter(acc -> acc.getOwner().equals(user))
            .collect(Collectors.toList());

        if (myAccounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Δεν έχεις λογαριασμούς.");
            return;
        }

        String[] ibanOptions = myAccounts.stream()
            .map(Account::getIban)
            .toArray(String[]::new);

        String selectedIban = (String) JOptionPane.showInputDialog(
            this,
            "📥 Διάλεξε IBAN για κατάθεση:",
            "Επιλογή Λογαριασμού",
            JOptionPane.QUESTION_MESSAGE,
            null,
            ibanOptions,
            ibanOptions[0]);

        if (selectedIban == null) return;

        Account selectedAccount = accountManager.findByIban(selectedIban);

        String amountStr = JOptionPane.showInputDialog(this, "💶 Ποσό κατάθεσης:");
        if (amountStr == null) return;

        try {
            double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "❌ Το ποσό πρέπει να είναι θετικό.");
                return;
            }

            Deposit deposit = new Deposit(selectedAccount, amount, user, "Κατάθεση μέσω GUI");
            transactionManager.execute(deposit);

            JOptionPane.showMessageDialog(this, "✅ Κατατέθηκαν " + amount + "€ στον " + selectedIban);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Μη έγκυρο ποσό.");
        }
    }
}
