package com.bank.model.bills;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.bank.storage.Storable;
import com.bank.utilities.GlobalClock;
import com.bank.model.accounts.Account;

public class Bill implements Storable{
    private String paymentCode;
    private String billNumber;
    private double amount;
    private Account issuer;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private boolean isPaid;


    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Bill(String billNumber, String paymentCode, double amount, Account issuer) {
        this.billNumber = billNumber;
        this.paymentCode = paymentCode;
        this.amount = amount;
        this.issuer = issuer;
        this.issueDate = GlobalClock.getDate();
        this.dueDate = GlobalClock.getDate().plusDays(30);
    }
    

    public String getPaymentCode() {
        return paymentCode;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public double getAmount() {
        return amount;
    }

    public Account getIssuer() {
        return issuer;
    }

    public void setIssuer(Account issuer) {
        this.issuer = issuer;
    }


    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String toString(){
        return "Bill{ "+ "Payment Code: "+ paymentCode + 
        ", Bill Number: " + billNumber + 
        ", Issuer: " + issuer.getIban() + 
        ", Amount: " + amount + 
        ", Issue Date: " + issueDate.format(formatter) + 
        ", Due Date: " + dueDate.format(formatter) + 
        '}';
    }


    @Override
    public String marshal() {
        return "type:Bill,paymentCode:" + paymentCode + ",billNumber:" + billNumber + ",issuer:" + issuer.getIban() + ",amount:" + amount + ",issueDate:" + issueDate.format(formatter) + ",dueDate:" + dueDate.format(formatter) + ",isPaid:" + isPaid;
    }


    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
        for (int i = 0; i < parts.length; i++) {
            String[] kv = parts[i].split(":");
            if (kv.length == 2) {
                if (kv[0].equals("paymentCode")) this.paymentCode = kv[1];
                else if (kv[0].equals("billNumber")) this.billNumber = kv[1];
                else if (kv[0].equals("amount")) this.amount = Double.parseDouble(kv[1]);
                else if (kv[0].equals("issueDate")) this.issueDate = LocalDate.parse(kv[1]);
                else if (kv[0].equals("dueDate")) this.dueDate = LocalDate.parse(kv[1]);
                else if (kv[0].equals("isPaid")) this.isPaid = Boolean.parseBoolean(kv[1]);
            
            }
        }
    }
    
    
}
