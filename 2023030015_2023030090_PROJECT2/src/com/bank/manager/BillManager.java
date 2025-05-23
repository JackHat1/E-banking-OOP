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
        initializeStorageFiles();
        loadBills();
    }

    private void initializeStorageFiles() {
        try {
            new File(issuedPath).createNewFile();
            new File(paidPath).createNewFile();
        } catch (Exception e) {
            System.err.println(" Failed to create issued/paid files: " + e.getMessage());
        }
    }

    public void createBill(Bill bill) {
        issuedBills.removeIf(b -> b.getPaymentCode().equals(bill.getPaymentCode()));
        issuedBills.add(bill);

        String filePath = billsFolder + bill.getIssueDate().toString() + ".csv";
        List<String> lines = storage.loadLines(filePath);
        lines.removeIf(l -> l.contains("paymentCode:" + bill.getPaymentCode()));
        lines.add(stripIsPaid(bill.marshal()));
        storage.saveLines(lines, filePath, false);

        saveAll();
    }

    public void payBill(Bill bill) {
        if (bill.isPaid()) return;

        bill.setPaid(true);
        issuedBills.removeIf(b -> b.getPaymentCode().equals(bill.getPaymentCode()));
        paidBills.removeIf(b -> b.getPaymentCode().equals(bill.getPaymentCode()));
        paidBills.add(bill);

        updateDailyFile(bill);
        saveAll();
    }

    private void updateDailyFile(Bill bill) {
        String filePath = billsFolder + bill.getIssueDate().toString() + ".csv";
        List<String> lines = storage.loadLines(filePath);
        List<String> updated = new ArrayList<>();
        for (String line : lines) {
            if (line.contains("paymentCode:" + bill.getPaymentCode())) {
                updated.add(stripIsPaid(bill.marshal()));
            } else {
                updated.add(line);
            }
        }
        storage.saveLines(updated, filePath, false);
    }

    public void loadBills() {
        issuedBills.clear();
        paidBills.clear();

        // Load daily files
        File folder = new File(billsFolder);
        File[] files = folder.listFiles((dir, name) -> name.matches("\\d{4}-\\d{2}-\\d{2}\\.csv"));
        if (files != null) {
            for (File file : files) {
                List<String> lines = storage.loadLines(file.getPath());
                for (String line : lines) {
                    if (!line.contains("isPaid:")) line += ",isPaid:false";
                    Bill bill = parseBill(line);
                    if (bill != null && !bill.isPaid()) {
                        issuedBills.add(bill);
                    }
                }
            }
        }

        // Load issued.csv
        for (String line : storage.loadLines(issuedPath)) {
            if (!line.contains("isPaid:")) line += ",isPaid:false";
            Bill bill = parseBill(line);
            if (bill != null && !bill.isPaid()) {
                issuedBills.add(bill);
            }
        }

        // Load paid.csv
        for (String line : storage.loadLines(paidPath)) {
            if (!line.contains("isPaid:")) line += ",isPaid:true";
            Bill bill = parseBill(line);
            if (bill != null) {
                bill.setPaid(true);
                paidBills.add(bill);
            }
        }
    }

    private Bill parseBill(String line) {
        Bill bill = new Bill("", "", 0.0, null);
        bill.unmarshal(line);

        String issuerVat = bill.getIssuerVat();
        Customer company = userManager.findByVat(issuerVat);
        if (!(company instanceof Company)) return null;

        for (Account acc : accountManager.getAllAccounts()) {
            if (acc instanceof BusinessAccount && acc.getOwner().equals(company)) {
                bill.setIssuer(acc);
                return bill;
            }
        }
        return null;
    }

    private String stripIsPaid(String line) {
        return line.replaceAll(",isPaid:(true|false)", "");
    }

    public void saveAll() {
        List<String> issuedLines = new ArrayList<>();
        for (Bill b : issuedBills) issuedLines.add(stripIsPaid(b.marshal()));
        storage.saveLines(issuedLines, issuedPath, false);

        List<String> paidLines = new ArrayList<>();
        for (Bill b : paidBills) paidLines.add(b.marshal());
        storage.saveLines(paidLines, paidPath, false);
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
            if (bill.getPaymentCode().equals(rfCode)) return bill;
        }
        return null;
    }
}
