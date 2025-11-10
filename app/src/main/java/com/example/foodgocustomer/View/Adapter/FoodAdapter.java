package com.example.foodgocustomer.View.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // <-- Import thư viện Glide
import com.example.foodgocustomer.R; // <-- Import R
import com.example.foodgocustomer.databinding.ItemFoodBinding; // <-- Binding cho item_food.xml
import com.example.foodgocustomer.network.DTO.DishResponse;

import java.text.DecimalFormat;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final List<DishResponse> foodList;
    private final OnFoodClickListener listener;

    public interface OnFoodClickListener {
        void onAddClick(DishResponse food);
        void onItemClick(DishResponse food);
    }

    public FoodAdapter(List<DishResponse> foodList, OnFoodClickListener listener) {
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new FoodViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        DishResponse food = foodList.get(position);
        holder.bind(food, listener); // Truyền cả listener vào hàm bind
    }

    @Override
    public int getItemCount() {
        return foodList != null ? foodList.size() : 0;
    }

    public void updateData(List<DishResponse> newList) {
        foodList.clear();
        foodList.addAll(newList);
        notifyDataSetChanged();
    }


    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        private final ItemFoodBinding binding;
        private final DecimalFormat priceFormat = new DecimalFormat("#,###đ");

        public FoodViewHolder(@NonNull ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DishResponse food, OnFoodClickListener listener) {
            binding.tvFoodName.setText(food.getDishName());
            binding.tvPrice.setText(priceFormat.format(food.getPrice()));

            binding.tvRating.setText(String.format("%.1f (%d)", food.getAverageRating(), food.getRatingCount()));
            binding.tvSold.setText(food.getTotalSold() + "+");

            Glide.with(binding.getRoot().getContext())
                    .load(food.getImageUrl())
                    .placeholder(R.drawable.avartar)
                    .error(R.drawable.avartar)
                    .into(binding.imgFood);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(food);
            });

            binding.btnAdd.setOnClickListener(v -> {
                if (listener != null) listener.onAddClick(food);

            });
        }
    }
}