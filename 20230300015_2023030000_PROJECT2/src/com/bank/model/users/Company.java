package com.bank.model.users;

public class Company extends Customer {

    public Company(String username, String password, String fullName, String vat) {
        super(username, password, fullName, vat);
    }

    @Override
    public String getRole() {
        return "COMPANY";
    }
}
