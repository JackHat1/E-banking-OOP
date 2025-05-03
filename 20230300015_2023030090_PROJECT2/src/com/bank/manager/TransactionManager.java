package com.bank.manager;

import com.bank.model.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private static TransactionManager instance = null;
    private List<Transaction> history = new ArrayList<>();

    private TransactionManager() {}

    public static TransactionManager getInstance() {
        if (instance == null) instance = new TransactionManager();
        return instance;
    }

    public void executeTransaction(Transaction tx) {
        tx.execute();
        history.add(tx);
    }

    public List<Transaction> getHistory() {
        return history;
    }
}
