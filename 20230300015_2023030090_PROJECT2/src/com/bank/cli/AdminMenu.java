package com.bank.cli;

import com.bank.manager.*;
import com.bank.model.accounts.Account;
import com.bank.model.users.User;

import java.util.*;

public class AdminMenu {
    private final UserManager userManager;
    private final AccountManager accountManager;
    private final Scanner scanner;

    public AdminMenu(UserManager userManager, AccountManager accountManager, Scanner scanner) {
        this.userManager = userManager;
        this.accountManager = accountManager;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("\n⚙️ Διαχειριστικό Μενού:");
            System.out.println("1. Προβολή Πελατών");
            System.out.println("2. Προβολή Λογαριασμών");
            System.out.println("0. Έξοδος");

            String option = scanner.nextLine();
            switch (option) {
                case "1": showUsers(); break;
                case "2": showAccounts(); break;
                case "0": return;
                default: System.out.println("❌ Άκυρη επιλογή.");
            }
        }
    }

    private void showUsers() {
        for (User u : userManager.getAllUsers()) {
            System.out.println("- " + u.getUsername() + " (" + u.getFullName() + ")");
        }
    }

    private void showAccounts() {
        for (Account a : accountManager.getAllAccounts()) {
            System.out.println("- " + a.getIban() + " | Κάτοχος: " + a.getOwner().getFullName());
        }
    }
}
