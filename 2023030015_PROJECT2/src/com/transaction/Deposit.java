package com.transaction;

public class Deposit extends Transaction {
    private String targetIBAN;

    public Deposit(String id, double amount, String targetIBAN) {
        super(id, amount);
        this.targetIBAN = targetIBAN;
    }

    public String getTargetIBAN() {
        return targetIBAN;
    }

    @Override
    public String getType() {
        return "Deposit";
    }

    @Override
    public String getSummary() {
        return "Deposit of " + amount + " to " + targetIBAN + " at " + getFormattedTimestamp();
    }

    @Override
    public String toCSV() {
        return String.join(",", "deposit", id, String.valueOf(amount), targetIBAN, getFormattedTimestamp());
    }
}
