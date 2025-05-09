package com.bank.model.transactions;

import com.bank.model.accounts.Account;
import com.bank.model.users.User;

public class Transfer extends Transaction {
    private Account from;
    private Account to;
    private double amount;
    private String senderReason;
    private String receiverReason;

    public Transfer(Account from, Account to, double amount, User transactor, String senderReason, String receiverReason) {
        super(transactor, "Transfer");
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.senderReason = senderReason;
        this.receiverReason = receiverReason;
    }

    @Override
    public void execute() {
        if (from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);
            System.out.println("Tranfer of the amount  " + amount + "€ from " + from.getIban() + " to " + to.getIban());
            System.out.println("Sender Reason: "+ senderReason);
            System.out.println("Receiver reason: "+ receiverReason);
        } else {
            System.out.println("Unavailable tranfer due to isufficient balance");
        }
    }
}
