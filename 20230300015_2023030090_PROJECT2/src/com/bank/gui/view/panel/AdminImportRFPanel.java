package com.bank.gui.view.panel;

import com.bank.manager.BillManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AdminImportRFPanel extends JPanel {

    private final BillManager billManager = new BillManager();
    private final JLabel statusLabel;

    public AdminImportRFPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("📥 Εισαγωγή RF Λογαριασμών", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.WHITE);
        JButton importBtn = new JButton("📂 Επιλογή Αρχείου CSV");
        importBtn.setBackground(new Color(0, 102, 180));
        importBtn.setForeground(Color.WHITE);
        importBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusLabel.setForeground(Color.DARK_GRAY);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        center.add(importBtn);
        add(center, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        importBtn.addActionListener(e -> loadBillsFromFile());
    }
    private void loadBillsFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Επιλέξτε αρχείο RF (CSV)");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                // int count = billManager.loadDailyBills(selectedFile.getAbsolutePath());
                // statusLabel.setText("✅ Φορτώθηκαν " + count + " λογαριασμοί από " + selectedFile.getName());
                statusLabel.setText("⚠️ Η εισαγωγή RF δεν έχει υλοποιηθεί ακόμα.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Σφάλμα κατά την εισαγωγή: " + ex.getMessage());
                statusLabel.setText("❌ Το αρχείο δεν φορτώθηκε.");
            }
        }
    }
}

