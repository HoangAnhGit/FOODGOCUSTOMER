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

    /**
     * Interface để xử lý sự kiện click từ Fragment
     */
    public interface OnFoodClickListener {
        void onAddClick(DishResponse food);
        void onItemClick(DishResponse food);
    }

    /**
     * Constructor (đã bỏ Context)
     */
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

    /**
     * Hàm để cập nhật dữ liệu từ Fragment
     */
    public void updateData(List<DishResponse> newList) {
        foodList.clear();
        foodList.addAll(newList);
        notifyDataSetChanged();
    }

    /**
     * Lớp ViewHolder (nên để là static)
     */
    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        private final ItemFoodBinding binding;
        private final DecimalFormat priceFormat = new DecimalFormat("#,###đ");

        public FoodViewHolder(@NonNull ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Hàm gán dữ liệu (DTO) vào View (XML)
         */
        public void bind(DishResponse food, OnFoodClickListener listener) {
            // Gán dữ liệu text
            binding.tvFoodName.setText(food.getDishName());
            binding.tvPrice.setText(priceFormat.format(food.getPrice()));

            // Dữ liệu này lấy từ API C# bạn gửi
            binding.tvRating.setText(String.format("%.1f (%d)", food.getAverageRating(), food.getRatingCount()));
            binding.tvSold.setText(food.getTotalSold() + "+");

            // Tải ảnh món ăn bằng Glide
            Glide.with(binding.getRoot().getContext()) // Lấy Context từ View
                    .load(food.getImageUrl()) // URL ảnh
                    .placeholder(R.drawable.avartar) // Ảnh chờ
                    .error(R.drawable.avartar) // Ảnh lỗi
                    .into(binding.imgFood); // ImageView

            // Gán sự kiện click cho toàn bộ item
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(food);
            });

            // Gán sự kiện click cho nút "Thêm"
            binding.btnAdd.setOnClickListener(v -> {
                if (listener != null) listener.onAddClick(food);
                // Toast đã được chuyển về Fragment (nơi implement)
                // nhưng để tạm ở đây cũng không sao
            });
        }
    }
}