package com.example.foodgocustomer.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodgocustomer.R;
import com.example.foodgocustomer.View.Adapter.OrdersPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class FragmentHistory extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        tabLayout = view.findViewById(R.id.tabLayoutOrders);
        viewPager = view.findViewById(R.id.viewPagerOrders);

        viewPager.setAdapter(new OrdersPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Đang đến");
                            break;
                        case 1:
                            tab.setText("Lịch sử");
                            break;
                        case 2:
                            tab.setText("Đánh giá");
                            break;
                        case 3:
                            tab.setText("Đơn nháp");
                            break;
                    }
                }
        ).attach();
        return view;
    }
}