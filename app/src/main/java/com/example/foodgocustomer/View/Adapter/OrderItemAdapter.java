package com.example.foodgocustomer.View.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodgocustomer.databinding.ItemBillDishBinding; // Tự động tạo
import com.example.foodgocustomer.network.DTO.OrderItemDetailDto;
import java.text.DecimalFormat;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ItemViewHolder> {

    private final List<OrderItemDetailDto> itemList;
    private final DecimalFormat priceFormat = new DecimalFormat("#,###đ");

    public OrderItemAdapter(List<OrderItemDetailDto> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBillDishBinding binding = ItemBillDishBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public void updateData(List<OrderItemDetailDto> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemBillDishBinding binding;

        public ItemViewHolder(ItemBillDishBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OrderItemDetailDto item) {
            String quantityXName = item.getQuantity() + " x " + item.getDishName();
            binding.tvQuantityXName.setText(quantityXName);

            double totalPrice = item.getPriceAtOrder() * item.getQuantity();
            binding.tvItemPrice.setText(new DecimalFormat("#,###đ").format(totalPrice));
        }
    }
}