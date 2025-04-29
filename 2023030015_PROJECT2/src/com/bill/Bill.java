package com.bank.bill;

import java.time.LocalDate;

public class Bill {
    private String billId;
    private String provider;
    private double amount;
    private LocalDate dueDate;
    private boolean paid;

    public Bill(String billId, String provider, double amount, LocalDate dueDate) {
        this.billId = billId;
        this.provider = provider;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paid = false;
    }

    public String getBillId() {
        return billId;
    }

    public String getProvider() {
        return provider;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void markAsPaid() {
        this.paid = true;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "ID='" + billId + '\'' +
                ", provider='" + provider + '\'' +
                ", amount=" + amount +
                ", dueDate=" + dueDate +
                ", paid=" + paid +
                '}';
    }
}
