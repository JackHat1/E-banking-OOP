package com.bank.cli;

import com.bank.manager.*;
import com.bank.model.accounts.*;
import com.bank.model.orders.StandingOrder;
import com.bank.model.users.*;

import java.time.LocalDate;
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
            System.out.println("3. Create New User");
            System.out.println("4. Create New Account");
            System.out.println("5. List Standing Orders");
            System.out.println("6. Simulate Time Passing");
            System.out.println("0. Exit");

            System.out.print("Select option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1": showUsers(); break;
                case "2": showAccounts(); break;
                case "3": createUser(); break;
                case "4": createAccount(); break;
                case "5": listOrders(); break;
                case "6": simulateTime(); break;
                case "0": return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void showUsers() {
        List<User> users = userManager.getAllUsers();
        System.out.println("\nUsers:");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            System.out.print("- " + u.getUsername() + " (" + u.getFullName() + ") | Role: " + u.getRole());
            if (u instanceof Customer) {
                System.out.print(" | VAT: " + ((Customer) u).getVat());
            }
            System.out.println();
        }
    }

    private void showAccounts() {
        List<Account> accounts = accountManager.getAllAccounts();
        System.out.println("\nAccounts:");
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            System.out.println("- " + a.getIban() + " | Owner: " + a.getOwner().getFullName() + " | Balance: " + a.getBalance());
        }
    }

    private void createUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter role (Individual / Company / Admin): ");
        String role = scanner.nextLine().trim().toLowerCase();

        User newUser = null;

        if (role.equals("individual") || role.equals("company")) {
            System.out.print("Enter VAT number: ");
            String vat = scanner.nextLine();
            if (role.equals("individual")) {
                newUser = new Individual(username, password, fullName, vat);
            } else {
                newUser = new Company(username, password, fullName, vat);
            }
        } else if (role.equals("admin")) {
            newUser = new Admin(username, password, fullName);
        } else {
            System.out.println("Invalid role.");
            return;
        }

        userManager.addUser(newUser);
        System.out.println("User created successfully.");
    }

    private void createAccount() {
        System.out.print("Enter VAT of account owner: ");
        String vat = scanner.nextLine();
        Customer customer = userManager.findByVat(vat);

        if (customer == null) {
            System.out.println("Customer with VAT " + vat + " not found.");
            return;
        }

        System.out.print("Enter interest rate (e.g. 0.02): ");
        double rate;
        try {
            rate = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid rate.");
            return;
        }

        Account newAccount = null;

        if (customer instanceof Individual) {
            newAccount = new PersonalAccount((Individual) customer, rate);
        } else if (customer instanceof Company) {
            System.out.print("Enter monthly fee: ");
            double fee;
            try {
                fee = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid fee.");
                return;
            }
            newAccount = new BusinessAccount((Company) customer, rate, fee);
        }

        if (newAccount != null) {
            accountManager.addAccount(newAccount);
            System.out.println("Account created with IBAN: " + newAccount.getIban());
        }
    }

    private void listOrders() {
    //     // List<StandingOrder> orders = StandingOrderManager.loadFromFile("data/orders.dat");
    //     // if (orders.isEmpty()) {
    //     //     System.out.println("No standing orders found.");
    //     //     return;
    //     // }
    //     // for (StandingOrder o : orders) {
    //     //     System.out.println("- " + o.getTitle() + " | From: " + o.getStartingDate() + " to: " + o.getEndingDate());
    //     // }
    }

     private void simulateTime() {
    //     System.out.print("Target date (YYYY-MM-DD): ");
    //     String input = scanner.nextLine();
    //     LocalDate targetDate;
    //     try {
    //         targetDate = LocalDate.parse(input);
    //     } catch (Exception e) {
    //         System.out.println("Invalid date.");
    //         return;
    //     }

    //     LocalDate today = LocalDate.now();
    //     TransactionManager transMan = new TransactionManager();
    //     BillManager billMan = new BillManager(accountManager, userManager);

    //     // standingOrderManager.loadOrders();
    //     // List<StandingOrder> orders = standingOrderManager.getAllOrders();

    //     while (!today.isAfter(targetDate)) {
    //         for (StandingOrder order : orders) {
    //             if (order.getIsActive() && !order.isExpired()) {
    //                 User user = userManager.findByVat(order.description); // ή άλλο πεδίο αν χρειάζεται
    //                 order.execute(today, billMan, accountManager, transMan, user);
    //             }
    //         }

    //         // Apply interest and monthly fees
    //         for (Account acc : accountManager.getAllAccounts()) {
    //             if (today.getDayOfMonth() == 1) {
    //                 acc.deposit(acc.getBalance() * acc.getInterestRate());
    //             }
    //             if (acc instanceof BusinessAccount && today.getDayOfMonth() == 1) {
    //                 acc.withdraw(((BusinessAccount) acc).getMonthlyFee());
    //             }
    //         }

    //         today = today.plusDays(1);
    //     }

    //     System.out.println("Simulation completed.");
    }
}