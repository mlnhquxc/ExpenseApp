package com.example.projectmobileapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projectmobileapp.fragment.AddTransactionGroup;
import com.example.projectmobileapp.fragment.IndexReport;

public class AddTransactionAdapter extends FragmentStateAdapter {

    public AddTransactionAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new AddTransactionGroup(true);
            case 1: return new AddTransactionGroup(false);
            default:return new AddTransactionGroup(true);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
