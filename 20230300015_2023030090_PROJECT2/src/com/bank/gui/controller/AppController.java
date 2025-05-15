package com.bank.gui.controller;

import com.bank.manager.*;
import com.bank.model.users.*;
import com.bank.gui.view.*;

import javax.swing.*;

public class AppController {
    private UserManager userManager = new UserManager();
    private AccountManager accountManager;

    public AppController(LoginWindow loginWindow) {
        try {
            userManager.load(); // ✅ Έλεγχος users.csv
            accountManager = new AccountManager(userManager);
            accountManager.load(); // ✅ Έλεγχος accounts.csv

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "⚠️ Σφάλμα κατά την ανάγνωση αρχείων δεδομένων (users/accounts).\n\n" +
                "Βεβαιωθείτε ότι υπάρχουν τα αρχεία στη διαδρομή /data/",
                "Αρχείο δεν βρέθηκε", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        loginWindow.setLoginListener((username, password) -> {
            User user = userManager.findByUsername(username);
            if (user != null && user.checkPassword(password)) {
                System.out.println("▶ Login επιτυχής: " + user.getUsername());
                loginWindow.dispose();
                showDashboard(user);
            } else {
                JOptionPane.showMessageDialog(null, "❌ Λάθος στοιχεία σύνδεσης.", "Αποτυχία", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void showDashboard(User user) {
        System.out.println(">>> Συνδέθηκε: " + user.getClass().getSimpleName());
        new MainDashboard(user, accountManager);
    }

    
}
