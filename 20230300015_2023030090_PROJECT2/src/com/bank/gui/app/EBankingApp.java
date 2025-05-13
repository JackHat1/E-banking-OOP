package com.bank.gui.app;

import com.bank.gui.controller.AppController;
import com.bank.gui.view.LoginWindow;

public class EBankingApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginWindow login = new LoginWindow();
            new AppController(login);
        });
    }

}
