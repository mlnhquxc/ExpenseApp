package com.example.projectmobileapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projectmobileapp.adapter.AddTransactionAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.List;

public class AddTransaction extends AppCompatActivity{

    TabLayout incomeexpenses;
    ViewPager2 transactionGroup;
    AddTransactionAdapter addTransactionAdapter;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_transaction);

        incomeexpenses = findViewById(R.id.incomeexpenses);
        transactionGroup = findViewById(R.id.transactiongroup);
        addTransactionAdapter = new AddTransactionAdapter(this);
        transactionGroup.setAdapter(addTransactionAdapter);

        incomeexpenses.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                transactionGroup.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        transactionGroup.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

    }



}