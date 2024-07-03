package com.example.projectmobileapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobileapp.R;
import com.example.projectmobileapp.model.ListTransaction;

import java.util.List;

public class StatisticalListDateAdapter extends RecyclerView.Adapter<StatisticalListDateAdapter.StatisticalListDateViewHolder> {
    List<ListTransaction> list;

    public StatisticalListDateAdapter(List<ListTransaction> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StatisticalListDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_statistical_list_date,parent,false);
        return new StatisticalListDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticalListDateViewHolder holder, int position) {
        ListTransaction listTransaction = list.get(position);
        holder.date.setText(listTransaction.getTextDate());
        holder.listbills.setLayoutManager(new LinearLayoutManager(holder.listbills.getContext(), LinearLayoutManager.VERTICAL, false));
        StatisticalListBillAdapter statisticalListBillAdapter = new StatisticalListBillAdapter(listTransaction.getListTransaction());
        holder.listbills.setAdapter(statisticalListBillAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class StatisticalListDateViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        RecyclerView listbills;

        public StatisticalListDateViewHolder(@NonNull View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.statisticalTextDate);
            this.listbills = itemView.findViewById(R.id.statisticalListBill);
        }
    }
}
