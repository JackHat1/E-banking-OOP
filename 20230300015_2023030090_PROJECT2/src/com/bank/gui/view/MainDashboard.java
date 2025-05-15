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

    public MainDashboard(User user, AccountManager accountManager, UserManager userManager) {
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

        // === Menu items ανάλογα με user type ===
        String[] menuItems;

        if (user instanceof Individual) {
            menuItems = new String[] {
                "Αρχική", "Λογαριασμοί", "Κατάθεση", "Ανάληψη",
                "Μεταφορά", "Πληρωμή RF", "Κινήσεις", "Προώθηση Ημέρας", "Αποσύνδεση"
            };
        } else if (user instanceof Company) {
            menuItems = new String[] {
                "Αρχική", "Εισερχόμενες Πληρωμές", "Αποσύνδεση"
            };
        } else if (user instanceof Admin) {
            menuItems = new String[] {
                "Αρχική", "Όλοι οι Πελάτες", "Δημιουργία Πελάτη", "Εισαγωγή RF", "Αποσύνδεση"
            };
        } else {
            menuItems = new String[] { "Αρχική", "Αποσύνδεση" };
        }

        // === Layout setup ===
        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        menuPanel.setBackground(new Color(245, 245, 245));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));

            JPanel panel;

            switch (item) {
                case "Λογαριασμοί":
                    panel = new AccountsPanel(user, accountManager); break;
                case "Κατάθεση":
                    panel = new DepositPanel(user, accountManager); break;
                case "Ανάληψη":
                    panel = new WithdrawPanel(user, accountManager); break;
                case "Μεταφορά":
                    panel = new TransferPanel(user, accountManager); break;
                case "Πληρωμή RF":
                    panel = new PayBillPanel(user, accountManager, userManager);  break;
                case "Κινήσεις":
                    panel = new StatementPanel(user, accountManager); break;
                case "Προώθηση Ημέρας":
                    panel = new SimulateDatePanel(); break;
                case "Εισερχόμενες Πληρωμές":
                    panel = new CompanyPaymentsPanel(user, accountManager); break;
                case "Όλοι οι Πελάτες":
                    panel = new AdminCustomerListPanel(userManager); break;
                case "Δημιουργία Πελάτη":
                    panel = new AdminCreateCustomerPanel(); break;
                case "Εισαγωγή RF":
                    panel = new AdminImportRFPanel(); break;
                case "Αποσύνδεση":
                    btn.addActionListener(e -> {
                        dispose();
                        new LoginWindow();
                    });
                    panel = new JPanel(); break;
                default:
                    panel = new JPanel();
                    panel.setBackground(Color.WHITE);
                    panel.add(new JLabel("📄 Ενότητα: " + item)); break;
            }

            btn.addActionListener(e -> cardLayout.show(contentPanel, item));
            menuPanel.add(btn);
            contentPanel.add(panel, item);
        }

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, "Αρχική");
        setVisible(true);
    }
}
