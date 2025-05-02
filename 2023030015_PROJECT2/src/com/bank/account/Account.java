package com.account;

import com.storage.Storable;

public abstract class Account implements Storable {
    protected String iban;
    protected String ownerId;
    protected double balance;
    protected boolean active;

    public Account(String iban, String ownerId) {
        this.iban = iban;
        this.ownerId = ownerId;
        this.balance = 0.0;
        this.active = true;
    }

    public void deposit(double amount) {
        if (active && amount > 0) {
            balance += amount;
        } else {
            System.out.println("Deposit failed: account inactive or invalid amount.");
        }
    }

    public void withdraw(double amount) {
        if (active && amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Withdraw failed: check balance or account status.");
        }
    }

    public void transferTo(Account destination, double amount) {
        if (this.active && destination.active && amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            destination.balance += amount;
        } else {
            System.out.println("Transfer failed: check accounts or balance.");
        }
    }

    public String getIban() {
        return iban;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String toString() {
        return "IBAN: " + iban + ", Owner: " + ownerId + ", Balance: " + balance + ", Active: " + active;
    }

    public String toCSV() {
        return iban + "," + ownerId + "," + balance + "," + active;
    }
}
