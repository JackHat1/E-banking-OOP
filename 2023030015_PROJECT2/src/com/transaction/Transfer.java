package com.bank.transaction;

import com.bank.account.Account;
import java.time.LocalDateTime;

public class Transfer extends Transaction {
    private Account destinationAccount;

    public Transfer(String transactionId, Account sourceAccount, Account destinationAccount, double amount, LocalDateTime dateTime) {
        super(transactionId, sourceAccount, amount, dateTime);
        this.destinationAccount = destinationAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    @Override
    public String getTransactionType() {
        return "Transfer";
    }

    @Override
    public String toString() {
        return super.toString() + ", To: " + destinationAccount.getAccountNumber();
    }
}
