package com.example.projectmobileapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectmobileapp.R;
import com.example.projectmobileapp.adapter.StatisticalViewDateAdapter;
import com.google.android.material.tabs.TabLayout;


public class History extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    StatisticalViewDateAdapter statisticalViewDateAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_statistical, container, false);

        tabLayout = view.findViewById(R.id.statisticalTypeDate);
        viewPager2 = view.findViewById(R.id.viewdate);
        statisticalViewDateAdapter = new StatisticalViewDateAdapter(getActivity());
        viewPager2.setAdapter(statisticalViewDateAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        return view;
    }
}