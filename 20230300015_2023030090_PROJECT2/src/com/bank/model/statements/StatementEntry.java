package com.bank.model.statements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatementEntry {

    private String transactor;
    private String reason;
    private String type; // debit ,credit
    private double amount;
    private double balance;
    private LocalDateTime timestamp;
    private String fromIban;
    private String toIban;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatementEntry(String transactor, String fromIban, String toIban, double amount, String reason, String type, LocalDateTime timestamp, double balance) {
        this.transactor = transactor;
        this.fromIban = fromIban;
        this.toIban = toIban;
        this.amount = amount;
        this.reason = reason;
        this.type = type;
        this.timestamp = timestamp;
        this.balance = balance;
    }

    public String getTransactor() { return transactor; }
    public String getReason() { return reason; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalance() { return balance; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getFromIban() { return fromIban; }
    public String getToIban() { return toIban; }

    public String marshal() {
        return String.join(",",
            transactor,
            fromIban,
            toIban,
            String.valueOf(amount),
            reason.replace(",", " "),
            type,
            formatter.format(timestamp),
            String.valueOf(balance)
        );
    }

    public static StatementEntry unmarshal(String line) {
        try {
            String[] parts = line.split(",", -1);
            String transactor = parts[0];
            String fromIban = parts[1];
            String toIban = parts[2];
            double amount = Double.parseDouble(parts[3]);
            String reason = parts[4];
            String type = parts[5];
            LocalDateTime timestamp = LocalDateTime.parse(parts[6], formatter);
            double balance = Double.parseDouble(parts[7]);

            return new StatementEntry(transactor, fromIban, toIban, amount, reason, type, timestamp, balance);
        } catch (Exception e) {
            System.err.println("❌ Σφάλμα unmarshal StatementEntry: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "Statement: " +
               "Transactor: " + transactor +
               ", From Iban: " + fromIban +
               ", To Iban: " + toIban +
               ", Amount: " + amount +
               ", Reason: " + reason +
               ", Type(debit or credit): " + type +
               ", Time: " + formatter.format(timestamp) +
               ", Remaining Balance: " + balance;
    }
}
