package com.example.projectmobileapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projectmobileapp.fragment.History;
import com.example.projectmobileapp.fragment.HomeFragment;


public class IndexViewAdapter extends FragmentStateAdapter {
    public IndexViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new History();
            default:return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
