package com.example.projectmobileapp.model;

public class Transactions {

    int TransactionID;
    String Username;
    String TransactionType;
    int GroupID;
    double Amount;
    String Notes;
    int day,month,year;


    public Transactions(int transactionID, String username, String transactionType, int groupID, double amount, String notes, int day, int month, int year) {
        TransactionID = transactionID;
        Username = username;
        TransactionType = transactionType;
        GroupID = groupID;
        Amount = amount;
        Notes = notes;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(int transactionID) {
        TransactionID = transactionID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }
}
