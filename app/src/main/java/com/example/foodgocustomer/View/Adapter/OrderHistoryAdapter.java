package com.example.foodgocustomer.View.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgocustomer.Model.Order;
import com.example.foodgocustomer.databinding.ItemOrderHistoryBinding;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Order order);
    }

    public OrderHistoryAdapter(List<Order> orderList, OnItemClickListener listener) {
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
        Order order = orderList.get(position);
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

        public void bind(Order order, OnItemClickListener listener) {
            binding.tvRestaurantName.setText(order.getRestaurantName());
            binding.tvOrderStatus.setText(order.getStatus());
            binding.tvOrderDate.setText(order.getOrderDate());
            binding.tvTotalPrice.setText(order.getTotalPrice());
            binding.tvOrderSummary.setText(order.getOrderSummary());

            // Bắt sự kiện click
            binding.getRoot().setOnClickListener(v -> listener.onItemClick(order));
        }
    }
}
