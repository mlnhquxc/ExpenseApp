package com.example.projectmobileapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectmobileapp.R;
import com.example.projectmobileapp.adapter.StatisticalListBillAdapter;
import com.example.projectmobileapp.adapter.StatisticalListDateAdapter;
import com.example.projectmobileapp.adapter.StatisticalViewDateAdapter;
import com.example.projectmobileapp.model.Bill;
import com.example.projectmobileapp.model.ListBill;
import com.example.projectmobileapp.model.ListBillDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class StatisticalDay extends Fragment {
    RecyclerView recyclerView;
    StatisticalListDateAdapter statisticalListDateAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistical_date, container, false);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2024, Calendar.JUNE, 25); // Ngày 25/06/2024
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2024, Calendar.JUNE, 26); // Ngày 26/06/2024
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2024, Calendar.JUNE, 27); // Ngày 27/06/2024

        // Tạo các đối tượng Bill
        Bill bill1 = new Bill(1, "abc", 1, 20000, calendar1.getTime(), "");
        Bill bill2 = new Bill(2, "def", 1, 30000, calendar2.getTime(), "");
        Bill bill3 = new Bill(3, "ghi", 1, 40000, calendar3.getTime(), "");

        List<Bill> bills1 = new ArrayList<>();
        List<Bill> bills2 = new ArrayList<>();
        List<Bill> bills3 = new ArrayList<>();
        bills1.add(bill1);
        bills1.add(bill2);
        bills2.add(bill1);
        bills2.add(bill3);
        bills3.add(bill2);
        bills3.add(bill3);

        ListBill listBill1 = new ListBillDay(1,1,1,bills1);
        ListBill listBill2 = new ListBillDay(2,1,1,bills2);
        ListBill listBill3 = new ListBillDay(3,1,1,bills3);

        List<ListBill> list = new ArrayList<>();
        list.add(listBill1);
        list.add(listBill2);
        list.add(listBill3);

        recyclerView = view.findViewById(R.id.listdate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        statisticalListDateAdapter= new StatisticalListDateAdapter(list);
        recyclerView.setAdapter(statisticalListDateAdapter);


        return view;
    }


}