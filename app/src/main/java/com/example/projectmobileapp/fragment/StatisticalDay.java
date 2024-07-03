package com.example.projectmobileapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobileapp.DatabaseHelper;
import com.example.projectmobileapp.R;
import com.example.projectmobileapp.adapter.StatisticalListDateAdapter;
import com.example.projectmobileapp.model.ListTransaction;
import com.example.projectmobileapp.model.ListTransactionByDay;
import com.example.projectmobileapp.model.Transactions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatisticalDay extends Fragment {


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
        for (String dateText:databaseHelper.getTransactionDayList(username)) {
            String[] dateParts = dateText.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);
            List<Transactions> listTransaction = databaseHelper.getTransactionListByDay(username,day,month,year);
            ListTransactionByDay listTransactionByDay = new ListTransactionByDay(day,month,year,listTransaction);
            list.add(listTransactionByDay);

        }

        statisticalListDateAdapter= new StatisticalListDateAdapter(list);
        recyclerView.setAdapter(statisticalListDateAdapter);


        return view;
    }

}