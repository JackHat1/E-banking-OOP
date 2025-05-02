package com.bank.transaction;

import com.bank.account.Account;

public class Transfer extends Transaction {

    public Transfer(String id, double amount, Account sourceAccount, Account destinationAccount) {
        super(id, amount, sourceAccount, destinationAccount, "Transfer");
    }

    @Override
    public void process() {
        if (getSourceAccount() != null && getDestinationAccount() != null
                && getSourceAccount().getBalance() >= getAmount()) {
            getSourceAccount().withdraw(getAmount());
            getDestinationAccount().deposit(getAmount());
            setStatus("COMPLETED");
        } else {
            setStatus("FAILED");
        }
    }
}
