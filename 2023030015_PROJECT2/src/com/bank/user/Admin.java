package com.user;

public class Admin extends User {

    public Admin(String username, String password, String fullName, String email) {
        super(username, password, fullName, email);
    }

    @Override
    public String getRole() {
        return "admin";
    }

    @Override
    public String toCSV() {
        return super.toCSV(); // Admins may not need extra fields beyond User
    }
}
