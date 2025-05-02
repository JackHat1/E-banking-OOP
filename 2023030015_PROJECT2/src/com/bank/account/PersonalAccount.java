package com.account;

public class PersonalAccount extends Account {

    public PersonalAccount(String iban, String ownerId) {
        super(iban, ownerId);
    }

    @Override
    public String toString() {
        return "[Personal] " + super.toString();
    }

    @Override
    public String toCSV() {
        return "personal," + super.toCSV(); // Προσθέτουμε τον τύπο για αναγνώριση κατά την ανάγνωση αρχείου
    }
}
