package com.example.projectmobileapp.model;

public class TransactionGroups {
    int GroupID;
    String GroupName;
    String transactionType;

    public TransactionGroups(int groupID, String groupName, String transactionType) {
        GroupID = groupID;
        GroupName = groupName;
        this.transactionType = transactionType;
    }


    public TransactionGroups() {
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
