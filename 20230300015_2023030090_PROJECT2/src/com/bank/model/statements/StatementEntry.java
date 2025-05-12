package com.bank.model.statements;

import java.time.LocalDateTime;

//import com.bank.model.transactions.*;

public class StatementEntry {

    private String transactor;
    private String reason;
    private String type; // debit ,credit
    private double amount;
    private double balance;
    private LocalDateTime timestamp;
    private String fromIban;
    private String toIban;


    public StatementEntry(String transactor, String fromIban, String toIban, double amount, String reason, String type, LocalDateTime timestamp, double balance){
        this.transactor = transactor;
        this.fromIban = fromIban;
        this.toIban = toIban;
        this.amount = amount;
        this.reason = reason;
        this.type = type;
        this.timestamp = timestamp;
        this.balance = balance;

    }


     public String getTransactor() {
        return transactor;
    }

    public String getReason() {
        return reason;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFromIban() {
        return fromIban;
    }

    public String getToIban() {
        return toIban;

    }

    //public String toCsvString(){

    //}


    public String toString(){
        return "Statement: "+
                "Transactor: "+ transactor+
                ", From Iban: "+ fromIban+
                ", To Iban: "+ toIban+
                ", Amount: "+ amount+
                ", Reason: "+ reason+
                ", Type(debit or credit): "+ type+
                ", Time: "+ timestamp+
                ", Remaining Balance: "+ balance;

    }


    

    
}
