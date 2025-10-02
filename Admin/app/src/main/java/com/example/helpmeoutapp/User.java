package com.example.helpmeoutapp;

public class User {
    public String username, email, password, contact;

    public User() {
    }

    public User(String username, String email, String contact, String password) {
        this.username = username;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
