package com.bank.gui.view.panel;

import com.bank.manager.UserManager;
import com.bank.model.users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminCustomerListPanel extends JPanel {

    private final UserManager userManager;
    private final DefaultTableModel tableModel;

    public AdminCustomerListPanel(UserManager userManager) {
        this.userManager = userManager;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("👥 Λίστα Πελατών", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[] { "Username", "Ονοματεπώνυμο", "Ρόλος" }, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("🔄 Ανανέωση");
        refreshBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        refreshBtn.setBackground(new Color(0, 102, 204));
        refreshBtn.setForeground(Color.WHITE);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadUsers());

        // First load
        loadUsers();
    }

    private void loadUsers() {
        tableModel.setRowCount(0); // καθάρισε
        for (User u : userManager.getAllUsers()) {
            tableModel.addRow(new Object[] {
                u.getUsername(),
                u.getFullName(),
                u.getClass().getSimpleName()
            });
        }
    }
}
