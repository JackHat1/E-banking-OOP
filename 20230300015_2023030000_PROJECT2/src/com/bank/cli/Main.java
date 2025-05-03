package com.bank.cli;

import com.bank.manager.UserManager;
import com.bank.manager.AccountManager;
import com.bank.model.users.User;
import com.bank.model.accounts.Account;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        // ✅ Έλεγχος ύπαρξης αρχείων
        File checkUsers = new File("./data/users/users.csv");
        System.out.println("✅ Βρέθηκε users.csv: " + checkUsers.exists());
        System.out.println("📂 Path: " + checkUsers.getAbsolutePath());

        File checkAccounts = new File("./data/accounts/accounts.csv");
        System.out.println("✅ Βρέθηκε accounts.csv: " + checkAccounts.exists());
        System.out.println("📂 Path: " + checkAccounts.getAbsolutePath());

        // 🔁 Managers
        UserManager userManager = new UserManager();
        AccountManager accountManager = new AccountManager(userManager);

        // 📥 Load από CSV
        userManager.load();
        accountManager.load();

        // 🧑‍💻 Χρήστες
        System.out.println("\n🔐 Χρήστες:");
        for (User u : userManager.getAllUsers()) {
            System.out.println(" - " + u.getRole() + ": " + u.getUsername() + " | " + u.getFullName());
        }

        // 💰 Λογαριασμοί
        System.out.println("\n🏦 Λογαριασμοί:");
        for (Account a : accountManager.getAllAccounts()) {
            System.out.println(" - " + a.getIban() + " | " + a.getOwner().getFullName() + " | " + a.getBalance() + " €");
        }
    }
}
