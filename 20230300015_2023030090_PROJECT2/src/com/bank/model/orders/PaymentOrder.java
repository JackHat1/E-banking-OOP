package com.bank.model.orders;

import java.time.LocalDate;
import com.bank.model.bills.Bill;
import com.bank.model.accounts.Account;

public class PaymentOrder extends StandingOrder{

    private Bill bill;
    private Account from;
    private double maxAmount;

    public PaymentOrder(String title, String description, Bill bill, Account from, double maxAmount, LocalDate startingDate, LocalDate endingDate){
        super(title , description, startingDate, endingDate);
        this.bill = bill;
        this.from = from;
        this.maxAmount = maxAmount;
    }

    @Override
    public void execute(){
        
    }

    
}
