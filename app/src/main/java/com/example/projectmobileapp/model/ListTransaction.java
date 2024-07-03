package com.example.projectmobileapp.model;

import java.util.List;

public interface ListTransaction {
    public String getTextDate();
    public List<Transactions> getListTransaction();

    public double getInconme();
    public double getExpense();
    public double getTotal();
}


