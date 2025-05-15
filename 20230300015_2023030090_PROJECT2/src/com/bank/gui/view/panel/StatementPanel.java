package com.bank.gui.view.panel;

import com.bank.manager.AccountManager;
import com.bank.manager.StatementManager;
import com.bank.model.accounts.Account;
import com.bank.model.statements.StatementEntry;
import com.bank.model.users.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class StatementPanel extends JPanel {

    private final User user;
    private final AccountManager accountManager;
    private final StatementManager statementManager = new StatementManager();

    private JComboBox<String> accountBox;
    private JTextArea resultArea;

    public StatementPanel(User user, AccountManager accountManager) {
        this.user = user;
        this.accountManager = accountManager;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("📄 Προβολή Κινήσεων", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // --- Επιλογή λογαριασμού ---
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new FlowLayout());

        // Λήψη μόνο των λογαριασμών του χρήστη (όπως CLI)
        List<Account> myAccounts = accountManager.getAllAccounts().stream()
                .filter(acc -> acc.getOwner().equals(user))
                .collect(Collectors.toList());

        if (myAccounts.isEmpty()) {
            topPanel.add(new JLabel("❌ Δεν βρέθηκαν λογαριασμοί."));
            add(topPanel, BorderLayout.NORTH);
            return;
        }

        String[] ibans = myAccounts.stream().map(Account::getIban).toArray(String[]::new);
        accountBox = new JComboBox<>(ibans);

        JButton showBtn = new JButton("Προβολή");
        topPanel.add(new JLabel("Λογαριασμός: "));
        topPanel.add(accountBox);
        topPanel.add(showBtn);
        add(topPanel, BorderLayout.NORTH);

        // --- Περιοχή αποτελεσμάτων ---
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        showBtn.addActionListener(e -> showStatements(myAccounts));
    }

    private void showStatements(List<Account> myAccounts) {
        String selectedIban = (String) accountBox.getSelectedItem();
        Account account = myAccounts.stream()
                .filter(acc -> acc.getIban().equals(selectedIban))
                .findFirst().orElse(null);

        if (account == null) {
            resultArea.setText("❌ Δεν βρέθηκε ο λογαριασμός.");
            return;
        }

        List<StatementEntry> entries = statementManager.load(account);
        if (entries.isEmpty()) {
            resultArea.setText("ℹ️ Δεν υπάρχουν καταχωρημένες κινήσεις.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (StatementEntry entry : entries) {
            sb.append(entry.toString()).append("\n");
        }

        resultArea.setText(sb.toString());
    }
}
