package com.example.projectmobileapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projectmobileapp.fragment.HomeFragment;
import com.example.projectmobileapp.fragment.IndexReport;

public class IndexReportAdapter extends FragmentStateAdapter {
    public IndexReportAdapter(@NonNull HomeFragment fragmentActivity) {
        super(fragmentActivity);
    }

    public IndexReportAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new IndexReport(0);
            case 1: return new IndexReport(1);
            case 2: return new IndexReport(2);
            default:return new IndexReport(0);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
