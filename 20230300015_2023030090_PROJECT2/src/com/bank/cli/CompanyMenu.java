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

    public CompanyMenu(User user, AccountManager accountManager,
                       BillManager billManager, Scanner scanner) {
        this.user = user;
        this.accountManager = accountManager;
        this.billManager = billManager;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("\n🏢 Επιχειρηματικό Μενού:");
            System.out.println("1. Προβολή Λογαριασμών");
            System.out.println("2. Φόρτωση Εκδομένων Λογαριασμών");
            System.out.println("3. Προβολή Πληρωμένων Λογαριασμών");
            System.out.println("0. Έξοδος");

            String option = scanner.nextLine();
            switch (option) {
                case "1": showAccounts(); break;
                case "2": loadIssued(); break;
                case "3": showPaid(); break;
                case "0": return;
                default: System.out.println("❌ Άκυρη επιλογή.");
            }
        }
    }

    private void showAccounts() {
        for (Account acc : accountManager.getAllAccounts()) {
            if (acc.getOwner().equals(user)) {
                System.out.println("- IBAN: " + acc.getIban() + " | Υπόλοιπο: " + acc.getBalance());
            }
        }
    }

    private void loadIssued() {
        System.out.println("📥 [Υπό υλοποίηση] Φόρτωση εκδομένων bills.");
    }

    private void showPaid() {
        System.out.println("📜 [Υπό υλοποίηση] Προβολή πληρωμένων bills.");
    }
}
