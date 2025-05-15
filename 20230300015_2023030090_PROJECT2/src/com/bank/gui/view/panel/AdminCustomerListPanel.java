package com.bank.gui.view.panel;

import com.bank.manager.AccountManager;
import com.bank.manager.UserManager;
import com.bank.model.accounts.Account;
import com.bank.model.users.Individual;
import com.bank.model.users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AdminCustomerListPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final UserManager userManager = new UserManager();
    private final AccountManager accountManager = new AccountManager(userManager);

    public AdminCustomerListPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("👥 Λίστα Πελατών", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[] { "Ονοματεπώνυμο", "Username", "Λογαριασμοί" }, 0);
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("🔄 Ανανέωση");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        refreshBtn.setBackground(new Color(0, 102, 180));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> loadCustomers());

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);

        loadCustomers();
    }

    private void loadCustomers() {
        tableModel.setRowCount(0);
        List<User> users = userManager.getAllUsers().stream()
            .filter(u -> u instanceof Individual)
            .collect(Collectors.toList());

        for (User user : users) {
            long count = accountManager.getAllAccounts().stream()
                .filter(acc -> acc.getOwner().equals(user))
                .count();
            tableModel.addRow(new Object[] {
                user.getFullName(), user.getUsername(), count
            });
        }
    }
}
