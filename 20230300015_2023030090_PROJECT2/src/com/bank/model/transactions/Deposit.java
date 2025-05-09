package com.bank.model.transactions;

import com.bank.model.users.User;
import com.bank.model.accounts.Account;

public class Deposit extends Transaction {
    private Account account;
    private double amount;

    public Deposit(Account account, double amount, User transactor, String reason) {
        super(transactor, "Deposit");
        this.account = account;
        this.amount = amount;
    }


    @Override
    public void execute() {
        account.deposit(amount);
        System.out.println("Deposit of " + amount + "€ in " + account.getIban());
    }
}
