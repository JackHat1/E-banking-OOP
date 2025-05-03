package com.bank.model.accounts;

import com.bank.model.users.Customer;
import com.bank.storage.Storable;

public abstract class Account implements Storable {
    protected String iban;
    protected Customer owner;
    protected double balance;
    protected double interestRate;
    //dhusdhusd

    public Account(Customer owner, double interestRate) {
        this.iban = IBANGenerator.generate(this.getAccountTypeCode());
        this.owner = owner;
        this.interestRate = interestRate;
        this.balance = 0.0;
    }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public Customer getOwner() { return owner; }
    public double getBalance() { return balance; }
    public double getInterestRate() { return interestRate; }

    public void deposit(double amount) { balance += amount; }
    public void withdraw(double amount) { balance -= amount; }

    public abstract String getAccountTypeCode(); // π.χ. "100" ή "200"

    @Override
    public String marshal() {
        return getAccountTypeCode() + "," + iban + "," + owner.getVat() + "," + balance + "," + interestRate;
    }

    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
        this.iban = parts[1];
        this.balance = Double.parseDouble(parts[3]);
        this.interestRate = Double.parseDouble(parts[4]);
    }
}
