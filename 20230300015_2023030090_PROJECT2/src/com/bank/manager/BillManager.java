package com.bank.manager;

import java.io.File;
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
    

    // === Δημιουργία / Διαγραφή ===
    public void createBill(Bill bill) {
        bills.add(bill);
    }

    public void deleteBill(Bill bill) {
        bills.remove(bill);
    }

    // === Αποθήκευση ===
    public void saveBill() {
        for (Bill bill : bills) {

            
            String date = bill.getIssueDate().toString();
            String filePath = billsFolder + date + ".csv";
            List<Bill> single = new ArrayList<>();
            single.add(bill);
            storage.saveAll(single, filePath, true);
        }
        
    }

    // === Ενημέρωση issued.csv ===
    public void saveIssuedBills() {
        List<Bill> issued = new ArrayList<>();
        for (Bill b : bills) {
            if (!b.isPaid()) issued.add(b);
        }
        storage.saveAll(issued, issuedPath, false);
    }



    public void saveAllBills() {
        storage.saveAll(bills, issuedPath, false);
    }
    public void updateIssuedCsv(Bill updatedBill) {
        List<Bill> updatedList = new ArrayList<>();

        for (Bill bill : bills) {
            if (!bill.isPaid()) {
                updatedList.add(bill); // κράτα μόνο τους unpaid
            }
        }

        storage.saveAll(updatedList, issuedPath, false); // overwrite
    }




    // === Ενημέρωση paid.csv ===
    public void savePaidBills() {
        List<Bill> paid = new ArrayList<>();
        for (Bill b : bills) {
            if (b.isPaid()) paid.add(b);
        }
        storage.saveAll(paid, paidPath, false);
    }


    // === Φόρτωση από όλα τα .csv με ημερομηνίες ===
    public void loadBills() {
        bills.clear(); // <<--- ΠΟΛΥ ΣΗΜΑΝΤΙΚΟ: Καθαρίζει τη λίστα πριν ξαναφορτώσεις
        File folder = new File(billsFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv") && !name.equals("issued.csv") && !name.equals("paid.csv"));
                if (files == null || files.length == 0) {
            System.out.println("❌ No CSV files found in: " + folder.getAbsolutePath());
            return;
        } else {
            for (java.io.File f : files) {
                System.out.println("✅ Found file: " + f.getName());            ////
            }
        }

        
        

        if (files == null) return;

        for (File file : files) {
            List<String> lines = storage.loadLines(file.getPath());

            for (String line : lines) {
                Bill bill = new Bill("", "", 0.0, null);
                bill.unmarshal(line);

                String issuerVat = null;
                String[] parts = line.split(",");
                for (String part : parts) {
                    String[] floor = part.split(":", 2);
                    if (floor.length == 2 && floor[0].equals("issuer")) {
                        issuerVat = floor[1];
                        break;
                    }
                }



                if (issuerVat == null) {
                    System.out.println("No issuer VAT found for bill " + bill.getPaymentCode()); ///
                    continue;
                }

                Customer company = userManager.findByVat(issuerVat);
                if (!(company instanceof Company)) continue;

                Account issuerAcc = null;
                for (Account acc : accountManager.getAllAccounts()) {
                    if (acc instanceof BusinessAccount && acc.getOwner().equals(company)) {
                        issuerAcc = acc;
                        break;
                    }
                }

                if (issuerAcc == null) {
                    System.out.println("No business account found for VAT " + issuerVat);
                    continue;
                }
                System.out.println("Loaded: " + bill.getPaymentCode());
                bill.setIssuer(issuerAcc);
                bills.add(bill);
                System.out.println("✅ Added bill: " + bill.getPaymentCode());

            }
        }
    }

    // === Πληρωμή ===
    public void payBills(String rfCode, Account from, User transactor) {
        Bill bill = getBillByRF(rfCode);

        if (bill == null) {
            System.out.println("Bill not found.");
            return;
        }

        if (bill.isPaid()) {
            System.out.println("The bill is already paid.");
        } else {
            TransactionManager transactionManager = new TransactionManager();
            Account businessAccount = bill.getIssuer();
            Payment payment = new Payment(bill, from, businessAccount, transactor);
            transactionManager.execute(payment);
            bill.setPaid(true);

            // Ενημέρωση αρχείων
            // ✅ Ανανέωση issued.csv
            updateIssuedCsv(bill);

            // ✅ Προσθήκη στο paid.csv
            appendToPaidCsv(bill);



            System.out.println("The bill is now paid.");
        }
    }
    public void appendToPaidCsv(Bill paidBill) {
        storage.save(paidBill, paidPath, true); // append = true
    }


    public List<Bill> getAllBills() {
        return new ArrayList<>(bills);
    }

        // === Ανάκτηση ===
    public Bill getBillByRF(String rfCode) {
        for (Bill bill : bills) {
            if (bill.getPaymentCode().equals(rfCode)) {
                return bill;
            }
        }
        return null;
    }

    private String issuerVat;
    private String customerVat;

    public String getIssuerVat() {
        return issuerVat;
    }
    public String getCustomerVat() {
        return customerVat;
    }

}
