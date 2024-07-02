package com.example.projectmobileapp.model;

import java.util.Date;
import java.util.List;

public class ListBillDay implements ListBill{


    int day, month, year;
    List<Bill> list;

    public ListBillDay(int day, int month, int year, List<Bill> list) {
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

    public List<Bill> getList() {
        return list;
    }

    public void setList(List<Bill> list) {
        this.list = list;
    }
    @Override
    public String getTextDate() {
        return "Ngày "+day+", tháng "+month+" năm "+year;
    }

    @Override
    public List<Bill> getListBill() {
        return list;
    }
}
