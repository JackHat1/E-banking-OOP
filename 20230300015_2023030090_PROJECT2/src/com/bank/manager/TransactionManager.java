package com.bank.manager;

import com.bank.model.transactions.Transaction;
import com.bank.model.accounts.Account;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
   
    private final List<Transaction> history = new ArrayList<>();

    void transactionExecution(Transaction transaction){

    }


    private void updateBalance(Transaction transaction){



    }

    public List<Transaction> getHistory(){
        return new ArrayList<>(history);
    }


}
