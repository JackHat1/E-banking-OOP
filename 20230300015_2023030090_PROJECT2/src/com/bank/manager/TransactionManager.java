package com.bank.manager;

import com.bank.model.transactions.Transaction;
//import com.bank.model.statements.StatementEntry;
//import com.bank.model.accounts.Account;
//import com.bank.model.statements.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    private final List<Transaction> history = new ArrayList<>();
    //private final StatementManager statementManager = new StatementManager();

    public void execute(Transaction transaction) {
        try {
            transaction.execute();
            history.add(transaction);

        
            // Αν είναι Deposit ή Withdrawal ή Transfer κ.λπ.
            // Το StatementEntry δημιουργείται ΜΕΣΑ στο transaction.execute()
            // Άρα εδώ δεν κάνουμε τίποτα άλλο
        } catch (Exception e) {
            System.err.println("Error at the transaction execution : " + e.getMessage());
        }
    }

    public List<Transaction> getHistory() {
        return new ArrayList<>(history);
    }
}
