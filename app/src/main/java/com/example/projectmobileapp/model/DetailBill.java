package com.example.projectmobileapp.model;

public class DetailBill {
    int detailBillId;
    String name;
    int typeBillId;

    public DetailBill(int detailBillId, String name, int typeBillId) {
        this.detailBillId = detailBillId;
        this.name = name;
        this.typeBillId = typeBillId;
    }

    public int getDetailBillId() {
        return detailBillId;
    }

    public void setDetailBillId(int detailBillId) {
        this.detailBillId = detailBillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeBillId() {
        return typeBillId;
    }

    public void setTypeBillId(int typeBillId) {
        this.typeBillId = typeBillId;
    }
}
