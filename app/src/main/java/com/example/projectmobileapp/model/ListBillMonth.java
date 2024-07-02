package com.example.projectmobileapp.model;

import java.util.List;

public class ListBillMonth implements ListBill{
    int month, year;
    List<Bill> list;

    public ListBillMonth(int month, int year, List<Bill> list) {
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

    public List<Bill> getList() {
        return list;
    }

    public void setList(List<Bill> list) {
        this.list = list;
    }

    @Override
    public String getTextDate() {
        return "Tháng "+month+" năm "+year;
    }

    @Override
    public List<Bill> getListBill() {
        return list;
    }
}
