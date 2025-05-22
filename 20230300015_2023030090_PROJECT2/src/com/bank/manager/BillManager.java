package com.bank.manager;

import java.util.ArrayList;
import java.util.List;

import com.bank.model.accounts.Account;
import com.bank.model.accounts.BusinessAccount;
import com.bank.model.bills.Bill;
import com.bank.model.transactions.Payment;
import com.bank.model.users.Company;
import com.bank.model.users.Customer;
import com.bank.model.users.User;
import com.bank.storage.CsvStorageManager;


public class BillManager {
    
    private final List<Bill> bills = new ArrayList<>();
    private final String billsFolder= "./data/bills/";
    private AccountManager accountManager;
    private final CsvStorageManager storage= new CsvStorageManager();
    private UserManager userManager;

    public BillManager(AccountManager accountManager, UserManager userManager) {
        this.accountManager = accountManager;
        this.userManager = userManager;
    }



    public void createBill(Bill bill){
        bills.add(bill);
    }

    public void deleteBill(Bill bill){
        if (bills.contains(bill)){

            bills.remove(bill);
        }

    }

    public void saveBill() {
        for (int i = 0; i < bills.size(); i++) {
            Bill bill = bills.get(i);
            String date = bill.getIssueDate().toString(); 
            String filePath = billsFolder + date + ".csv";
            List<Bill> singleBillList = new ArrayList<>();
            singleBillList.add(bill);
        
            storage.saveAll(singleBillList, filePath, true);
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


      public void loadBills() {
        java.io.File folder = new java.io.File(billsFolder);
        java.io.File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files == null){
            return;
        }
        
    
        for (int i = 0; i < files.length; i++) {
            List<String> lines = storage.loadLines(files[i].getPath());
            for (int j = 0; j < lines.size(); j++) {
                String line = lines.get(j);
                Bill bill = new Bill("", "", 0.0, null);
                bill.unmarshal(line);
    
                String issuerVat = null;
                String[] parts = line.split(",");
                for (int k = 0; k < parts.length; k++) {
                    String[] kv = parts[k].split(":", 2);
                    if (kv.length == 2 && kv[0].equals("issuer")) {
                        issuerVat = kv[1];
                        break;
                    }
                }
    
                if (issuerVat == null) {
                    System.out.println("No issuer VAT found for bill " + bill.getPaymentCode());
                    continue;
                }
    
                Customer company = userManager.findByVat(issuerVat);
                if (company == null || !(company instanceof Company)) {
                    
                    continue;
                }
    
                Account issuerAcc = null;
                List<Account> allAccounts = accountManager.getAllAccounts();
                for (int a = 0; a < allAccounts.size(); a++) {
                    Account acc = allAccounts.get(a);
                    if (acc instanceof BusinessAccount && acc.getOwner().equals(company)) {
                        issuerAcc = acc;
                        break;
                    }
                }
    
                if (issuerAcc == null) {
                    System.out.println("No business account found for company VAT " + issuerVat);
                    continue;
                }
    
                bill.setIssuer(issuerAcc);
                bills.add(bill);
            }
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
