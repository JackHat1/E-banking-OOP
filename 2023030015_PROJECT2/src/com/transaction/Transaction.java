package com.bank.transaction;

public class Deposit extends Transaction {
    public Deposit(String transactionId, double amount) {
        super(transactionId, amount);
    }

    @Override
    public void process() {
        System.out.println("Processing deposit of " + amount + "€ [ID: " + transactionId + "]");
        // Εδώ μπορεί να ενημερωθεί το υπόλοιπο λογαριασμού
    }
}
