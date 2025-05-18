package com.bank.model.orders;

import java.time.LocalDate;
import com.bank.model.bills.Bill;
import com.bank.model.transactions.Transaction;
import com.bank.manager.AccountManager;
import com.bank.manager.TransactionManager;
import com.bank.model.accounts.Account;
import com.bank.model.accounts.BusinessAccount;

public class PaymentOrder extends StandingOrder{

    private Bill bill;
    private Account from;
    private double maxAmount;
    private int failedAttempts= 0;
    private String fromIban;
    

    public PaymentOrder(String title, String description, Bill bill, Account from, double maxAmount, LocalDate startingDate, LocalDate endingDate){
        super(title , description, startingDate, endingDate);
        this.bill = bill;
        this.from = from;
        this.maxAmount = maxAmount;
    }

    @Override
    public void execute(LocalDate paymentDay){  //na ftaiksw gia ton xrono

        if(bill.getDueDate().isEqual(paymentDay)){

            if(bill.getAmount() <= maxAmount ){

                if(bill.getAmount() <= from.getBalance()){

                    from.withdraw(bill.getAmount());
                    bill.setPaid(true);
                    failedAttempts= 0;
                    System.out.println("Payment executed for bill " + bill.getPaymentCode());
                } else{
                    failedAttempts++;
                    System.out.println("Payment failed due to insufficient funds.");
                }
            } else{
                failedAttempts++;
                System.out.println("Payment failed due to exceeding max amount.");
            }

        }

        if(failedAttempts > 3){
            System.out.println("Maximum failed attempts reached. ");
            return;

        }

    }

    @Override
    public String marshal() {
        StringBuffer sb = new StringBuffer();
        sb.append("type:PaymentOrder").append(",");
        sb.append("orderId:").append(getOrderId()).append(",");
        sb.append("paymentCode:").append(bill.getPaymentCode()).append(",");
        sb.append("title:").append(getTitle()).append(",");
        sb.append("description:").append(getDescription()).append(",");
        //customer??den jerw akoma
        sb.append("maxAmount:").append(maxAmount).append(",");
        sb.append("startDate:").append(getStartingDate()).append(",");
        sb.append("endDate:").append(getEndingDate()).append(",");
        sb.append("chargeAccount:").append(from.getIban());
        return sb.toString();
    }

    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");

        for(String part: parts){
            String[] keyValue= part.split(":");
            String key = keyValue[0];
            String value= "";

            if(value.length() >1){
                value= keyValue[1];
            }

            if(key.equals("orderId")){
                this.orderId = value;
            } else if(key.equals("paymentCode")){
                
                this.bill= new Bill("", value, 0.0, null); // den jerw akoma
            } else if(key.equals("title")){
                this.title= value;
            } else if(key.equals("description")){
                this.description= value;
            } else if(key.equals("maxAmount")){
                this.maxAmount= Double.parseDouble(value);
            } else if(key.equals("startDate")){
                this.startingDate= LocalDate.parse(value);
            } else if(key.equals("endDate")){
                this.endingDate= LocalDate.parse(value);
            } else if(key.equals("chargeAccount")){
                this.fromIban= value;
                //Account from = accountManager.findByIban(fromIban);
                //this.from= accountMan.findByIban(value);
                //this.from = new Account(value, 0.0); // mhpws na baloyme to ibam ston constructor?
            }

        }
        
        
    }

    
}
