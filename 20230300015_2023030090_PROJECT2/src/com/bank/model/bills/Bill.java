package com.bank.model.bills;

import java.time.LocalDate;
import com.bank.model.users.User;
import com.bank.storage.Storable;
import com.bank.model.accounts.Account;

public class Bill implements Storable{
    private String paymentCode;
    private String billNumber;
    private double amount;
    private Account issuer;
    private LocalDate issueDate;
    private LocalDate dueDate;
    public boolean isPaid = true;


    public Bill(String billNumber, String paymentCode, double amount, Account issuer) {
        this.billNumber = billNumber;
        this.paymentCode = paymentCode;
        this.amount = amount;
        this.issuer = issuer;
        this.issueDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(30);
        this.isPaid = false;
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

        @Override
    public String marshal() {
        return String.join(",",
            billNumber,
            paymentCode,
            String.valueOf(amount),
            issuer.getIban(),
            issueDate.toString(),
            dueDate.toString(),
            String.valueOf(isPaid)
        );
    }

    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
        this.billNumber = parts[0];
        this.paymentCode = parts[1];
        this.amount = Double.parseDouble(parts[2]);
        // issuer πρέπει να ανατεθεί από έξω (δεν έχουμε access σε AccountManager εδώ)
        this.issueDate = LocalDate.parse(parts[4]);
        this.dueDate = LocalDate.parse(parts[5]);
        this.isPaid = Boolean.parseBoolean(parts[6]);
    }
    
    
}
