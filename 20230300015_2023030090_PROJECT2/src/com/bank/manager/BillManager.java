package com.bank.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.accounts.Account;
import com.bank.model.bills.Bill;
import com.bank.model.transactions.Payment;
import com.bank.model.users.User;


public class BillManager {
    
    private final List<Bill> bills = new ArrayList<>();
    private final String billsFolder= "./data/bills/";
    private AccountManager accountManager;


    public BillManager(AccountManager accountManager){
        this.accountManager= accountManager;     
    }



    public void create(Bill bill){
        bills.add(bill);
    }

    public void delete(Bill bill){
        if (bills.contains(bill)){

            bills.remove(bill);
        }

    }

    public void save(String fileName){

        try(BufferedWriter writer= new BufferedWriter(new FileWriter(fileName))){
            for(Bill bill: bills){
                writer.write(bill.marshal());
                writer.newLine();
            }

        }catch(IOException e){
            System.err.println("Error saving file: "+ e.getMessage());
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


    public void loadBills(String fileName){
        
        String fullPath = billsFolder+ fileName;
        try( BufferedReader reader= new BufferedReader(new FileReader(fullPath))){
            String line;

            while((line= reader.readLine()) != null){
                Bill bill= new Bill("", "", 0.0,null);
                bill.unmarshal(line);

                String[] parts= line.split(",");
                String issuer = parts[3];
                Account issuerAcc = accountManager.findByIban(issuer);
                bill.setIssuer(issuerAcc);
                bills.add(bill);

            }
        } catch(IOException e){
            System.err.println("Error reading file: "+ e.getMessage());
        }

    }


    public void payBills(String rfCode, Account from, User transactor){
        Bill bill = getBillByRF(rfCode);


        if(bill.isPaid()){
        
            System.out.println("The bill is paid.");
        }else{
            TransactionManager transactionManager = new TransactionManager();

            Account businessAccount= bill.getIssuer();
            Payment payment = new Payment(bill, from, businessAccount, transactor);
            transactionManager.execute(payment);
            
            bill.setPaid(true);
            System.out.println("The bill is paid now.");

        }


    }


    public List<Bill> getAllBills(){
        return new ArrayList<>(bills);
    }



}
