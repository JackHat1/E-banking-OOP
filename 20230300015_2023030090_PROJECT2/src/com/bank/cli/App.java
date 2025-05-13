package com.bank.cli;

import com.bank.manager.UserManager;
import com.bank.manager.AccountManager;
import com.bank.model.users.*;
import com.bank.model.accounts.*;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

public class App {

    private UserManager userManager = new UserManager();
    private AccountManager accountManager = new AccountManager(userManager);
    private Scanner scanner = new Scanner(System.in);
    private User loggedInUser;
    private LocalDate currentDate = LocalDate.of(2025, 5, 1); // αρχική ημερομηνία συστήματος


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
    
    private void withdrawMenu() {
        System.out.println("📤 Λογαριασμοί σου για ανάληψη:");
    
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
    
        System.out.print("💶 Ποσό ανάληψης: ");
        double amount = Double.parseDouble(scanner.nextLine());
    
        if (amount <= 0) {
            System.out.println("❌ Μη έγκυρο ποσό.");
            return;
        }
    
        if (acc.getBalance() < amount) {
            System.out.println("❌ Δεν υπάρχει αρκετό υπόλοιπο.");
            return;
        }
    
        acc.withdraw(amount);
        System.out.println("✅ Ανάληψη " + amount + "€ από τον " + acc.getIban());
    }
    
    private void payBillMenu() {
        System.out.println("📨 Πληρωμή Λογαριασμού");
    
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
            System.out.println("❌ Δεν έχετε διαθέσιμους λογαριασμούς.");
            return;
        }
    
        System.out.print("👉 Επιλογή λογαριασμού: ");
        int accChoice = Integer.parseInt(scanner.nextLine());
        if (accChoice < 1 || accChoice > myAccounts.size()) {
            System.out.println("❌ Μη έγκυρη επιλογή.");
            return;
        }
    
        Account from = myAccounts.get(accChoice - 1);
    
        System.out.print("🔢 RF κωδικός (τυχαίος): ");
        String rfCode = scanner.nextLine();
    
        System.out.print("💶 Ποσό πληρωμής: ");
        double amount = Double.parseDouble(scanner.nextLine());
    
        if (amount <= 0) {
            System.out.println("❌ Μη έγκυρο ποσό.");
            return;
        }
    
        if (from.getBalance() < amount) {
            System.out.println("❌ Ανεπαρκές υπόλοιπο.");
            return;
        }
    
        from.withdraw(amount);
        System.out.println("✅ Πληρώθηκε RF " + rfCode + " από τον " + from.getIban());
    }
    
    
    private void showStatementMenu() {
        System.out.println("📄 Προβολή Κινήσεων: (θα υλοποιηθεί σύντομα)");
    }
    

    private void menu() {
        while (true) {
            System.out.println("\n🔽 Επιλέξτε λειτουργία:");
            System.out.println("1. Προβολή Λογαριασμών");
            System.out.println("2. Κατάθεση");
            System.out.println("3. Ανάληψη");
            System.out.println("4. Μεταφορά");
            System.out.println("5. Πληρωμή Λογαριασμού");
            System.out.println("6. Προβολή Κινήσεων (🔜)");
            System.out.println("0. Έξοδος");
    
            String option = scanner.nextLine();
            System.out.println("7. Προώθηση Ημέρας (Simulate Time Passing)");

            switch (option) {
                case "1":
                    showAccounts();
                    break;
                case "2":
                    depositMenu();
                    break;
                case "3":
                    withdrawMenu();
                    break;
                case "4":
                    transferMenu();
                    break;
                case "5":
                    payBillMenu();
                    break;
                case "6":
                    showStatementMenu();
                    break;
                 case "7":
                    simulateNextDay();
                    break;
                
                case "0":
                    System.out.println("📦 Αποθήκευση...");
                    userManager.saveAll();
                    accountManager.saveAll();
                    System.out.println("✅ Έγινε αποθήκευση. Αντίο!");
                    return;
                default:
                    System.out.println("❌ Άκυρη επιλογή.");


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
        private void simulateNextDay() {
        currentDate = currentDate.plusDays(1);
        System.out.println("📅 Προχωρήσαμε στην ημερομηνία: " + currentDate);

        String filename = "./data/bills/" + currentDate + ".csv";
        File file = new File(filename);
        if (file.exists()) {
            System.out.println("📥 Φόρτωση νέων λογαριασμών από: " + filename);
            billManager.loadDailyBills(filename); // Εδώ θα φορτώσεις τα bills
        } else {
            System.out.println("ℹ️ Δεν υπάρχουν λογαριασμοί για την ημερομηνία.");
        }

        // TODO: Εκτέλεση standing orders (όταν υλοποιηθούν)
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
