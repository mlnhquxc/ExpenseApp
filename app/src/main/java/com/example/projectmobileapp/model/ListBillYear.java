package com.example.projectmobileapp.model;

import java.util.List;

public class ListBillYear implements ListBill{
    int year;
    List<Bill> list;

    public ListBillYear(int year, List<Bill> list) {
        this.year = year;
        this.list = list;
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
        return "Năm "+year;
    }

    @Override
    public List<Bill> getListBill() {
        return list;
    }
}
