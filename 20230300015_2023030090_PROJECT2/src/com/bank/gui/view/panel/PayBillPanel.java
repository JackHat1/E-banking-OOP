package com.bank.gui.view.panel;

import com.bank.manager.AccountManager;
import com.bank.manager.BillManager;
import com.bank.manager.TransactionManager;
import com.bank.model.accounts.Account;
import com.bank.model.bills.Bill;
import com.bank.model.transactions.Payment;
import com.bank.model.users.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PayBillPanel extends JPanel {

    public PayBillPanel(User user, AccountManager accountManager) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("✉️ Πληρωμή Λογαριασμού RF", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        List<Account> myAccounts = accountManager.getAllAccounts().stream()
            .filter(acc -> acc.getOwner().equals(user))
            .toList();

        JComboBox<String> accountBox = new JComboBox<>();
        for (Account acc : myAccounts) accountBox.addItem(acc.getIban());

        JTextField rfField = new JTextField(12);
        JTextField amountField = new JTextField(12);
        JLabel balanceLabel = new JLabel("Υπόλοιπο: - €");
        balanceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        balanceLabel.setForeground(Color.DARK_GRAY);

        JButton payBtn = new JButton("💳 Πληρωμή");
        payBtn.setBackground(new Color(30, 120, 180));
        payBtn.setForeground(Color.WHITE);
        payBtn.setFocusPainted(false);

        // === Ενημέρωση υπολοίπου ===
        accountBox.addActionListener(e -> {
            String iban = (String) accountBox.getSelectedItem();
            Account selected = accountManager.findByIban(iban);
            if (selected != null) {
                balanceLabel.setText("Υπόλοιπο: " + String.format("%.2f €", selected.getBalance()));
            }
        });
        if (!myAccounts.isEmpty()) {
            Account selected = accountManager.findByIban((String) accountBox.getSelectedItem());
            balanceLabel.setText("Υπόλοιπο: " + String.format("%.2f €", selected.getBalance()));
        }

        // === Προσθήκη στο Grid ===
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Από Λογαριασμό:"), gbc);
        gbc.gridx = 1;
        form.add(accountBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("RF Κωδικός:"), gbc);
        gbc.gridx = 1;
        form.add(rfField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Ποσό Πληρωμής (€):"), gbc);
        gbc.gridx = 1;
        form.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("Υπόλοιπο:"), gbc);
        gbc.gridx = 1;
        form.add(balanceLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        form.add(payBtn, gbc);

        add(form, BorderLayout.CENTER);

        // === Εκτέλεση Πληρωμής ===
        payBtn.addActionListener(e -> {
            String iban = (String) accountBox.getSelectedItem();
            Account from = accountManager.findByIban(iban);
            String rf = rfField.getText().trim();

            try {
                double amount = Double.parseDouble(amountField.getText().trim());

                if (amount <= 0 || rf.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "❌ Συμπλήρωσε σωστά όλα τα πεδία.");
                    return;
                }

                BillManager billManager = new BillManager();
                Bill bill = billManager.getBill(rf);

                if (bill == null) {
                    JOptionPane.showMessageDialog(this, "❌ Δεν βρέθηκε RF: " + rf);
                    return;
                }
                if (bill.isPaid) {
                    JOptionPane.showMessageDialog(this, "⚠️ Ο λογαριασμός έχει ήδη πληρωθεί.");
                    return;
                }
                if (from.getBalance() < amount) {
                    JOptionPane.showMessageDialog(this, "❌ Ανεπαρκές υπόλοιπο.");
                    return;
                }

                Account business = accountManager.getAllAccounts().stream()
                    .filter(acc -> !acc.getOwner().equals(user))
                    .findFirst().orElse(null);
                if (business == null) {
                    JOptionPane.showMessageDialog(this, "❌ Δεν υπάρχει διαθέσιμος λογαριασμός επιχείρησης.");
                    return;
                }

                TransactionManager tm = new TransactionManager();
                tm.execute(new Payment(bill, from, business, user));

                JOptionPane.showMessageDialog(this, "✅ Επιτυχής πληρωμή " + amount + "€ για RF: " + rf);
                rfField.setText("");
                amountField.setText("");
                balanceLabel.setText("Υπόλοιπο: " + String.format("%.2f €", from.getBalance()));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Μη έγκυρο ποσό.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Σφάλμα: " + ex.getMessage());
            }
        });
    }
}
