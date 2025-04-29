package com.user;

public abstract class User {
    protected String username;
    protected String password;
    protected String fullName;
    protected String email;

    public User(String username, String password, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }

    // Setters
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }

    // Για αποθήκευση σε .csv
    public String toCSV() {
        return username + "," + password + "," + fullName + "," + email + "," + getRole();
    }

    // Υποχρεωτικά υλοποιείται από Admin ή Customer
    public abstract String getRole();
}
