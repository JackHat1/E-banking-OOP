package com.bank.manager;

import java.util.ArrayList;
import java.util.List;
import com.bank.model.bills.Bill;

public class BillManager {
    
    private final List<Bill> bills = new ArrayList<>();
    private final String[] path = {
        "./data/bills/2025-05-01.csv",
        "./data/bills/2025-05-05.csv",
        "./data/bills/2025-05-20.csv",
        "./data/bills/2025-06-01.csv"
    };


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













}
