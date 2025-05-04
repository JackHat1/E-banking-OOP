package com.bank.manager;

import com.bank.model.accounts.*;
import com.bank.model.users.*;
import com.bank.storage.CsvStorageManager;
import com.bank.storage.Storable;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {

    private List<Account> accounts = new ArrayList<>();
    private final String path = "./data/accounts/accounts.csv";
    private CsvStorageManager storage = new CsvStorageManager();

    private UserManager userManager;

    public AccountManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void load() {
        List<String> lines = storage.loadLines(path);
    
        for (String line : lines) {
            String[] fields = line.split(",");
            String type = "", iban = "", vat = "";
            double balance = 0, interest = 0;
    
            for (String field : fields) {
                String[] parts = field.split(":");
                if (parts.length < 2) continue;
    
                String key = parts[0].trim();
                String value = parts[1].trim();
    
                switch (key) {
                    case "type": type = value.toUpperCase(); break;
                    case "iban": iban = value; break;
                    case "primaryOwner": vat = value; break;
                    case "balance": balance = Double.parseDouble(value); break;
                    case "rate": interest = Double.parseDouble(value); break;
                    // ignore: dateCreated, coOwner
                }
            }
    
            Customer owner = userManager.findByVat(vat);
            Account acc = null;
    
            if (type.equals("PERSONALACCOUNT") && owner instanceof Individual) {
                acc = new PersonalAccount((Individual) owner, interest);
            } else if (type.equals("BUSINESSACCOUNT") && owner instanceof Company) {
                acc = new BusinessAccount((Company) owner, interest, 0); // προσωρινά fee = 0
            }
    
            if (acc != null) {
                acc.setIban(iban);
                acc.deposit(balance);
                accounts.add(acc);
            //  System.out.println("✔ Προστέθηκε: " + iban + " ➝ " + owner.getFullName());
            }
        }
    }
    

    public void saveAll() {
        List<Storable> list = new ArrayList<>();
        for (Account acc : accounts) {
            list.add(acc);
        }
        storage.saveAll(list, path, false);
    }

    public Account findByIban(String iban) {
        for (Account acc : accounts) {
            if (acc.getIban().equals(iban)) {
                return acc;
            }
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public void addAccount(Account acc) {
        accounts.add(acc);
    }
}
