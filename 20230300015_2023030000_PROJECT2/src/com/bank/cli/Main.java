package com.bank.cli;

import com.bank.model.users.*;
import com.bank.model.accounts.*;
import com.bank.model.transactions.*;
import com.bank.manager.TransactionManager;

public class Main {
    public static void main(String[] args) {
        // Δημιουργία Χρηστών
        Individual giannis = new Individual("user1", "1234", "Γιάννης Παπαδόπουλος", "123456789");
        Company vodafone = new Company("vodafone", "pass123", "Vodafone Ελλάς", "987654321");
        Admin admin = new Admin("admin", "admin", "Διαχειριστής");

        // Λογαριασμοί
        PersonalAccount acc1 = new PersonalAccount(giannis, 1.2);
        BusinessAccount acc2 = new BusinessAccount(vodafone, 1.5, 5.0);

        // Συναλλαγές
        TransactionManager txManager = TransactionManager.getInstance();

        Deposit deposit1 = new Deposit(acc1, 500.0, giannis, "Κατάθεση αρχικού ποσού");
        txManager.executeTransaction(deposit1);

        Withdrawal withdrawal1 = new Withdrawal(acc1, 200.0, giannis, "Ανάληψη για ψώνια");
        txManager.executeTransaction(withdrawal1);

        Withdrawal withdrawal2 = new Withdrawal(acc1, 400.0, giannis, "Ανάληψη υπερβάλλουσα"); // Θα αποτύχει
        txManager.executeTransaction(withdrawal2);

        // Εκτυπώσεις
        System.out.println("\n🏁 Τελικό Υπόλοιπο:");
        System.out.println("Λογαριασμός " + acc1.getIban() + " => " + acc1.getBalance() + " €");

        // Προβολή ιστορικού συναλλαγών
        System.out.println("\n📜 Ιστορικό Συναλλαγών:");
        for (Transaction tx : txManager.getHistory()) {
            System.out.println(tx.getTimestamp() + " | " + tx.getClass().getSimpleName() + " | " + tx.getReason());
        }

        // Δοκιμή μεταφοράς
        PersonalAccount acc3 = new PersonalAccount(giannis, 1.2);
        Deposit preload = new Deposit(acc3, 200.0, giannis, "Προετοιμασία μεταφοράς");
        txManager.executeTransaction(preload);

        Transfer transfer = new Transfer(acc3, acc1, 150.0, giannis, "Μεταφορά σε άλλο δικό μου λογαριασμό", "Λήψη από λογαριασμό savings");
        txManager.executeTransaction(transfer);

        // Δοκιμή πληρωμής
        Payment payment = new Payment(acc1, acc2, 100.0, "RF123456789", giannis, "Πληρωμή Vodafone Λογαριασμού");
        txManager.executeTransaction(payment);

    }
}
