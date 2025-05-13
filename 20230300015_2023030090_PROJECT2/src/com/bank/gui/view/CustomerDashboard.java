    package com.bank.gui.view;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionListener;

    import com.bank.manager.AccountManager;
    import com.bank.model.users.User;

    public class CustomerDashboard extends JFrame {
        private User user;

    private AccountManager accountManager;

    public CustomerDashboard(User user, AccountManager accountManager) {
        this.user = user;
        this.accountManager = accountManager;


        setTitle("Πελάτης - E-Banking");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("👋 Καλωσήρθες, " + user.getFullName(), JLabel.CENTER);
        add(welcomeLabel);

        JButton viewAccountsBtn = new JButton("1️⃣ Προβολή Λογαριασμών");
        viewAccountsBtn.addActionListener(e -> {
            StringBuilder msg = new StringBuilder("📄 Λογαριασμοί:\n\n");
            accountManager.getAllAccounts().stream()
                .filter(acc -> acc.getOwner().equals(user))
                .forEach(acc -> msg.append("- ").append(acc.getIban())
                                  .append(" | Υπόλοιπο: ").append(acc.getBalance()).append(" €\n"));
        
            JOptionPane.showMessageDialog(this, msg.toString(), "Οι λογαριασμοί μου", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton depositBtn = new JButton("2️⃣ Κατάθεση");
        JButton withdrawBtn = new JButton("3️⃣ Ανάληψη");
        JButton transferBtn = new JButton("4️⃣ Μεταφορά");
        JButton payRFBtn = new JButton("5️⃣ Πληρωμή RF");
        JButton viewStatementsBtn = new JButton("6️⃣ Προβολή Κινήσεων");
        JButton logoutBtn = new JButton("🚪 Έξοδος");

        add(viewAccountsBtn);
        add(depositBtn);
        add(withdrawBtn);
        add(transferBtn);
        add(payRFBtn);
        add(viewStatementsBtn);
        add(logoutBtn);

        // 🔧 Εδώ θα μπει AppController λογική ή listeners

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginWindow(); // Επανεκκίνηση login
        });

        setVisible(true);
    }
}
