package com.bank.cli;

import com.bank.manager.*;
import com.bank.model.users.User;
import com.bank.model.accounts.Account;

import java.util.*;

public class CompanyMenu {
    private final User user;
    private final AccountManager accountManager;
    private final BillManager billManager;
    private final Scanner scanner;

    public CompanyMenu(User user, AccountManager accountManager,BillManager billManager, Scanner scanner) {
        this.user = user;
        this.accountManager = accountManager;
        this.billManager = billManager;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("\nCompany Menu:");
            System.out.println("1. View Accounts");
            System.out.println("2. Load Issued Bills");
            System.out.println("3. View Paid Bills");
            System.out.println("0. Exit");

            String option = scanner.nextLine();
            switch (option) {
                case "1": showAccounts(); break;
                case "2": loadIssued(); break;
                case "3": showPaid(); break;
                case "0": return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void showAccounts() {
        List<Account> accounts = accountManager.getAllAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getOwner().equals(user)) {
                System.out.println("- IBAN: " + acc.getIban() + " | Balance: " + acc.getBalance());
            }
        }
    }

    private void loadIssued() {
        System.out.println("[Not implemented] Load issued bills.");
    }

    private void showPaid() {
        System.out.println("[Not implemented] View paid bills.");
    }
}