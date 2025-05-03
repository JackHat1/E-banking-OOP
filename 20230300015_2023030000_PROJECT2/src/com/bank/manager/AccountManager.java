package com.bank.manager;

import com.bank.model.accounts.*;
import com.bank.model.users.*;
import com.bank.storage.CsvStorageManager;
import com.bank.storage.Storable;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {

    private List<Account> accounts = new ArrayList<>();
    private final String filePath = "./data/accounts/accounts.csv";
    private CsvStorageManager storage = new CsvStorageManager();

    private UserManager userManager;

    public AccountManager(UserManager userManager) {
        this.userManager = userManager;
    }

    // Φόρτωση από CSV
    public void load() {
        List<String> lines = storage.loadLines(filePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            String typeCode = parts[0]; // "100" ή "200"
            String iban = parts[1];
            String vat = parts[2];

            Customer owner = userManager.findByVat(vat);

            Account acc = null;

            if (typeCode.equals("100")) {
                acc = new PersonalAccount((Individual) owner, 0);
            } else if (typeCode.equals("200")) {
                acc = new BusinessAccount((Company) owner, 0, 0);
            }

            if (acc != null) {
                acc.unmarshal(line);
                accounts.add(acc);
            }
        }
    }

    // Αποθήκευση όλων των λογαριασμών
    public void saveAll() {
        List<Storable> list = new ArrayList<>();
        for (Account acc : accounts) {
            list.add(acc);
        }
        storage.saveAll(list, filePath);
    }

    // Εύρεση με IBAN
    public Account findByIban(String iban) {
        for (Account acc : accounts) {
            if (acc.getIban().equals(iban)) {
                return acc;
            }
        }
        return null;
    }

    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }
}
