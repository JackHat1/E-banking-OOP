package com.bank.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.accounts.Account;
import com.bank.model.accounts.BusinessAccount;
import com.bank.model.bills.Bill;
import com.bank.model.users.Company;
import com.bank.model.users.Customer;
import com.bank.storage.CsvStorageManager;

public class BillManager {

    private final List<Bill> issuedBills = new ArrayList<>();
    private final List<Bill> paidBills = new ArrayList<>();
    private final String billsFolder = "./data/bills/";
    private final String issuedPath = billsFolder + "issued.csv";
    private final String paidPath = billsFolder + "paid.csv";

    private final AccountManager accountManager;
    private final UserManager userManager;
    private final CsvStorageManager storage = new CsvStorageManager();

    public BillManager(AccountManager accountManager, UserManager userManager) {
        this.accountManager = accountManager;
        this.userManager = userManager;
        loadBills();
    }

    public void createBill(Bill bill) {
        issuedBills.add(bill);

        String date = bill.getIssueDate().toString();
        String filePath = billsFolder + date + ".csv";
        List<Bill> single = new ArrayList<>();
        single.add(bill);
        storage.saveAll(single, filePath, true);

        saveAll();
    }

    public void payBill(Bill bill) {
        if (!bill.isPaid()) {
            bill.setPaid(true);

            issuedBills.removeIf(b -> b.getPaymentCode().equals(bill.getPaymentCode()));
            paidBills.add(bill);

    
            List<Bill> single = new ArrayList<>();
            single.add(bill);
            storage.saveAll(single, paidPath, true); 

            updateDailyFile(bill);     
            saveAll();                 
        }
    }



    private void updateDailyFile(Bill bill) {
        String fileName = billsFolder + bill.getIssueDate().toString() + ".csv";
        List<String> lines = storage.loadLines(fileName);
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            if (line.contains("paymentCode:" + bill.getPaymentCode())) {
                updatedLines.add(bill.marshal());
            } else {
                updatedLines.add(line);
            }
        }

        storage.saveLines(updatedLines, fileName, false);
    }

    public void loadBills() {
        issuedBills.clear();
        paidBills.clear();

        List<String> issuedLines = storage.loadLines(issuedPath);

        for (String line : issuedLines) {

            Bill bill = parseBill(line);
            if (bill != null && !bill.isPaid()) issuedBills.add(bill);
        }

        List<String> paidLines = storage.loadLines(paidPath);

        for (String line : paidLines) {

            Bill bill = parseBill(line);

            if (bill != null) {
                bill.setPaid(true);
                paidBills.add(bill);
            }
        }

        File folder = new File(billsFolder);
        File[] files = folder.listFiles((dir, name) -> name.matches("\\d{4}-\\d{2}-\\d{2}\\.csv"));
        if (files != null) {

            for (File file : files) {

                List<String> lines = storage.loadLines(file.getPath());
                for (String line : lines) {
                    Bill bill = parseBill(line);
                    if (bill == null) continue;
                    if (bill.isPaid()) {
                        if (!paidBills.stream().anyMatch(b -> b.getPaymentCode().equals(bill.getPaymentCode()))) {
                            paidBills.add(bill);
                        }
                    } else {
                        if (!issuedBills.stream().anyMatch(b -> b.getPaymentCode().equals(bill.getPaymentCode()))) {
                            issuedBills.add(bill);
                        }
                    }
                }
            }
        }
    }


    private Bill parseBill(String line) {
        Bill bill = new Bill("", "", 0.0, null);
        bill.unmarshal(line);

        String issuerVat = bill.getIssuerVat();
        Customer company = userManager.findByVat(issuerVat);

        if (!(company instanceof Company)){
            return null;
        } 

        for (Account acc : accountManager.getAllAccounts()) {

            if (acc instanceof BusinessAccount && acc.getOwner().equals(company)) {
                bill.setIssuer(acc);
                return bill;
            }
        }

        return null;
    }

    public void saveAll() {
        storage.saveAll(issuedBills, issuedPath, false);
        storage.saveAll(paidBills, paidPath, false);
    }

    
    public List<Bill> getAllBills() {
        List<Bill> all = new ArrayList<>();
        all.addAll(issuedBills);
        all.addAll(paidBills);
        return all;
    }

    public List<Bill> getIssuedBills() {
        return new ArrayList<>(issuedBills);
    }

    public List<Bill> getPaidBills() {
        return new ArrayList<>(paidBills);
    }

    public Bill getBillByRF(String rfCode) {
        for (Bill bill : getAllBills()) {
            if (bill.getPaymentCode().equals(rfCode)) {
                return bill;
            }
        }
        return null;
    }
}
