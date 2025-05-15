package com.bank.manager;

import java.util.ArrayList;
import java.util.List;
import com.bank.model.bills.Bill;
import com.bank.model.transactions.Transaction;


public class BillManager {
    
    private final List<Bill> bills = new ArrayList<>();
    private final String[] path = {
        "./data/bills/2025-05-01.csv",
        "./data/bills/2025-05-05.csv",
        "./data/bills/2025-05-20.csv",
        "./data/bills/2025-06-01.csv"
    };

    //δημιουργία, ανάκτηση, αποθήκευση, διαγραφή

    public void create(Bill bill){
        bills.add(bill);
    }

    public void delete(Bill bill){
        if (bills.contains(bill)){

            bills.remove(bill);
        }

    }

    // den jerw akoma 
    public Bill getBill(String billNumber){
        for(Bill bill: bills){
            if(bill.getBillNumber().equals(billNumber)){
                return bill;
            }

        }
        return null;
    }

    public void load(){
        for(String filepath: path){

        }
    }

    public Bill getBillByRF(String rfCode) {
        for (Bill bill : bills) {
            if (bill.getPaymentCode().equals(rfCode)) {
                return bill;
            }
        }
        return null;
    }



    public void payBills(String billNumber){
        Bill bill = getBill(billNumber);


        if(bill.isPaid){
            // paid
            System.out.println("The bill is paid");
        }else{
            TransactionManager transactionManager = new TransactionManager();

            
            //Transaction transaction = new Payment(bill.getBillNumber(), bill.getAmount());
            //transactionManager.transactionExecution(transaction);

        }

        

    }










}
