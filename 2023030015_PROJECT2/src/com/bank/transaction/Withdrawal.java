package com.bank.transaction;

import com.bank.account.Account;

public class Withdrawal extends Transaction {

    public Withdrawal(String id, double amount, Account sourceAccount) {
        super(id, amount, sourceAccount, null, "Withdrawal");
    }

    @Override
    public void process() {
        if (getSourceAccount() != null && getSourceAccount().getBalance() >= getAmount()) {
            getSourceAccount().withdraw(getAmount());
            setStatus("COMPLETED");
        } else {
            setStatus("FAILED");
        }
    }
}
