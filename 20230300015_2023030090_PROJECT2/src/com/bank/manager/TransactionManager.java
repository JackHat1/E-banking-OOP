package com.bank.manager;

import com.bank.model.transactions.Deposit;
import com.bank.model.transactions.Payment;
import com.bank.model.transactions.Transaction;
import com.bank.model.transactions.Transfer;
import com.bank.model.transactions.Withdrawal;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    private final List<Transaction> history = new ArrayList<>();

    public void execute(Transaction transaction) {
        try {
            if (transaction instanceof Deposit deposit) {
                deposit.getAccount().deposit(deposit.getAmount());

            } else if (transaction instanceof Withdrawal withdrawal) {
                if (withdrawal.getAccount().getBalance() >= withdrawal.getAmount()) {
                    withdrawal.getAccount().withdraw(withdrawal.getAmount());
                } else {
                    System.out.println("Withdrawal failed: insufficient balance.");
                    return;
                }

            } else if (transaction instanceof Transfer transfer) {
                if (transfer.getFrom().getBalance() >= transfer.getAmount()) {
                    transfer.getFrom().withdraw(transfer.getAmount());
                    transfer.getTo().deposit(transfer.getAmount());
                } else {
                    System.out.println("Transfer failed: insufficient balance.");
                    return;
                }

            } else if (transaction instanceof Payment payment) {
                double amount = payment.getBill().getAmount();
                if (payment.getFrom().getBalance() >= amount) {
                    payment.getFrom().withdraw(amount);
                    payment.getBusiness().deposit(amount);
                    payment.getBill().setPaid(true);
                } else {
                    System.out.println("Payment failed: insufficient balance.");
                    return;
                }
            }

            transaction.execute();
            history.add(transaction);

        } catch (Exception e) {
            System.err.println("Error at the transaction execution: " + e.getMessage());
        }
    }

    public List<Transaction> getHistory() {
        return new ArrayList<>(history);
    }
}