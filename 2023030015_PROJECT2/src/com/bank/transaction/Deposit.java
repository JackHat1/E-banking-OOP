package com.bank.transaction;

import com.bank.account.Account;

public class Deposit extends Transaction {

    public Deposit(String id, double amount, Account sourceAccount) {
        super(id, amount, sourceAccount, null, "Deposit");
    }

    @Override
    public void process() {
        if (getSourceAccount() != null) {
            getSourceAccount().deposit(getAmount());
            setStatus("COMPLETED");
        } else {
            setStatus("FAILED");
        }
    }
}
