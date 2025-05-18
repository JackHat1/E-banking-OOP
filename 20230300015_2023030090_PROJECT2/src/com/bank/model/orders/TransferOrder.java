package com.bank.model.orders;

import java.time.LocalDate;

import com.bank.manager.AccountManager;
import com.bank.manager.BillManager;
import com.bank.manager.TransactionManager;
import com.bank.model.accounts.Account;
import com.bank.model.transactions.Transaction;
import com.bank.model.transactions.Transfer;
import com.bank.model.users.User;

public class TransferOrder extends StandingOrder{

    //private Account from;
   // private Account to;
    private String fromIban;
    private String toIban;
    private double amount;
    private int transferFreq; // se mines
    private int transferDay;
    private int failedAttempts= 0;

    public TransferOrder(String title, String description, String fromIban, String toIban/*Account from, Account to*/, double amount, int transferFreq, int transferDay, LocalDate startingDate, LocalDate endingDate){
        super(title, description, startingDate, endingDate);
        this.fromIban = fromIban;
        this.toIban = toIban;
        this.amount = amount;
        this.transferDay = transferDay;
        this.transferFreq = transferFreq;
    }

    @Override
    public void execute(LocalDate trDayDate, BillManager billMan, AccountManager accountMan, TransactionManager transMan, User user){  //na ftaiksw gia ton xrono

        Account from= accountMan.findByIban(fromIban);
        Account to= accountMan.findByIban(toIban);

        if(!trDayDate.isBefore(getStartingDate()) && !trDayDate.isAfter(getEndingDate())) {
            if(trDayDate.getDayOfMonth()== transferDay){

                if(amount <= from.getBalance()){

                    Transaction transaction= new Transfer(from, to, amount, user, "StandingOrder Transfer", "StandingOrder Transfer");
                    transMan.execute(transaction);
                    //from.withdraw(amount);
                    //to.deposit(amount);
                    failedAttempts= 0;
                    System.out.println("Transfer executed "+ amount+ " from "+ from.getIban()+ " to "+to.getIban()+".");

                }else{
                    failedAttempts++;
                    System.out.println("Transfer failed due to insuffincient amount in balance.");
                }

            } else{
                System.out.println("Tranfer not thiw month.");
            }

        }else {
            System.out.println("Transfer not this period.");
        }

  
        if(failedAttempts > 3){
            System.out.println("Maximum failed attempts reached. ");
            return;
        }

        
    }

    @Override
    public boolean isFailed(){
        return failedAttempts>3;
    }

    
    @Override
    public String marshal(){
        StringBuffer sb = new StringBuffer();
        sb.append("type:TransferOrder").append(",");
        sb.append("orderId:").append(getOrderId()).append(",");
        sb.append("title:").append(getTitle()).append(",");
        sb.append("description:").append(getDescription()).append(",");
        //customer??den jerw akoma
        sb.append("amount:").append(amount).append(",");
        sb.append("startDate:").append(getStartingDate()).append(",");
        sb.append("endDate:").append(getEndingDate()).append(",");
        //fee?? 
        sb.append("chargeAccount:").append(fromIban).append(",");
        sb.append("creditAccount:").append(toIban).append(",");
        sb.append("frequencyInMonths:").append(transferFreq).append(",");
        sb.append("dayOfMonth:").append(transferDay);
        return sb.toString();
    }

    @Override
    public void unmarshal(String data){

        String[] parts = data.split(",");
        

        for(String part: parts){
            String[] keyValue= part.split(",");
            String key= keyValue[0];
            String value= "";

            if(keyValue.length > 1){
                value= keyValue[1];
            }

            if(key.equals("orderId")){
                this.orderId= value;
            } else if(key.equals("title")){
                this.title= value;
            } else if(key.equals("description")){
                this.description= value;
            } else if(key.equals("amount")){
                this.amount= Double.parseDouble(value);
            } else if(key.equals("startDate")){
                this.startingDate= LocalDate.parse(value);
            } else if(key.equals("endDate")){
                this.endingDate= LocalDate.parse(value);
            } else if(key.equals("chargeAccount")){
                this.fromIban= value;
                //this.from= accountMan.findByIban(value);
                //this.from = new Account(value, 0.0); // den jerw akoma
            } else if(key.equals("creditAccount")){
                this.toIban= value;
                //this.to= accountMan.findByIban(value);
                //this.to = new Account(value, 0.0);
            } else if(key.equals("frequencyInMonths")){
                this.transferFreq= Integer.parseInt(value);
            } else if(key.equals("dayOfMonth")){
                this.transferDay= Integer.parseInt(value);
            }

        }


 
      
    }


    
}
