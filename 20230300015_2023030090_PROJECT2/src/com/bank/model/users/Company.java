package com.bank.model.users;

public class Company extends Customer {
    public Company() {
        super("", "", "", "");
    }

    public Company(String username, String password, String fullName, String vat) {
        super(username, password, fullName, vat);
    }

    @Override
    public String getRole() {
        return "Company";
    }

    @Override
    public String marshal() {
        return "type:Company"
             + ",legalName:" + fullName
             + ",userName:" + username
             + ",password:" + password
             + ",vatNumber:" + vat;
    }

    @Override
    public void unmarshal(String line) {
        String[] parts = line.split(",");
        for (String part : parts) {
            String[] kv = part.split(":");
            switch (kv[0]) {
                case "legalName": fullName = kv[1]; break;
                case "userName": username = kv[1]; break;
                case "password": password = kv[1]; break;
                case "vatNumber": vat = kv[1]; break;
            }
        }
    }
}
