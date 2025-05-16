package com.bank.manager;

import com.bank.model.accounts.Account;
import com.bank.model.statements.StatementEntry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StatementManager {

    private final String basePath = "./data/statements/";

    public StatementManager() {
        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();
    }

    public void save(Account acc, StatementEntry entry) {
        String fullPath = basePath + acc.getIban() + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true))) {
            writer.write(entry.marshal());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error in statement save: " + e.getMessage());
        }
    }

    public List<StatementEntry> load(Account acc) {
        List<StatementEntry> list = new ArrayList<>();
        String path = basePath + acc.getIban() + ".csv";
        File file = new File(path);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StatementEntry entry = StatementEntry.unmarshal(line);
                if (entry != null) list.add(entry);
            }
        } catch (IOException e) {
            System.err.println("Error in statement load: " + e.getMessage());
        }

        return list;
    }
}
