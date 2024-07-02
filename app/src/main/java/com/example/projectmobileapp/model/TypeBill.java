package com.example.projectmobileapp.model;

public class TypeBill {
    int typeBillId;
    String name;

    public TypeBill(int typeBillId, String name) {
        this.typeBillId = typeBillId;
        this.name = name;
    }

    public int getTypeBillId() {
        return typeBillId;
    }

    public void setTypeBillId(int typeBillId) {
        this.typeBillId = typeBillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
