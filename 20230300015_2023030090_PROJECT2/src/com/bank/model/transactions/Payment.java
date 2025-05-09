package com.bank.model.transactions;

import com.bank.model.accounts.Account;
import com.bank.model.users.User;
import com.bank.model.bills.Bill;

public class Payment extends Transaction {
    private Bill bill;
    private Account from;
    private Account business;
    //private double amount;
   // private Bill paymentCode;
   // private String billReason;

    public Payment(Bill bill, Account from, Account business, User transactor) {
        super(transactor, "Payment");
        this.bill = bill;
        this.from = from;
        this.business = business;
        //this.amount = amount;
        //this.paymentCode = rfCode;
        //this.billReason = billReason;
    }

    public Bill getBill(){
        return bill;
    }

    @Override
    public void execute() {
        if (from.getBalance() >= bill.getAmount()) {
            from.withdraw(bill.getAmount());
            business.deposit(bill.getAmount());
            bill.isPaid = true;
            System.out.println("Payment from " + bill.getAmount() + "€ to business " + business.getIban() + " [RF: " + bill.getPaymentCode() + "]");
        } else {
            System.out.println(" Unavailable payment due to insufficient balance.");
        }
    }
}
