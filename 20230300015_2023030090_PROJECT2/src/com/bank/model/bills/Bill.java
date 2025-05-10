package com.bank.model.bills;

import java.time.LocalDate;
import com.bank.model.users.User;
import com.bank.model.accounts.Account;

public class Bill {
    private String paymentCode;
    private String billNumber;
    private double amount;
    private Account issuer;
    private LocalDate issueDate;
    private LocalDate dueDate;
    public boolean isPaid = true;


    public Bill(String paymentCode, String billNumber, Account issuer, double amount, LocalDate issueDate, LocalDate dueDate) {
        this.paymentCode = paymentCode;
        this.billNumber = billNumber;
        this.issuer = issuer;
        this.amount = amount;
        this.issueDate = issueDate;
        this.dueDate = dueDate;

    }


    public String getPaymentCode() {
        return paymentCode;
    }

    private void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getBillNumber() {
        return billNumber;
    }

    private void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public double getAmount() {
        return amount;
    }

    private void setAmount(double amount) {
        this.amount = amount;
    }

    private Account getIssuer() {
        return issuer;
    }

    private void setIssuer(Account issuer) {
        this.issuer = issuer;
    }

    private LocalDate getIssueDate() {
        return issueDate;
    }

    private void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    private LocalDate getDueDate() {
        return dueDate;
    }

    private void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }



    public String toString(){
        return "Bill{ "+ "Payment Code: "+ paymentCode + 
        ", Bill Number: " + billNumber + 
        ", Issuer: " + issuer.getIban() + 
        ", Amount: " + amount + 
        ", Issue Date: " + issueDate + 
        ", Due Date: " + dueDate + 
        '}';
    }
    
}
