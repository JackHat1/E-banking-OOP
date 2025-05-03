package com.bank.model.users;

public class Individual extends Customer {

    public Individual(String username, String password, String fullName, String vat) {
        super(username, password, fullName, vat);
    }
    
    public Individual() {
        super("", "", "", "");
    }
    
    @Override
    public String getRole() {
        return "INDIVIDUAL";
    }
}
