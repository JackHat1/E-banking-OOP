package com.bank.manager;

import com.bank.model.accounts.Account;
import com.bank.model.statements.StatementEntry;
import com.bank.storage.CsvStorageManager;

import java.io.*;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatementManager {

    private final String basePath = "./data/statements/";
    private final CsvStorageManager storage= new CsvStorageManager();

    public StatementManager() {
        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();
    }

    public void saveStatement(Account acc, StatementEntry entry) {
        String fullPath = basePath + acc.getIban() + ".csv";

        storage.save(entry,fullPath);

        /*try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true))) {
            writer.write(entry.marshal());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error in statement save: " + e.getMessage());
        }*/
    }

    public List<StatementEntry> load(Account acc) {

        String path = basePath + acc.getIban() + ".csv";
        List<StatementEntry> list = new ArrayList<>();
        List<String> lines= storage.loadLines(path);

        for(String line: lines){
            StatementEntry entry= new StatementEntry();
            
            entry.unmarshal(line);
            list.add(entry);
        }

        Collections.reverse(list); // gia na tis kanei anapoda poy leei h ekfonisi
        return list;

        /*File file = new File(path);
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

        return list;*/
    }
}
