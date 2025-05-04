package com.bank.cli;

import com.bank.manager.UserManager;
import com.bank.manager.AccountManager;
import com.bank.model.users.*;
import com.bank.model.accounts.*;

import java.util.*;

public class App {

    private UserManager userManager = new UserManager();
    private AccountManager accountManager = new AccountManager(userManager);
    private Scanner scanner = new Scanner(System.in);
    private User loggedInUser;

    public void run() {
        userManager.load();
        accountManager.load();

        login();

        if (loggedInUser instanceof Customer) {
            menu();
        } else {
            System.out.println("Μόνο πελάτες μπορούν να χρησιμοποιήσουν το σύστημα.");
        }
    }

    private void login() {
        System.out.println("Σύνδεση Χρήστη");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userManager.findByUsername(username);
        if (user != null && user.checkPassword(password)) {
            loggedInUser = user;
            System.out.println("Καλωσήρθες " + user.getFullName());
        } else {
            System.out.println("Λάθος στοιχεία.");
            System.exit(0);
        }
    }

    private void menu() {
        while (true) {
            System.out.println("\n1. Προβολή λογαριασμών");
            System.out.println("2. Κατάθεση");
            System.out.println("3. Μεταφορά");
            System.out.println("4. Έξοδος");
            System.out.print("Επιλογή: ");
            String option = scanner.nextLine();

            if (option.equals("1")) {
                showAccounts();
            } else if (option.equals("2")) {
                depositMenu();
            } else if (option.equals("3")) {
                transferMenu();
            } else if (option.equals("4")) {
                System.out.println("📦 Αποθήκευση...");
                userManager.save();
                accountManager.save();
                System.out.println("✅ Έγινε αποθήκευση. Αντίο!");
                break;
            } else {
                System.out.println("Άκυρη επιλογή.");
            }
        }
    }

    private void showAccounts() {
        for (Account acc : accountManager.getAllAccounts()) {
            if (acc.getOwner().equals(loggedInUser)) {
                System.out.println("- IBAN: " + acc.getIban() + " | Υπόλοιπο: " + acc.getBalance() + " €");
            }
        }
    }

    private void depositMenu() {
        System.out.println("💳 Λογαριασμοί σου για κατάθεση:");

        List<Account> myAccounts = new ArrayList<>();
        int index = 1;
        for (Account acc : accountManager.getAllAccounts()) {
            if (acc.getOwner().equals(loggedInUser)) {
                myAccounts.add(acc);
                System.out.println(index + ". " + acc.getIban() + " | Υπόλοιπο: " + acc.getBalance() + " €");
                index++;
            }
        }

        if (myAccounts.isEmpty()) {
            System.out.println("❌ Δεν έχεις λογαριασμούς.");
            return;
        }

        System.out.print("👉 Διάλεξε αριθμό: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice < 1 || choice > myAccounts.size()) {
            System.out.println("❌ Μη έγκυρη επιλογή.");
            return;
        }

        Account acc = myAccounts.get(choice - 1);

        System.out.print("💶 Ποσό κατάθεσης: ");
        double amount = Double.parseDouble(scanner.nextLine());

        acc.deposit(amount);
        System.out.println("✅ Κατατέθηκαν " + amount + "€ στον " + acc.getIban());
    }

    private void transferMenu() {
        System.out.println("🔁 Λογαριασμοί σου για αποστολή:");

        List<Account> myAccounts = new ArrayList<>();
        int index = 1;
        for (Account acc : accountManager.getAllAccounts()) {
            if (acc.getOwner().equals(loggedInUser)) {
                myAccounts.add(acc);
                System.out.println(index + ". " + acc.getIban() + " | Υπόλοιπο: " + acc.getBalance() + " €");
                index++;
            }
        }

        if (myAccounts.isEmpty()) {
            System.out.println("❌ Δεν έχεις λογαριασμούς.");
            return;
        }

        System.out.print("👉 Διάλεξε αριθμό λογαριασμού για αποστολή: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice < 1 || choice > myAccounts.size()) {
            System.out.println("❌ Μη έγκυρη επιλογή.");
            return;
        }

        Account fromAccount = myAccounts.get(choice - 1);

        System.out.print("📨 Σε ποιον IBAN θες να στείλεις: ");
        String toIban = scanner.nextLine();
        Account toAccount = accountManager.findByIban(toIban);

        if (toAccount == null) {
            System.out.println("❌ Ο παραλήπτης δεν βρέθηκε.");
            return;
        }

        if (fromAccount.getIban().equals(toIban)) {
            System.out.println("⚠️ Δεν μπορείς να μεταφέρεις στον ίδιο λογαριασμό.");
            return;
        }

        System.out.print("💸 Ποσό μεταφοράς: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (fromAccount.getBalance() < amount) {
            System.out.println("❌ Δεν έχεις αρκετό υπόλοιπο.");
            return;
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        System.out.println("✅ Μεταφέρθηκαν " + amount + "€ από " + fromAccount.getIban() + " σε " + toIban);
    }
}
