package com.example.projectmobileapp.model;

import java.util.List;

public class ListTransactionByDay implements ListTransaction{
    int day, month, year;
    List<Transactions> list;

    public ListTransactionByDay(int day, int month, int year, List<Transactions> list) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.list = list;
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


    public List<Transactions> getList() {
        return list;
    }

    public void setList(List<Transactions> list) {
        this.list = list;
    }

    @Override
    public String getTextDate() {
        return "Ngày "+day+", tháng "+month+" năm "+year;
    }

    @Override
    public List<Transactions> getListTransaction() {
        return list;
    }

    @Override
    public double getInconme() {
        double result =0.0;
        for (Transactions tr: list) {
            if (tr.getTransactionType().equals("Income"))
            {
                result += tr.getAmount();
            }
        }
        return result;
    }

    @Override
    public double getExpense() {
        double result =0.0;
        for (Transactions tr: list) {
            if (tr.getTransactionType().equals("Expense"))
            {
                result += tr.getAmount();
            }
        }
        return result;
    }


    @Override
    public double getTotal() {
        return getInconme()-getExpense();
    }
}

