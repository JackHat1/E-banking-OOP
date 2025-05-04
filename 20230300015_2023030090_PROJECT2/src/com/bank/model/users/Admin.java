package com.bank.model.users;

public class Admin extends User {
    public Admin() {
        super("", "", "");
    }

    public Admin(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public String marshal() {
        return "type:Admin"
             + ",legalName:" + fullName
             + ",userName:" + username
             + ",password:" + password;
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
            }
        }
    }
}
