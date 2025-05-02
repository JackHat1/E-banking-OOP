package com.user;

import com.storage.Storable;
import com.storage.CSVManager;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private final String USERS_FILE = "data/users/users.csv";

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void saveUsers() {
        List<Storable> storableUsers = new ArrayList<>();
        storableUsers.addAll(users);
        CSVManager.writeToCSV(USERS_FILE, storableUsers);
    }

    private void loadUsers() {
        List<String[]> records = CSVManager.readFromCSV(USERS_FILE);
        for (String[] data : records) {
            if (data.length >= 5) {
                String role = data[0];
                String username = data[1];
                String password = data[2];
                String fullName = data[3];
                String email = data[4];

                if (role.equals("admin")) {
                    users.add(new Admin(username, password, fullName, email));
                } else if (role.equals("customer")) {
                    users.add(new Customer(username, password, fullName, email));
                }
            }
        }
    }

    public List<User> getAllUsers() {
        return users;
    }
}
