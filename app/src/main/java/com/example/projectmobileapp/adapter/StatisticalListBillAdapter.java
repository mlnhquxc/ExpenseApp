package com.example.projectmobileapp.adapter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobileapp.DatabaseHelper;
import com.example.projectmobileapp.R;
import com.example.projectmobileapp.model.TransactionGroups;
import com.example.projectmobileapp.model.Transactions;

import java.util.List;

public class StatisticalListBillAdapter extends RecyclerView.Adapter<StatisticalListBillAdapter.StatisticalListBillViewHolder> {
    List<Transactions> Listtransactions;
    

    public StatisticalListBillAdapter(List<Transactions> Listtransactions) {
        this.Listtransactions = Listtransactions;
    }

    @NonNull
    @Override
    public StatisticalListBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_statistical_list_bill,parent,false);
        return new StatisticalListBillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticalListBillViewHolder holder, int position) {
        Transactions transactions = Listtransactions.get(position);
        TransactionGroups transactionGroups = new TransactionGroups();
        for (TransactionGroups tg:holder.databaseHelper.getTransactionGroupsList()) {
            if (transactions.getGroupID() == tg.getGroupID()){
                transactionGroups = tg;
                break;
            }
        }
        

        holder.detailBillName.setText(transactionGroups.getGroupName());

        if (transactions.getTransactionType().equals("Income")){
            holder.money.setTextColor(Color.parseColor("#4CAF50"));
            holder.money.setText(String.valueOf(transactions.getAmount()));
        }else{
            holder.money.setText(String.valueOf("-"+transactions.getAmount()));
        }

    }

    @Override
    public int getItemCount() {
        return Listtransactions.size();
    }


    public class StatisticalListBillViewHolder extends RecyclerView.ViewHolder{
        TextView detailBillName, money;
        DatabaseHelper databaseHelper;

        public StatisticalListBillViewHolder(@NonNull View itemView) {
            super(itemView);
            this.detailBillName = itemView.findViewById(R.id.statisticalDetailBillName);
            this.money = itemView.findViewById(R.id.statisticalmoney);
            databaseHelper = new DatabaseHelper(itemView.getContext());;
        }
    }
}
