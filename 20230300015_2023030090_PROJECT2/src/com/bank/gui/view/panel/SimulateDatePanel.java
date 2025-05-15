package com.bank.gui.view.panel;

import com.bank.manager.BillManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SimulateDatePanel extends JPanel {

    private LocalDate currentDate = LocalDate.of(2025, 5, 1); // αρχική
    private final JLabel dateLabel;
    private final BillManager billManager = new BillManager();

    public SimulateDatePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("🗓 Προώθηση Ημερομηνίας", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0; gbc.gridy = 0;

        dateLabel = new JLabel("📅 Σήμερα: " + currentDate);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        center.add(dateLabel, gbc);

        JButton advanceBtn = new JButton("⏭ Προώθηση Ημέρας");
        advanceBtn.setBackground(new Color(0, 100, 160));
        advanceBtn.setForeground(Color.WHITE);
        advanceBtn.setFocusPainted(false);

        gbc.gridy++;
        center.add(advanceBtn, gbc);

        add(center, BorderLayout.CENTER);

        advanceBtn.addActionListener(e -> simulateNextDay());
    }

    private void simulateNextDay() {
        currentDate = currentDate.plusDays(1);
        dateLabel.setText("📅 Σήμερα: " + currentDate);

        String filename = "./data/bills/" + currentDate.format(DateTimeFormatter.ISO_DATE) + ".csv";
        File file = new File(filename);

        if (file.exists()) {
            // Αν υλοποιηθεί το loadDailyBills
            // billManager.loadDailyBills(filename);
            JOptionPane.showMessageDialog(this, "📥 Φόρτωση λογαριασμών από: " + file.getName());
        } else {
            JOptionPane.showMessageDialog(this, "ℹ️ Δεν υπάρχουν λογαριασμοί για " + currentDate);
        }
    }
}
