package com.bank.model.transactions;

import com.bank.model.accounts.Account;
import com.bank.model.users.User;

public class Withdrawal extends Transaction {
    private Account account;
    private double amount;

    public Withdrawal(Account account, double amount, User transactor, String reason) {
        super(transactor, reason);
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        if (account.getBalance() >= amount) {
            account.withdraw(amount);
            System.out.println("🏧 Ανάληψη " + amount + "€ από λογαριασμό " + account.getIban());
        } else {
            System.out.println(" Ανεπαρκές υπόλοιπο για ανάληψη.");
        }
    }
}
