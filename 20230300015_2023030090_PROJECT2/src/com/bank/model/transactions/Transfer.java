package com.bank.model.transactions;

import java.time.LocalDateTime;

import com.bank.manager.StatementManager;
import com.bank.model.accounts.Account;
import com.bank.model.statements.StatementEntry;
import com.bank.model.users.User;

public class Transfer extends Transaction {
    private Account from;
    private Account to;
    private double amount;
    private String senderReason;
    private String receiverReason;

    public Transfer(Account from, Account to, double amount, User transactor, String senderReason, String receiverReason) {
        super(transactor, "Transfer");
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.senderReason = senderReason;
        this.receiverReason = receiverReason;
    }

    @Override
    public void execute() {
        if (from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);

            System.out.println("Transfer of " + amount + "€ from " + from.getIban() + " to " + to.getIban());
            System.out.println("Sender Reason: " + senderReason);
            System.out.println("Receiver Reason: " + receiverReason);

            StatementEntry fromAccountEntry = new StatementEntry(getTransactor().getUsername(),from.getIban(),to.getIban(),amount,senderReason,"Debit",LocalDateTime.now(),from.getBalance());

            StatementEntry toAccountEntry = new StatementEntry(getTransactor().getUsername(),from.getIban(),to.getIban(),amount,receiverReason,"Credit",LocalDateTime.now(),to.getBalance());

            StatementManager statementManager = new StatementManager();
            statementManager.save(from, fromAccountEntry);
            statementManager.save(to, toAccountEntry);

        } else {
            System.out.println("Unavailable transfer due to insufficient balance.");
        }
    }
}
