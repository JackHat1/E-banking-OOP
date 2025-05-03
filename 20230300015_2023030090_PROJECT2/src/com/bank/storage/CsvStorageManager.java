package com.bank.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvStorageManager {

    // Αποθήκευση ενός αντικειμένου
    public void save(Storable s, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(s.marshal());
        } catch (IOException e) {
            System.out.println("Δεν έγινε αποθήκευση: " + filePath);
        }
    }

    // Αποθήκευση λίστας αντικειμένων (overwrite)
// ➤ Κύρια μέθοδος με append επιλογή
    public void saveAll(List<? extends Storable> items, String filePath, boolean append) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, append))) {
            for (Storable s : items) {
                writer.println(s.marshal());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ➤ Συντομευμένη έκδοση (default: overwrite)
    public void saveAll(List<? extends Storable> items, String filePath) {
        saveAll(items, filePath, false);
    }


    // Ανάγνωση όλων των γραμμών του αρχείου
    public List<String> loadLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("⚠️ Σφάλμα ανάγνωσης από: " + filePath);
        }
        return lines;
    }
}
