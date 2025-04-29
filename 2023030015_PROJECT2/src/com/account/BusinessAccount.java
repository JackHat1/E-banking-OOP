package com.account;

public class BusinessAccount extends Account {

    public BusinessAccount(String iban, String ownerId) {
        super(iban, ownerId);
    }

    @Override
    public String toString() {
        return "[Business] " + super.toString();
    }

    @Override
    public String toCSV() {
        return "business," + super.toCSV(); // Χρησιμοποιείται στο CSV για αναγνώριση τύπου
    }
}
