package com.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaction {
    protected String id;
    protected double amount;
    protected LocalDateTime timestamp;

    public Transaction(String id, double amount) {
        this.id = id;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public abstract String getType();

    public abstract String getSummary();  // To display details of the transaction

    public abstract String toCSV();       // Used for saving to file
}
