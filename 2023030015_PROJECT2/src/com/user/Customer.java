package com.user;

public class Customer extends User {

    private String phoneNumber;
    private String address;

    public Customer(String username, String password, String fullName, String email, String phoneNumber, String address) {
        super(username, password, fullName, email);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getRole() {
        return "customer";
    }

    @Override
    public String toCSV() {
        return super.toCSV() + "," + phoneNumber + "," + address;
    }
}
