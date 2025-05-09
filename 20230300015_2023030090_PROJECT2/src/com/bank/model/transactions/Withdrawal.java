package com.bank.model.transactions;

import com.bank.model.accounts.Account;
import com.bank.model.users.User;

public class Withdrawal extends Transaction {

    private Account account;
    private double amount;

    public Withdrawal(Account account, double amount, User transactor, String reason) {
        super(transactor, "Withdrawal");
        this.account = account;
        this.amount = amount;
    }


    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public void execute() {

        if(account.getBalance()>= amount){
            account.withdraw(amount);
            System.out.println("The amount of " + amount +"€ has been withdrawaled from " + account.getIban());

        }else {
            System.out.println("Unavailable to withdraw due to isufficient balance.");
        }

    }

}
