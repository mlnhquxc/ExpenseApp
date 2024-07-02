package com.example.projectmobileapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobileapp.R;
import com.example.projectmobileapp.model.ListBill;

import java.util.List;

public class StatisticalListDateAdapter extends RecyclerView.Adapter<StatisticalListDateAdapter.StatisticalListDateViewHolder> {
    List<ListBill> list;

    public StatisticalListDateAdapter(List<ListBill> list) {
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
        ListBill listBill = list.get(position);
        holder.date.setText(listBill.getTextDate());
        holder.listbills.setLayoutManager(new LinearLayoutManager(holder.listbills.getContext(), LinearLayoutManager.VERTICAL, false));
        StatisticalListBillAdapter statisticalListBillAdapter = new StatisticalListBillAdapter(listBill.getListBill());
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
