package com.bank.gui.view;

import javax.swing.*;

import com.bank.manager.AccountManager;
import com.bank.model.users.User;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainDashboard extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainDashboard(User user, AccountManager accountManager) {
        
        setTitle("Bank Of TUC");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Top Header with logo and title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setPreferredSize(new Dimension(900, 80));

        // Smaller logo
        ImageIcon rawLogo = new ImageIcon("./data/logo/tuc.png");
        Image scaledImage = rawLogo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledLogo);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Title in ALL CAPS
        JLabel titleLabel = new JLabel("BANK OF TUC", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);


        add(headerPanel, BorderLayout.NORTH);

        // Menu buttons bar
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        menuPanel.setBackground(new Color(230, 230, 230));
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
            btn.addActionListener(e -> cardLayout.show(contentPanel, item));
            menuPanel.add(btn);

            // Dummy panels for now
            JPanel panel = new JPanel();
            panel.add(new JLabel("📄 Ενότητα: " + item));
            pages.put(item, panel);
        }

        add(menuPanel, BorderLayout.CENTER);

        for (Map.Entry<String, JPanel> entry : pages.entrySet()) {
            contentPanel.add(entry.getValue(), entry.getKey());
        }

        add(contentPanel, BorderLayout.SOUTH);
        cardLayout.show(contentPanel, "Αρχική");

        setVisible(true);
    }
}
