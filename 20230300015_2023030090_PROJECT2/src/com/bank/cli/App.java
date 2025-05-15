package com.bank.cli;

import com.bank.manager.*;
import com.bank.model.users.*;

import java.time.LocalDate;
import java.util.Scanner;

public class App {

    private final UserManager userManager = new UserManager();
    private final TransactionManager transactionManager = new TransactionManager();
    private final AccountManager accountManager = new AccountManager(userManager);
    private final BillManager billManager = new BillManager();
    private final Scanner scanner = new Scanner(System.in);
    private User loggedInUser;

    public void run() {
        userManager.load();
        accountManager.load();

        login();

        if (loggedInUser instanceof Individual) {
            new IndividualMenu(loggedInUser, accountManager, transactionManager, billManager, new StatementManager(), scanner).run();
        } else if (loggedInUser instanceof Company) {
            new CompanyMenu(loggedInUser, accountManager, billManager, scanner).run();
        } else if (loggedInUser instanceof Admin) {
            new AdminMenu(userManager, accountManager, scanner).run();
        } else {
            System.out.println("❌ Ο τύπος χρήστη δεν υποστηρίζεται.");
        }

        saveAndExit();
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
            System.out.println("❌ Λάθος στοιχεία.");
            System.exit(0);
        }
    }

    private void saveAndExit() {
        System.out.println("📦 Αποθήκευση...");
        userManager.saveAll();
        accountManager.saveAll();
        System.out.println("✅ Έγινε αποθήκευση. Αντίο!");
    }
}
