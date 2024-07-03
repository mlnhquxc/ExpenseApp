package com.example.projectmobileapp.model;

public class User {
    private String email;
    private String password;
    private String username;
    private double Balance;

    public User(String email, String password, String username, double Balance) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.Balance = Balance;
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

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public double getCash() {
        return Balance;
    }

    public void setCash(double Balance) {
        this.Balance = Balance;
    }
}
