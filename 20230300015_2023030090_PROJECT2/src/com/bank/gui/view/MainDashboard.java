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
        // Initialize managers needed for panels
        BillManager billManager = new BillManager(accountManager, userManager);
        TransactionManager transactionManager = new TransactionManager();

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
        // ...existing code...
        
        String[] menuItems;
        
        if (user instanceof Individual) {
            menuItems = new String[] {
                "Accounts",      // View accounts and balances
                "Deposit",       // Deposit to own account
                "Withdraw",      // Withdraw from own account
                "Transfer",      // Transfer to another IBAN
                "Pay Bill",      // Pay active bill by RF
                "Statements",    // View account statements
                "Logout"         // Exit to login
            };
        } else if (user instanceof Company) {
            menuItems = new String[] {
                "Accounts",         // View company accounts and balances
                "Issue Bill",       // Create new bill (bill number, RF code, amount, customer VAT)
                "Issued Bills",     // All bills issued by the company
                "Paid Bills",       // Only bills that are already paid
                "Logout"            // Exit to login
            };
        } else if (user instanceof Admin) {
            menuItems = new String[] {
                "All Users",
                "Customer Details",
                "All Accounts",
                "Account Info",
                "Account Statements",
                "Issued Bills",
                "Paid Bills",
                "List Orders",
                "Admin Pay Bill",
                "Simulate Time Passing",
                "Logout"
            };

        } else {
            menuItems = new String[] { "Logout" };
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
            // === Individual / Company Shared ===
            case "Accounts":
                panel = new AccountsPanel(user, accountManager);
                break;
            case "Deposit":
                panel = new DepositPanel(user, accountManager);
                break;
            case "Withdraw":
                panel = new WithdrawPanel(user, accountManager);
                break;
            case "Transfer":
                panel = new TransferPanel(user, accountManager);
                break;
            case "Pay Bill":
                panel = new PayBillPanel(user, accountManager, billManager, transactionManager);
                break;
            case "Statements":
                panel = new StatementPanel(user, accountManager);
                break;

            // === Company Only ===
            case "Issue Bill":
                panel = new CompanyIssueBillPanel(user, accountManager, userManager, billManager);
                break;
            case "Issued Bills":
                panel = new CompanyIssuedBillsPanel(user, billManager);
                break;
            case "Paid Bills":
                panel = new CompanyPaidBillsPanel(user, billManager);
                break;

            // === Admin Only ===
            case "All Users":
                panel = new AdminCustomerListPanel(userManager);
                break;
            case "Customer Details":
                panel = new AdminCustomerDetailsPanel(userManager, accountManager);
                break;
            case "All Accounts":
                panel = new AdminAccountListPanel(accountManager);
                break;
            case "Account Info":
                panel = new AdminAccountDetailsPanel(accountManager);
                break;
            case "Account Statements":
                panel = new AdminAccountStatementsPanel(accountManager);
                break;
            case "List Orders":
                panel = new AdminStandingOrdersPanel();
                break;
            case "Admin Pay Bill":
                panel = new AdminPayBillPanel(accountManager, userManager);
                break;
            case "Simulate Time Passing":
                panel = new AdminSimulateTimePanel(accountManager, userManager);
                break;
            case "Create User":
                panel = new AdminCreateCustomerPanel(userManager);
                break;
            case "Create Account":
                panel = new AdminCreateAccountPanel(accountManager, userManager);
                break;

            // === Common ===
            case "Logout":
                btn.addActionListener(e -> {
                    getContentPane().removeAll();
                    repaint();
                    revalidate();
                    dispose();
                });
                panel = new JPanel();
                break;

            default:
                panel = new JPanel();
                panel.setBackground(Color.WHITE);
                panel.add(new JLabel("Section: " + item));
                break;
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
