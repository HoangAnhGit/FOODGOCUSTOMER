package com.example.foodgocustomer.View.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

// 1. Sửa import (bỏ Order, thêm ItemOrderHistoryDto)
import com.example.foodgocustomer.R;
import com.example.foodgocustomer.network.DTO.ItemOrderHistoryDto;
import com.example.foodgocustomer.databinding.ItemOrderHistoryBinding;

import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private final List<ItemOrderHistoryDto> orderList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ItemOrderHistoryDto order);
    }

    public OrderHistoryAdapter(List<ItemOrderHistoryDto> orderList, OnItemClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderHistoryBinding binding = ItemOrderHistoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        ItemOrderHistoryDto order = orderList.get(position);
        holder.bind(order, listener);
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrderHistoryBinding binding;

        public OrderViewHolder(ItemOrderHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void bind(ItemOrderHistoryDto order, OnItemClickListener listener) {
            binding.tvRestaurantName.setText(order.getRestaurantName());
            binding.tvOrderSummary.setText(order.getOrderSummary());


            String status = order.getOrderStatus();
            binding.tvOrderStatus.setText(status);


            binding.tvOrderDate.setText(formatDate(order.getOrderDate()));

            binding.tvTotalPrice.setText(formatCurrency(order.getTotalPrice()));

            binding.getRoot().setOnClickListener(v -> listener.onItemClick(order));
        }

        private String formatCurrency(double price) {
            Locale locale = new Locale("vi", "VN");
            NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
            return formatter.format(price);
        }


        private String formatDate(String isoDateString) {
            try {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoDateString);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                return zonedDateTime.format(outputFormatter);
            } catch (Exception e) {
                Log.e("OrderHistoryAdapter", "Lỗi format ngày: ", e);
                return isoDateString;
            }
        }
    }
}