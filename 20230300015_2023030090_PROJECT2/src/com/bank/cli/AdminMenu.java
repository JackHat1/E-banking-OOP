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
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Users");
            System.out.println("2. View Accounts");
            System.out.println("0. Exit");

            String option = scanner.nextLine();
            switch (option) {
                case "1": showUsers(); break;
                case "2": showAccounts(); break;
                case "0": return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void showUsers() {
        List<User> users = userManager.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            System.out.println("- " + u.getUsername() + " (" + u.getFullName() + ")");
        }
    }

    private void showAccounts() {
        List<Account> accounts = accountManager.getAllAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            System.out.println("- " + a.getIban() + " | Owner: " + a.getOwner().getFullName());
        }
    }
}