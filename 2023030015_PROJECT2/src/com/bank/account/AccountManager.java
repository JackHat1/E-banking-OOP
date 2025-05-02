package com.account;

import com.storage.CSVManager;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private List<Account> accounts;

    public AccountManager() {
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        if (findByIBAN(account.getIban()) == null) {
            accounts.add(account);
        } else {
            System.out.println("Account with this IBAN already exists.");
        }
    }

    public Account findByIBAN(String iban) {
        for (Account acc : accounts) {
            if (acc.getIban().equals(iban)) {
                return acc;
            }
        }
        return null;
    }

    public List<Account> findByOwner(String ownerId) {
        List<Account> result = new ArrayList<>();
        for (Account acc : accounts) {
            if (acc.getOwnerId().equals(ownerId)) {
                result.add(acc);
            }
        }
        return result;
    }

    public void saveToCSV(String path) {
        CSVManager.writeCSV(path, toCSVList());
    }

    public void loadFromCSV(String path) {
        List<String[]> rows = CSVManager.readCSV(path);
        for (String[] row : rows) {
            String type = row[0];
            String iban = row[1];
            String owner = row[2];
            double balance = Double.parseDouble(row[3]);

            Account acc;
            if (type.equals("personal")) {
                acc = new PersonalAccount(iban, owner);
            } else {
                acc = new BusinessAccount(iban, owner);
            }
            acc.deposit(balance);
            addAccount(acc);
        }
    }

    private List<String[]> toCSVList() {
        List<String[]> list = new ArrayList<>();
        for (Account acc : accounts) {
            list.add(acc.toCSV().split(","));
        }
        return list;
    }

    public void printAllAccounts() {
        for (Account acc : accounts) {
            System.out.println(acc);
        }
    }
}
