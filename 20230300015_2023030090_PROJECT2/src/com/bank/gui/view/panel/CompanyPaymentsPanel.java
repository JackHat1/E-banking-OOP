package com.bank.gui.view.panel;

import com.bank.manager.AccountManager;
import com.bank.model.accounts.Account;
import com.bank.model.transactions.Payment;
import com.bank.model.transactions.Transaction;
import com.bank.model.users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyPaymentsPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel model;

    public CompanyPaymentsPanel(User user, AccountManager accountManager) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("💼 Εισερχόμενες Πληρωμές", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[] {
            "RF", "Ποσό (€)", "Αποστολέας", "Ημερομηνία"
        }, 0);
        table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("🔄 Ανανέωση");
        refreshBtn.setBackground(new Color(0, 102, 180));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadPayments(user, accountManager));
        loadPayments(user, accountManager);
    }

    private void loadPayments(User user, AccountManager accountManager) {
        model.setRowCount(0);

        // TODO: Αναμονή υλοποίησης πραγματικής λογικής συναλλαγών

        model.addRow(new Object[] {
            "demoRF12345", "99.99", "Demo User", "2025-05-15"
        });
    }


}
