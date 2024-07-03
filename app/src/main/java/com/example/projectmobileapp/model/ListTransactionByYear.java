package com.example.projectmobileapp.model;

import java.util.List;

public class ListTransactionByYear implements ListTransaction{

    int year;
    List<Transactions> list;

    public ListTransactionByYear(int year, List<Transactions> list) {
        this.year = year;
        this.list = list;
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
        return "Năm "+year;
    }

    @Override
    public List<Transactions> getListTransaction() {
        return list;
    }

    @Override
    public double getInconme() {
        return 0;
    }

    @Override
    public double getExpense() {
        return 0;
    }

    @Override
    public double getTotal() {
        return 0;
    }
}
