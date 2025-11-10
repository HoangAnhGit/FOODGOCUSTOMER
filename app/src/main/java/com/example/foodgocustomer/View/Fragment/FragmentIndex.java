package com.example.foodgocustomer.View.Fragment;

import android.content.Intent; // <-- Thêm import
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodgocustomer.R;
import com.example.foodgocustomer.View.Activity.RestaurantDetailActivity; // <-- Thêm import
import com.example.foodgocustomer.View.Adapter.BannerAdapter;
import com.example.foodgocustomer.View.Adapter.FoodAdapter;
import com.example.foodgocustomer.View.Adapter.RestaurantHomeAdapter; // <-- Thêm import
import com.example.foodgocustomer.ViewModel.DishViewModel;
import com.example.foodgocustomer.ViewModel.RestaurantViewModel;
import com.example.foodgocustomer.databinding.FragmentIndexBinding;
import com.example.foodgocustomer.network.DTO.DishResponse;
import com.example.foodgocustomer.network.DTO.ItemRestaurantDto; // <-- Thêm import

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// === SỬA LẠI 1: Implement CẢ HAI listener ===
public class FragmentIndex extends Fragment
        implements FoodAdapter.OnFoodClickListener, RestaurantHomeAdapter.OnRestaurantClickListener {

    // --- Biến cho Banner ---
    private ViewPager2 bannerViewPager;
    private Handler handler = new Handler();
    private int currentPage = 0;
    private FragmentIndexBinding binding;
    private final List<Integer> bannerImages = Arrays.asList(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    );
    private final Runnable bannerRunnable = new Runnable() {
        @Override
        public void run() {
            if (bannerImages.isEmpty()) return;
            currentPage = (currentPage + 1) % bannerImages.size();
            bannerViewPager.setCurrentItem(currentPage, true);
            handler.postDelayed(this, 3000);
        }
    };

    // --- Biến cho RecyclerViews ---
    private RestaurantViewModel restaurantViewModel;
    private RestaurantHomeAdapter restaurantAdapter;
    private DishViewModel dishViewModel;
    private FoodAdapter foodAdapter;

    // --- Biến trạng thái loading ---
    private boolean isRestaurantLoading = false;
    private boolean isDishLoading = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentIndexBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Khởi tạo Banner
        setupBanner();

        // 2. Khởi tạo ViewModels
        initViewModels();

        // 3. Cài đặt RecyclerViews
        setupRecyclerViews();

        // 4. Tải dữ liệu
        loadRestaurantData();
        loadDishData();
    }

    private void setupBanner() {
        bannerViewPager = binding.bannerViewPager;
        BannerAdapter adapter = new BannerAdapter(getContext(), bannerImages);
        bannerViewPager.setAdapter(adapter);
    }

    private void initViewModels() {
        restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        dishViewModel = new ViewModelProvider(this).get(DishViewModel.class);
    }

    private void setupRecyclerViews() {
        // Cài đặt cho Nhà hàng (Vertical)
        // === SỬA LẠI 2: Truyền 'this' vào adapter ===
        restaurantAdapter = new RestaurantHomeAdapter(new ArrayList<>(), this);
        binding.rcvRestaurant.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvRestaurant.setAdapter(restaurantAdapter);
        binding.rcvRestaurant.setNestedScrollingEnabled(false);

        // Cài đặt cho Món ăn (Horizontal)
        foodAdapter = new FoodAdapter(new ArrayList<>(), this); // 'this' là OnFoodClickListener
        binding.rcvCategoryFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rcvCategoryFood.setAdapter(foodAdapter);
    }

    private void loadRestaurantData() {
        if (isRestaurantLoading) return;
        isRestaurantLoading = true;

        restaurantViewModel.fetchRestaurants(1, 10).observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                isRestaurantLoading = false;
                return;
            }

            switch (result.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    isRestaurantLoading = false;
                    if (result.data != null && result.data.getData() != null) {
                        restaurantAdapter.updateData(result.data.getData());
                    }
                    break;
                case ERROR:
                    isRestaurantLoading = false;
                    Toast.makeText(getContext(), "Lỗi tải nhà hàng: " + result.message, Toast.LENGTH_SHORT).show();
                    break;
            }
            restaurantViewModel.fetchRestaurants(1, 10).removeObservers(getViewLifecycleOwner());
        });
    }

    private void loadDishData() {
        if (isDishLoading) return;
        isDishLoading = true;

        dishViewModel.getAllDish().observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                isDishLoading = false;
                return;
            }
            switch (result.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    isDishLoading = false;
                    if (result.data != null && result.data.getData() != null) {
                        foodAdapter.updateData(result.data.getData());
                    }
                    break;
                case ERROR:
                    isDishLoading = false;
                    Toast.makeText(getContext(), "Lỗi tải món ăn: " + result.message, Toast.LENGTH_SHORT).show();
                    break;
            }
            dishViewModel.getAllDish().removeObservers(getViewLifecycleOwner());
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(bannerRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(bannerRunnable, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // --- Hàm click cho Món ăn (FoodAdapter) ---
    @Override
    public void onAddClick(DishResponse food) {
        Toast.makeText(getContext(), "Đã thêm: " + food.getDishName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(DishResponse food) {
        // Xử lý khi nhấn vào cả item
        Toast.makeText(getContext(), "Xem chi tiết: " + food.getDishName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestaurantClick(ItemRestaurantDto restaurant) {
        // Mở màn hình Chi tiết Nhà hàng
        Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);

        // Gửi ID và Tên của nhà hàng sang Activity mới
        intent.putExtra("RESTAURANT_ID", restaurant.getRestaurantId());
        intent.putExtra("RESTAURANT_NAME", restaurant.getName());
        intent.putExtra("RESTAURANT", restaurant.getAverageRating());
        intent.putExtra("RESTAURANT_ORDER_COUNT", restaurant.getCompletedOrderCount());
        intent.putExtra("RESTAURANT_REVIEW_COUNT", restaurant.getReviewCount());

        startActivity(intent);
    }
}