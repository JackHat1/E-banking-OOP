package com.bank.model.orders;

import java.time.LocalDate;

import com.bank.model.accounts.Account;

public class TransferOrder extends StandingOrder{

    private Account from;
    private Account to;
    private double amount;
    private int transferFreq; // se mines
    private int transferDay;

    public TransferOrder(String title, String description, Account from, Account to, double amount, int transferFreq, int transferDay, LocalDate startingDate, LocalDate endingDate){
        super(title, description, startingDate, endingDate);
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.transferDay = transferDay;
        this.transferFreq = transferFreq;
    }

    @Override
    public void execute(){
        
    }


    
}
