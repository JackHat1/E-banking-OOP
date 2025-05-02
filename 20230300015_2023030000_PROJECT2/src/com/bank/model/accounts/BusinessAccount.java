package com.bank.model.accounts;

import com.bank.model.users.Company;

public class BusinessAccount extends Account {
    private double maintenanceFee;

    public BusinessAccount(Company owner, double interestRate, double maintenanceFee) {
        super(owner, interestRate);
        this.maintenanceFee = maintenanceFee;
    }

    public double getMaintenanceFee() {
        return maintenanceFee;
    }

    @Override
    public String getAccountTypeCode() {
        return "200";
    }
}
