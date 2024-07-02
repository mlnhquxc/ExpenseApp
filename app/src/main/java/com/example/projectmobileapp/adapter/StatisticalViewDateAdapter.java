package com.example.projectmobileapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projectmobileapp.fragment.StatisticalDay;
import com.example.projectmobileapp.fragment.StatisticalMonth;
import com.example.projectmobileapp.fragment.StatisticalYear;

public class StatisticalViewDateAdapter extends FragmentStateAdapter {

    public StatisticalViewDateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new StatisticalDay();
            case 1:
                return new StatisticalMonth();
            case 2:
                return new StatisticalYear();
            default:
                return new StatisticalDay();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
