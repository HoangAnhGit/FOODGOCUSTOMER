package com.example.foodgocustomer.View.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodgocustomer.View.Fragment.ArrivingFragment;
import com.example.foodgocustomer.View.Fragment.DraftFragment;
import com.example.foodgocustomer.View.Fragment.HistoryOrderFragment;
import com.example.foodgocustomer.View.Fragment.ReviewFragment;

public class OrdersPagerAdapter extends FragmentStateAdapter {

    public OrdersPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new ArrivingFragment();
            case 1: return new HistoryOrderFragment();
            case 2: return new ReviewFragment();
            case 3: return new DraftFragment();
            default: return new ArrivingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}