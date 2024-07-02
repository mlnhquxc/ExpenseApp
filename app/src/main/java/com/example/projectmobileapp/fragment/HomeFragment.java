package com.example.projectmobileapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectmobileapp.AddTransaction;
import com.example.projectmobileapp.MainActivity;
import com.example.projectmobileapp.R;
import com.example.projectmobileapp.adapter.IndexReportAdapter;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {
    private TextView money;
    private Button viewMoney;
    private Boolean viewMoneyAllow = true;

    TabLayout reportTabLayout;
    ViewPager2 reportViewPaper2;
    IndexReportAdapter indexReportAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        money = view.findViewById(R.id.main_money);
        viewMoney = view.findViewById(R.id.main_view_money);

        reportTabLayout = view.findViewById(R.id.main_report_tablayout);
        Button add = view.findViewById(R.id.add);
        reportViewPaper2 = view.findViewById(R.id.main_report_viewpaper2);
        indexReportAdapter = new IndexReportAdapter(getActivity());
        reportViewPaper2.setAdapter(indexReportAdapter);

        reportTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                reportViewPaper2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        reportViewPaper2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }


        });






        viewMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewMoneyAllow){
                    money.setText("******");
                    viewMoney.setBackgroundResource(R.drawable.hide);
                    viewMoneyAllow = false;
                }else {
                    money.setText("200.000 Đồng");
                    viewMoney.setBackgroundResource(R.drawable.view);
                    viewMoneyAllow = true;
                }


            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTransaction.class);
                startActivity(intent);
            }
        });
        


        return view;
    }




}