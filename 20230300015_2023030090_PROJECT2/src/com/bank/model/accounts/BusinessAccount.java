package com.bank.model.accounts;

import com.bank.model.users.Company;

public class BusinessAccount extends Account {
    private double monthlyFee;

    public BusinessAccount(Company owner, double interestRate, double monthlyFee) {
        super(owner, interestRate);
        this.monthlyFee = monthlyFee;
    }

    public double getMonthlyFee(){
    return monthlyFee;
    }

    @Override
    public String getAccountTypeCode() {
        return "200";
    }
    @Override
    public String marshal() {
        return "type:BusinessAccount"
             + ",iban:" + getIban()
             + ",primaryOwner:" + getOwner().getVat()
             + ",dateCreated:" + dateCreated.toString()
             + ",rate:" + getInterestRate()
             + ",balance:" + getBalance()
             + ",fee:" + getMonthlyFee();
    }
    
    

}
