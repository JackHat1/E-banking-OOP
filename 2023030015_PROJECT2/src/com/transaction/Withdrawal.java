package com.bank.transaction;

public class Withdrawal extends Transaction {
    public Withdrawal(String transactionId, double amount) {
        super(transactionId, amount);
    }

    @Override
    public void process() {
        System.out.println("Processing withdrawal of " + amount + "€ [ID: " + transactionId + "]");
        // Εδώ μπορεί να ελεγχθεί αν υπάρχει αρκετό υπόλοιπο
    }
}
