package com.bank.cli;

import com.bank.manager.*;
import com.bank.model.users.User;
import com.bank.model.accounts.Account;
import com.bank.model.transactions.*;
import com.bank.model.bills.Bill;
import com.bank.model.statements.StatementEntry;

import java.util.*;

public class IndividualMenu {
    private final User user;
    private final AccountManager accountManager;
    private final TransactionManager transactionManager;
    private final BillManager billManager;
    private final StatementManager statementManager;
    private final Scanner scanner;

    public IndividualMenu(User user, AccountManager accountManager,
                          TransactionManager transactionManager,
                          BillManager billManager,
                          StatementManager statementManager,
                          Scanner scanner) {
        this.user = user;
        this.accountManager = accountManager;
        this.transactionManager = transactionManager;
        this.billManager = billManager;
        this.statementManager = statementManager;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("\n🔽 Επιλέξτε λειτουργία:");
            System.out.println("1. Προβολή Λογαριασμών");
            System.out.println("2. Κατάθεση");
            System.out.println("3. Ανάληψη");
            System.out.println("4. Μεταφορά");
            System.out.println("5. Πληρωμή Λογαριασμού");
            System.out.println("6. Προβολή Κινήσεων");
            System.out.println("0. Έξοδος");

            String option = scanner.nextLine();

            switch (option) {
                case "1": showAccounts(); break;
                case "2": deposit(); break;
                case "3": withdraw(); break;
                case "4": transfer(); break;
                case "5": payBill(); break;
                case "6": showStatements(); break;
                case "0": return;
                default: System.out.println("❌ Άκυρη επιλογή.");
            }
        }
    }

    private List<Account> getUserAccounts() {
        List<Account> list = new ArrayList<>();
        for (Account acc : accountManager.getAllAccounts()) {
            if (acc.getOwner().equals(user)) list.add(acc);
        }
        return list;
    }

    private Account selectAccount(List<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getIban());
        }
        System.out.print("👉 Επιλογή: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        return (idx >= 0 && idx < accounts.size()) ? accounts.get(idx) : null;
    }

    private void showAccounts() {
        getUserAccounts().forEach(acc -> System.out.println("- " + acc.getIban() + " | Υπόλοιπο: " + acc.getBalance()));
    }

    private void deposit() {
        List<Account> accounts = getUserAccounts();
        Account acc = selectAccount(accounts);
        if (acc == null) return;
        System.out.print("💶 Ποσό κατάθεσης: ");
        double amount = Double.parseDouble(scanner.nextLine());
        transactionManager.execute(new Deposit(acc, amount, user, "Κατάθεση μέσω CLI"));
    }

    private void withdraw() {
        List<Account> accounts = getUserAccounts();
        Account acc = selectAccount(accounts);
        if (acc == null) return;
        System.out.print("💶 Ποσό ανάληψης: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > acc.getBalance()) {
            System.out.println("❌ Ανεπαρκές υπόλοιπο.");
            return;
        }
        transactionManager.execute(new Withdrawal(acc, amount, user, "Ανάληψη μέσω CLI"));
    }

    private void transfer() {
        List<Account> myAccounts = getUserAccounts();
        Account from = selectAccount(myAccounts);
        if (from == null) return;
        System.out.print("📨 IBAN παραλήπτη: ");
        String toIban = scanner.nextLine();
        Account to = accountManager.findByIban(toIban);
        if (to == null || from.getIban().equals(toIban)) {
            System.out.println("❌ Άκυρος IBAN.");
            return;
        }
        System.out.print("💸 Ποσό: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("✍️ Αιτιολογία: ");
        String reason = scanner.nextLine();
        transactionManager.execute(new Transfer(from, to, amount, user, reason, reason));
    }

    private void payBill() {
        List<Account> accounts = getUserAccounts();
        Account from = selectAccount(accounts);
        if (from == null) return;
        System.out.print("🔢 RF: ");
        String rf = scanner.nextLine();
        System.out.print("💶 Ποσό: ");
        double amount = Double.parseDouble(scanner.nextLine());
        Bill bill = billManager.getBillByRF(rf);
        if (bill == null || bill.isPaid || from.getBalance() < amount) {
            System.out.println("❌ Μη έγκυρη πληρωμή.");
            return;
        }
        Account business = accountManager.getAllAccounts().stream()
                .filter(acc -> !acc.getOwner().equals(user)).findFirst().orElse(null);
        if (business == null) {
            System.out.println("❌ Δεν υπάρχει λογαριασμός επιχείρησης.");
            return;
        }
        transactionManager.execute(new Payment(bill, from, business, user));
    }

    private void showStatements() {
        List<Account> accounts = getUserAccounts();
        Account acc = selectAccount(accounts);
        if (acc == null) return;
        List<StatementEntry> entries = statementManager.load(acc);
        if (entries.isEmpty()) {
            System.out.println("ℹ️ Δεν υπάρχουν κινήσεις.");
            return;
        }
        entries.forEach(System.out::println);
    }
}
