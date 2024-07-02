package com.example.projectmobileapp.model;

import java.util.Date;

public class Bill {
    int billId;
    String email;
    int detailBillId;
    int money;
    Date date;
    String note;

    public Bill(int billId, String email, int detailBillId, int money, Date date, String note) {
        this.billId = billId;
        this.email = email;
        this.detailBillId = detailBillId;
        this.money = money;
        this.date = date;
        this.note = note;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDetailBillId() {
        return detailBillId;
    }

    public void setDetailBillId(int detailBillId) {
        this.detailBillId = detailBillId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
