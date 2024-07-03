package com.example.projectmobileapp.model;

import java.util.List;

public class ListTransactionByMonth implements ListTransaction{
    int month, year;
    List<Transactions> list;

    public ListTransactionByMonth(int month, int year, List<Transactions> list) {
        this.month = month;
        this.year = year;
        this.list = list;
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

    public List<Transactions> getList() {
        return list;
    }

    public void setList(List<Transactions> list) {
        this.list = list;
    }

    @Override
    public String getTextDate() {
        return "Tháng "+month+" năm "+year;
    }


    @Override
    public List<Transactions> getListTransaction() {
        return list;
    }
}