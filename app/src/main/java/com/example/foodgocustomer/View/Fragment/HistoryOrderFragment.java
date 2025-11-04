package com.example.foodgocustomer.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodgocustomer.Model.Order;
import com.example.foodgocustomer.R;
import com.example.foodgocustomer.View.Adapter.OrderHistoryAdapter;
import com.example.foodgocustomer.databinding.FragmentHistoryOrderBinding;

import java.util.ArrayList;
import java.util.List;


public class HistoryOrderFragment extends Fragment {


    private FragmentHistoryOrderBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryOrderBinding.inflate(inflater,container, false);


        RecyclerView recyclerView =binding.rcvOrderHistory;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        List<Order> orders = new ArrayList<>();
        orders.add(new Order("Cơm Tấm A Phủ", "Đã giao", "02/11/2025", "65.000đ", "Cơm tấm, sườn, bì, chả"));
        orders.add(new Order("Bún Đậu Mắm Tôm", "Đang giao", "03/11/2025", "90.000đ", "Bún đậu, nem chua, trà đá"));
        orders.add(new Order("Pizza Hut", "Chờ xác nhận", "04/11/2025", "230.000đ", "Pizza phô mai, coca"));
        orders.add(new Order("Cơm Tấm A Phủ", "Đã giao", "02/11/2025", "65.000đ", "Cơm tấm, sườn, bì, chả"));
        orders.add(new Order("Bún Đậu Mắm Tôm", "Đang giao", "03/11/2025", "90.000đ", "Bún đậu, nem chua, trà đá"));
        orders.add(new Order("Pizza Hut", "Chờ xác nhận", "04/11/2025", "230.000đ", "Pizza phô mai, coca"));
        orders.add(new Order("Cơm Tấm A Phủ", "Đã giao", "02/11/2025", "65.000đ", "Cơm tấm, sườn, bì, chả"));
        orders.add(new Order("Bún Đậu Mắm Tôm", "Đã hủy", "03/11/2025", "90.000đ", "Bún đậu, nem chua, trà đá"));
        orders.add(new Order("Pizza Hut", "Chờ xác nhận", "04/11/2025", "230.000đ", "Pizza phô mai, coca"));

        OrderHistoryAdapter adapter = new OrderHistoryAdapter(orders, order -> {
            Toast.makeText(this.getContext(), "Chọn: " + order.getRestaurantName(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }
}