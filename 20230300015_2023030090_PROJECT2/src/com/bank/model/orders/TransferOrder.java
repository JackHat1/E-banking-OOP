package com.bank.model.orders;

import java.time.LocalDate;

import com.bank.model.accounts.Account;

public class TransferOrder extends StandingOrder{

    private Account from;
    private Account to;
    private double amount;
    private int transferFreq; // se mines
    private int transferDay;
    private int failedAttempts= 0;

    public TransferOrder(String title, String description, Account from, Account to, double amount, int transferFreq, int transferDay, LocalDate startingDate, LocalDate endingDate){
        super(title, description, startingDate, endingDate);
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.transferDay = transferDay;
        this.transferFreq = transferFreq;
    }

    @Override
    public void execute(LocalDate trDayDate){

        if(!trDayDate.isBefore(getStartingDate()) && !trDayDate.isAfter(getEndingDate())) {
            if(trDayDate.getDayOfMonth()== transferDay){

                if(amount <= from.getBalance()){
                    from.withdraw(amount);
                    to.deposit(amount);
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
    public String marshal(){
        StringBuffer sb = new StringBuffer();
        sb.append("type:TransferOrder,");
        sb.append("orderId:").append(getOrderId()).append(",");
        sb.append("title:").append(getTitle()).append(",");
        sb.append("description:").append(getDescription()).append(",");
        //customer??den jerw akoma
        sb.append("amount:").append(amount).append(",");
        sb.append("startDate:").append(getStartingDate()).append(",");
        sb.append("endDate:").append(getEndingDate()).append(",");
        //fee?? 
        sb.append("chargeAccount:").append(from.getIban()).append(",");
        sb.append("creditAccount:").append(to.getIban()).append(",");
        sb.append("frequencyInMonths:").append(transferFreq).append(",");
        sb.append("dayOfMonth").append(transferDay);
        return sb.toString();
    }

    @Override
    public void unmarshal(String data){
        String[] keyValue= data.split(",");
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
            this.from = new Account(value, 0.0); // den jerw akoma
        } else if(key.equals("creditAccount")){
            this.to = new Account(value, 0.0);
        } else if(key.equals("frequencyInMonths")){
            this.transferFreq= Integer.parseInt(value);
        } else if(key.equals("dayOfMonth")){
            this.transferDay= Integer.parseInt(value);
        } 
      
    }


    
}
