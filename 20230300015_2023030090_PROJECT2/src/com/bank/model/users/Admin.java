package com.bank.model.users;

public class Admin extends User {

    public Admin(String username, String password, String fullName) {
        super(username, password, fullName);
    }
    
    public Admin() {
        super("", "", "");
    }
    
    @Override
    public String getRole() {
        return "ADMIN";
    }
}
