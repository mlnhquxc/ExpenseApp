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

import com.example.projectmobileapp.DatabaseHelper;
import com.example.projectmobileapp.R;
import com.example.projectmobileapp.adapter.StatisticalListDateAdapter;
import com.example.projectmobileapp.model.ListTransaction;
import com.example.projectmobileapp.model.ListTransactionByDay;
import com.example.projectmobileapp.model.ListTransactionByMonth;
import com.example.projectmobileapp.model.Transactions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatisticalMonth extends Fragment {

    RecyclerView recyclerView;
    StatisticalListDateAdapter statisticalListDateAdapter;

    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistical_date, container, false);

        recyclerView = view.findViewById(R.id.listdate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(getActivity());
        String username = sharedPreferences.getString("currentUsername", "");
        List<ListTransaction> list = new ArrayList<>();
        for (String dateText:databaseHelper.getTransactionMonthList(username)) {
            String[] dateParts = dateText.split("-");
            int year = Integer.parseInt(dateParts[1]);
            int month = Integer.parseInt(dateParts[0]);
            List<Transactions> listTransaction = databaseHelper.getTransactionListByMonth(username,month,year);
            ListTransactionByMonth listTransactionByMonth = new ListTransactionByMonth(month,year,listTransaction);
            list.add(listTransactionByMonth);

        }

        statisticalListDateAdapter= new StatisticalListDateAdapter(list);
        recyclerView.setAdapter(statisticalListDateAdapter);


        return view;
    }

}