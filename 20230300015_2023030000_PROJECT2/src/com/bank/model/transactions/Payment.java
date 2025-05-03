package com.bank.model.transactions;

import com.bank.model.accounts.Account;
import com.bank.model.users.User;

public class Payment extends Transaction {
    private Account from;
    private Account business;
    private double amount;
    private String paymentCode; // RF
    private String billReason;

    public Payment(Account from, Account business, double amount, String rfCode, User transactor, String billReason) {
        super(transactor, "Payment");
        this.from = from;
        this.business = business;
        this.amount = amount;
        this.paymentCode = rfCode;
        this.billReason = billReason;
    }

    @Override
    public void execute() {
        if (from.getBalance() >= amount) {
            from.withdraw(amount);
            business.deposit(amount);
            System.out.println(" Πληρωμή " + amount + "€ στον επαγγελματικό λογαριασμό " + business.getIban() + " [RF: " + paymentCode + "]");
        } else {
            System.out.println(" Ανεπαρκές υπόλοιπο για πληρωμή.");
        }
    }
}
