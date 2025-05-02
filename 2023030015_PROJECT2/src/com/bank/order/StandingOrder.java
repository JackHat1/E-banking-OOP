package com.bank.order;

import com.bank.account.Account;

import java.time.LocalDate;

public class StandingOrder {
    private Account sourceAccount;
    private Account destinationAccount;
    private double amount;
    private LocalDate startDate;
    private int intervalDays; // Π.χ. κάθε 30 μέρες

    public StandingOrder(Account sourceAccount, Account destinationAccount, double amount, LocalDate startDate, int intervalDays) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.startDate = startDate;
        this.intervalDays = intervalDays;
    }

    public boolean execute(LocalDate today) {
        if (today.equals(startDate) || today.isAfter(startDate) &&
                (today.toEpochDay() - startDate.toEpochDay()) % intervalDays == 0) {
            return sourceAccount.withdraw(amount) && destinationAccount.deposit(amount);
        }
        return false;
    }

    // Getters
    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getIntervalDays() {
        return intervalDays;
    }
}
