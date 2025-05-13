// New structure for cleaner separation

// === FILE: MainDashboard.java ===
package com.bank.gui.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import com.bank.gui.view.panel.*;
import com.bank.manager.*;
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

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setPreferredSize(new Dimension(1000, 80));

        ImageIcon rawLogo = new ImageIcon("./data/logo/tuc.png");
        Image scaledImage = rawLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("BANK OF TUC", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Menu
        JPanel menuPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        menuPanel.setBackground(new Color(240, 240, 240));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] menuItems = {
            "Αρχική", "Λογαριασμοί", "Κατάθεση", "Ανάληψη",
            "Μεταφορά", "Πληρωμή RF", "Κινήσεις", "Αποσύνδεση"
        };

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        Map<String, JPanel> pages = new LinkedHashMap<>();
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btn.setPreferredSize(new Dimension(160, 35));
            btn.addActionListener(e -> cardLayout.show(contentPanel, item));
            menuPanel.add(btn);

            JPanel panel;
            switch (item) {
                case "Λογαριασμοί":
                    panel = new AccountsPanel(user, accountManager); break;
                case "Κατάθεση":
                    panel = new DepositPanel(user, accountManager); break;
                case "Ανάληψη":
                    panel = new WithdrawPanel(user, accountManager); break;
                default:
                    panel = new JPanel();
                    panel.setBackground(Color.WHITE);
                    panel.add(new JLabel("📄 Ενότητα: " + item)); break;
            }

            contentPanel.add(panel, item);
        }

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, "Αρχική");
        setVisible(true);
    }
}
