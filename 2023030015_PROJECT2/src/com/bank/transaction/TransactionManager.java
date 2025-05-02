package com.bank.transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private List<Transaction> transactions;

    public TransactionManager() {
        this.transactions = new ArrayList<>();
    }

    public void recordTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.process();
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public List<Transaction> getTransactionsByType(String type) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase(type)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if ((t.getSourceAccount() != null && t.getSourceAccount().getAccountNumber().equals(accountNumber)) ||
                (t.getDestinationAccount() != null && t.getDestinationAccount().getAccountNumber().equals(accountNumber))) {
                result.add(t);
            }
        }
        return result;
    }
}
