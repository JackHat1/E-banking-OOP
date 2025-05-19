package com.bank.model.bills;

import java.time.LocalDate;
//import com.bank.model.users.User;
import com.bank.storage.Storable;
import com.bank.model.accounts.Account;

public class Bill implements Storable{
    private String paymentCode;
    private String billNumber;
    private double amount;
    private Account issuer;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private boolean isPaid;


    public Bill(String billNumber, String paymentCode, double amount, Account issuer) {
        this.billNumber = billNumber;
        this.paymentCode = paymentCode;
        this.amount = amount;
        this.issuer = issuer;
        this.issueDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(30);
        //this.isPaid = false;
    }
    

    public String getPaymentCode() {
        return paymentCode;
    }

    /*private void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }*/

    public String getBillNumber() {
        return billNumber;
    }

    /*private void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }*/

    public double getAmount() {
        return amount;
    }

    /*private void setAmount(double amount) {
        this.amount = amount;
    }*/

    public Account getIssuer() {
        return issuer;
    }

    public void setIssuer(Account issuer) {
        this.issuer = issuer;
    }


    public LocalDate getIssueDate() {
        return issueDate;
    }

    // private void setIssueDate(LocalDate issueDate) {
    //     this.issueDate = issueDate;
    // }

    public LocalDate getDueDate() {
        return dueDate;
    }

    /*private void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }*/

    //-------------------toys thelw
    public boolean isPaid() {
        return isPaid;
    }


    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    //----------------

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
            return "type:Bill,paymentCode:" + paymentCode + ",billNumber:" + billNumber + ",issuer:" + issuer.getIban() + ",amount:" + amount + ",issueDate:" + issueDate + ",dueDate:" + dueDate + ",isPaid:" + isPaid;
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
                else if (kv[0].equals("issueDate")) this.issueDate = java.time.LocalDate.parse(kv[1]);
                else if (kv[0].equals("dueDate")) this.dueDate = java.time.LocalDate.parse(kv[1]);
                else if (kv[0].equals("isPaid")) this.isPaid = Boolean.parseBoolean(kv[1]);
                // issuer ανατίθεται από έξω στο BillManager
            }
        }
    }
    
    
}
