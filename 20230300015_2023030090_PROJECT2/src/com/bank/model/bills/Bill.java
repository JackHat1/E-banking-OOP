package com.bank.model.bills;

import java.time.LocalDate;

public class Bill {
    private String paymentCode;
    private String billNumber;
    private double amount;
    private String issuer;
    //private String customer;
    private LocalDate issueDate;
    private LocalDate dueDate;


    public Bill(String paymentCode, String billNumber, String issuer, double amount, LocalDate issueDate, LocalDate dueDate) {
        this.paymentCode = paymentCode;
        this.billNumber = billNumber;
        this.issuer = issuer;
       // this.customer = customer;
        this.amount = amount;
        this.issueDate = issueDate;
        this.dueDate = dueDate;

    }


    private String getPaymentCode() {
        return paymentCode;
    }

    private void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    private String getBillNumber() {
        return billNumber;
    }

    private void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    private double getAmount() {
        return amount;
    }

    private void setAmount(double amount) {
        this.amount = amount;
    }

    private String getIssuer() {
        return issuer;
    }

    private void setIssuer(String issuer) {
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


    
    public String printBill(){
        return "Bill{ "+ "Payment Code: "+ paymentCode + ", Bill Number: " + billNumber + ", Issuer: " + issuer + ", Amount: " + amount + ", Issue Date: " + issueDate + ", Due Date: " + dueDate + '}';
    }
    
}
