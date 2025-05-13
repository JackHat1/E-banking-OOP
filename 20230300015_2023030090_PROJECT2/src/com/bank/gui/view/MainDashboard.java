package com.bank.gui.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import com.bank.manager.*;
import com.bank.model.accounts.*;
import com.bank.model.transactions.Deposit;
import com.bank.model.users.*;

public class MainDashboard extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainDashboard(User user, AccountManager accountManager) {
        setTitle("Bank Of TUC");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Header =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setPreferredSize(new Dimension(1000, 80));

        ImageIcon rawLogo = new ImageIcon("./data/logo/tuc.png");
        Image scaledImage = rawLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledLogo);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("BANK OF TUC", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // ===== Menu Buttons =====
        JPanel menuPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        menuPanel.setBackground(new Color(240, 240, 240));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] menuItems = {
            "Αρχική", "Λογαριασμοί", "Κατάθεση", "Ανάληψη",
            "Μεταφορά", "Πληρωμή RF", "Κινήσεις", "Αποσύνδεση"
        };

        Map<String, JPanel> pages = new LinkedHashMap<>();
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btn.setPreferredSize(new Dimension(160, 35));
            btn.addActionListener(e -> cardLayout.show(contentPanel, item));
            menuPanel.add(btn);

            JPanel panel;
            if (item.equals("Λογαριασμοί")) {
                panel = createAccountsPanel(user, accountManager);
            } else if (item.equals("Κατάθεση")) {
                panel = createDepositPanel(user, accountManager); // 👇 Επόμενο βήμα
            } else {
                panel = new JPanel();
                panel.setBackground(Color.WHITE);
                panel.add(new JLabel("📄 Ενότητα: " + item));
            }

            pages.put(item, panel);
        }

        // ===== Add Pages to contentPanel =====
        for (Map.Entry<String, JPanel> entry : pages.entrySet()) {
            contentPanel.add(entry.getValue(), entry.getKey());
        }

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, "Αρχική");

        setVisible(true);
    }

    private JPanel createAccountsPanel(User user, AccountManager accountManager) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("📄 Οι Λογαριασμοί μου", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setEditable(false);

        StringBuilder sb = new StringBuilder();
        accountManager.getAllAccounts().stream()
            .filter(acc -> acc.getOwner().equals(user))
            .forEach(acc -> sb.append("IBAN: ").append(acc.getIban())
                              .append("\nΥπόλοιπο: ").append(String.format("%.2f €", acc.getBalance()))
                              .append("\n\n"));

        area.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDepositPanel(User user, AccountManager accountManager) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
    
        JLabel title = new JLabel("💳 Κατάθεση Χρημάτων", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);
    
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Λίστα λογαριασμών
        List<Account> myAccounts = accountManager.getAllAccounts().stream()
            .filter(acc -> acc.getOwner().equals(user))
            .toList();
    
        JComboBox<String> accountBox = new JComboBox<>();
        for (Account acc : myAccounts) accountBox.addItem(acc.getIban());
    
        JTextField amountField = new JTextField(12);
        JButton depositBtn = new JButton("💰 Εκτέλεση Κατάθεσης");
    
        depositBtn.setBackground(new Color(0, 102, 204));
        depositBtn.setForeground(Color.WHITE);
        depositBtn.setFocusPainted(false);
    
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Λογαριασμός:"), gbc);
        gbc.gridx = 1;
        form.add(accountBox, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Ποσό (€):"), gbc);
        gbc.gridx = 1;
        form.add(amountField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        form.add(depositBtn, gbc);
    
        panel.add(form, BorderLayout.CENTER);
    
        depositBtn.addActionListener(e -> {
            String selectedIban = (String) accountBox.getSelectedItem();
            Account selected = accountManager.findByIban(selectedIban);
    
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) throw new NumberFormatException();
    
                TransactionManager tm = new TransactionManager();
                Deposit dep = new Deposit(selected, amount, user, "Κατάθεση μέσω GUI");
                tm.execute(dep);
    
                JOptionPane.showMessageDialog(panel, "✅ Κατατέθηκαν " + amount + "€ στον " + selected.getIban());
                amountField.setText("");
    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "❌ Μη έγκυρο ποσό.");
            }
        });
    
        return panel;
    }
    

}
