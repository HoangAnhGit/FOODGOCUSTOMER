package com.example.foodgocustomer.View.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgocustomer.databinding.ItemRestaurantBinding;
import com.example.foodgocustomer.network.DTO.ItemRestaurantDto;

import java.util.List;
import java.util.Locale;

public class RestaurantHomeAdapter extends RecyclerView.Adapter<RestaurantHomeAdapter.RestaurantViewHolder> {


    private final List<ItemRestaurantDto> restaurantList;

    private OnRestaurantClickListener listener;


    public interface OnRestaurantClickListener {
        void onRestaurantClick(ItemRestaurantDto restaurant);
    }
    public RestaurantHomeAdapter(List<ItemRestaurantDto> restaurantList, OnRestaurantClickListener listener) {
        this.restaurantList = restaurantList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRestaurantBinding binding = ItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        ItemRestaurantDto restaurant = restaurantList.get(position);
        holder.bind(restaurant, listener);
    }

    @Override
    public int getItemCount() {
        return restaurantList != null ? restaurantList.size() : 0;
    }

    public void updateData(List<ItemRestaurantDto> newList) {
        restaurantList.clear();
        restaurantList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final ItemRestaurantBinding binding;

        public RestaurantViewHolder(@NonNull ItemRestaurantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ItemRestaurantDto restaurant, OnRestaurantClickListener listener) {
            binding.tvRestaurantName.setText(restaurant.getName());

            binding.tvRating.setText(String.format(Locale.US, "%.1f (%d)",
                    restaurant.getAverageRating(),
                    restaurant.getReviewCount()));

            binding.tvCompletedOrders.setText(String.valueOf(restaurant.getCompletedOrderCount()));

            binding.tvDistance.setText(String.format(Locale.US, "%.1fkm",
                    restaurant.getDistanceInKm()));

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRestaurantClick(restaurant);
                }
            });
        }
    }
}