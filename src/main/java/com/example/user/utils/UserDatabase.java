package com.example.user.utils;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {

    // User model that holds username, password, full name, and email
    public static class User {
        private final String username;
        private String password;
        private String fullName;
        private String email;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
            this.fullName = "";
            this.email = "";
        }

        // Getters and setters
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    // Fake database using a map
    private static final Map<String, User> users = new HashMap<>();

    public static void addUser(String username, String password) {
        users.put(username, new User(username, password));
    }

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    public static boolean validateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).getPassword().equals(password);
    }

    public static User getUser(String username) {
        return users.get(username);
    }
}
