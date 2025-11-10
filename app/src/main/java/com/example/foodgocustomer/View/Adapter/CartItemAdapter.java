package com.example.foodgocustomer.View.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgocustomer.Model.CartItem;
import com.example.foodgocustomer.databinding.ItemCartDishBinding;

import java.text.DecimalFormat;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartViewHolder> {

    private final List<CartItem> cartItems;
    private final OnCartItemChangeListener listener;
    private final DecimalFormat priceFormat = new DecimalFormat("#,###đ");

    public interface OnCartItemChangeListener {
        void onIncrease(CartItem item);
        void onDecrease(CartItem item);
    }

    public CartItemAdapter(List<CartItem> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartDishBinding binding = ItemCartDishBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(cartItems.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateData(List<CartItem> newList) {
        cartItems.clear();
        cartItems.addAll(newList);
        notifyDataSetChanged();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private final ItemCartDishBinding binding;

        public CartViewHolder(ItemCartDishBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CartItem item, OnCartItemChangeListener listener) {
            String quantityText = item.getQuantity() + " x";
            binding.tvQuantity.setText(quantityText);
            binding.tvDishName.setText(item.getDish().getDishName());

            double totalPrice = item.getQuantity() * item.getDish().getPrice();
            binding.tvDishPrice.setText(new DecimalFormat("#,###đ").format(totalPrice));

            binding.btnIncrease.setOnClickListener(v -> listener.onIncrease(item));
            binding.btnDecrease.setOnClickListener(v -> listener.onDecrease(item));
        }
    }
}