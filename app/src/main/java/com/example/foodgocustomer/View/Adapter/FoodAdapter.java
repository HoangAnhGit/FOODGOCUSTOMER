package com.example.foodgocustomer.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodgocustomer.databinding.ItemFoodBinding;
import com.example.foodgocustomer.network.DTO.DishResponse;

import java.text.DecimalFormat;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final Context context;
    private final List<DishResponse> foodList;
    private final OnFoodClickListener listener;

    public interface OnFoodClickListener {
        void onAddClick(DishResponse food);
        void onItemClick(DishResponse food);
    }

    public FoodAdapter(Context context, List<DishResponse> foodList, OnFoodClickListener listener) {
        this.context = context;
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
        holder.bind(food);
    }

    @Override
    public int getItemCount() {
        return foodList != null ? foodList.size() : 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private final ItemFoodBinding binding;
        private final DecimalFormat priceFormat = new DecimalFormat("#,###đ");

        public FoodViewHolder(@NonNull ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DishResponse food) {
            binding.tvFoodName.setText(food.getDishName());
            binding.tvPrice.setText(priceFormat.format(food.getPrice()));
            binding.tvRating.setText(String.format("%.1f", food.getAverageRating()));
            binding.tvSold.setText(food.getTotalSold() + "+");

            // Load ảnh món ăn
            Glide.with(context)
                    .load(food.getImageUrl())
                    .placeholder(com.example.foodgocustomer.R.drawable.avartar)
                    .into(binding.imgFood);

            // Click toàn item
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(food);
            });

            // Click nút thêm
            binding.btnAdd.setOnClickListener(v -> {
                if (listener != null) listener.onAddClick(food);
                Toast.makeText(context, "Đã thêm " + food.getDishName(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
