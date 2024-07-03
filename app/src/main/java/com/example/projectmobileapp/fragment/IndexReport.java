package com.example.projectmobileapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectmobileapp.DatabaseHelper;
import com.example.projectmobileapp.R;
import com.example.projectmobileapp.adapter.StatisticalListDateAdapter;
import com.example.projectmobileapp.model.ListTransaction;
import com.example.projectmobileapp.model.ListTransactionByDay;
import com.example.projectmobileapp.model.Transactions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class IndexReport extends Fragment {

    TextView thunhap,chitieu,tongcong;
    RecyclerView listbill;
    int date;
    StatisticalListDateAdapter statisticalListDateAdapter;
    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;

    public IndexReport(int date) {
        this.date = date;
    }


    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index_report, container, false);
        thunhap = view.findViewById(R.id.index_reprot_income);
        chitieu = view.findViewById(R.id.index_reprot_expenses);
        tongcong = view.findViewById(R.id.index_reprot_total);
        sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(getActivity());

        listbill = view.findViewById(R.id.index_report_listbill);
        listbill.setLayoutManager(new LinearLayoutManager(getActivity()));
        listbill.setNestedScrollingEnabled(false);

        List<ListTransaction> list= new ArrayList<>();
        String username = sharedPreferences.getString("currentUsername", "");
//        switch (date) {
//            case 0:
//
//                break;
//            case 1:
//
//                break;
//            case 2:
//
//                break;
//            default:
//
//                break;
//        }
        for (String dateText:databaseHelper.getTransactionDayList(username)) {
            String[] dateParts = dateText.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);
            List<Transactions> listTransaction = databaseHelper.getTransactionListByDay(username,day,month,year);
            ListTransactionByDay listTransactionByDay = new ListTransactionByDay(day,month,year,listTransaction);
            list.add(listTransactionByDay);

        }

        statisticalListDateAdapter = new StatisticalListDateAdapter(list);

        listbill.setAdapter(statisticalListDateAdapter);

        listbill.post(new Runnable() {
            @Override
            public void run() {
                setRecyclerViewHeightBasedOnChildren(listbill);
            }
        });

        statisticalListDateAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                setRecyclerViewHeightBasedOnChildren(listbill);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                setRecyclerViewHeightBasedOnChildren(listbill);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                setRecyclerViewHeightBasedOnChildren(listbill);
            }
        });



        return view;
    }

    private void setRecyclerViewHeightBasedOnChildren(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
            adapter.onBindViewHolder(holder, i);
            holder.itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += holder.itemView.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = totalHeight + (recyclerView.getItemDecorationCount() * (adapter.getItemCount() - 1));
        recyclerView.setLayoutParams(params);
        recyclerView.requestLayout();

    }
}