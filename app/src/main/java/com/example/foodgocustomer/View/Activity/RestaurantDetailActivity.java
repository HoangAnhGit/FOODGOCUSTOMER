package com.example.foodgocustomer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.foodgocustomer.R;
import com.example.foodgocustomer.Util.CartManager;
import com.example.foodgocustomer.View.Adapter.FoodAdapter;
import com.example.foodgocustomer.ViewModel.DishViewModel;
import com.example.foodgocustomer.databinding.ActivityRestaurantDetailBinding;
import com.example.foodgocustomer.network.DTO.DishResponse;

import java.text.DecimalFormat; // <-- THÊM IMPORT
import java.util.ArrayList;
import java.util.Locale;

public class RestaurantDetailActivity extends AppCompatActivity implements FoodAdapter.OnFoodClickListener {

    private ActivityRestaurantDetailBinding binding;
    private DishViewModel dishViewModel;
    private FoodAdapter foodAdapter;
    private CartManager cartManager; // <-- THÊM CartManager
    private final DecimalFormat priceFormat = new DecimalFormat("#,###đ"); // <-- THÊM

    // Biến để lưu trữ TẤT CẢ dữ liệu từ Intent
    private int restaurantId = -1;
    private String restaurantName = "Chi tiết";
    private String restaurantImageUrl = null;
    private double restaurantRating = 0.0;
    private int restaurantReviewCount = 0;
    private int restaurantOrderCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // BƯỚC 1: Lấy TẤT CẢ dữ liệu từ Intent
        if (!getIntentData()) {
            Toast.makeText(this, "Lỗi: Không tìm thấy nhà hàng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // BƯỚC 2: Lấy CartManager
        cartManager = CartManager.getInstance();

        // BƯỚC 3: Khởi tạo ViewModel
        dishViewModel = new ViewModelProvider(this).get(DishViewModel.class);

        // BƯỚC 4: Gán dữ liệu vào Header
        setupViews();

        // BƯỚC 5: Cài đặt RecyclerView
        setupRecyclerView();

        // BƯỚC 6: Bắt đầu lắng nghe giỏ hàng
        setupCartObservers();

        // BƯỚC 7: Tải danh sách món ăn
        loadDishes();
    }

    /**
     * Lấy toàn bộ dữ liệu từ Intent (FragmentIndex gửi sang)
     */
    private boolean getIntentData() {
        restaurantId = getIntent().getIntExtra("RESTAURANT_ID", -1);
        if (restaurantId == -1) {
            return false;
        }
        restaurantName = getIntent().getStringExtra("RESTAURANT_NAME");
        restaurantImageUrl = getIntent().getStringExtra("RESTAURANT_IMAGE_URL");
        restaurantRating = getIntent().getDoubleExtra("RESTAURANT", 0.0);
        restaurantOrderCount = getIntent().getIntExtra("RESTAURANT_ORDER_COUNT", 0);
        restaurantReviewCount = getIntent().getIntExtra("RESTAURANT_REVIEW_COUNT", 0);
        return true;
    }

    /**
     * Gán dữ liệu (đã lấy từ Intent) vào các View
     */
    private void setupViews() {
        // Nút Back
        binding.imgBack.setOnClickListener(v -> finish());

        // Gán dữ liệu vào CardView header
        binding.tvRestaurantName.setText(restaurantName);

        // Format "5.0 (15)"
        String ratingText = String.format(Locale.US, "%.1f (%d)",
                restaurantRating,
                restaurantReviewCount);
        binding.tvRating.setText(ratingText);

        // Format "200+ món bán"
        String orderCountText = restaurantOrderCount + "+ món bán";
        binding.tvSold.setText(orderCountText);

        // === SỬA LẠI CODE CỦA BẠN ===
        // Gán ảnh bìa (Code của bạn đang ẩn đi)
        // Bỏ: binding.imgRestaurant.setVisibility(View.GONE);
        Glide.with(this) // Thêm
                .load(restaurantImageUrl)
                .placeholder(R.drawable.avartar)
                .error(R.drawable.avartar)
                .into(binding.imgRestaurant);
        // ============================

        // Ẩn các view chưa có dữ liệu
        binding.tvDescription.setVisibility(View.GONE);
        // binding.tvDistance.setVisibility(View.GONE); // (Bạn chưa thêm ID cho distance)
    }

    /**
     * Cài đặt RecyclerView để hiển thị món ăn
     */
    private void setupRecyclerView() {
        foodAdapter = new FoodAdapter(new ArrayList<>(), this);
        binding.rcvFoodList.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvFoodList.setAdapter(foodAdapter);
    }

    /**
     * THÊM MỚI: Lắng nghe các thay đổi từ CartManager
     */
    private void setupCartObservers() {
        // 1. Lắng nghe Tổng tiền
        cartManager.getTotalPrice().observe(this, totalPrice -> {
            binding.tvTotalPrice.setText(priceFormat.format(totalPrice));
        });

        // 2. Lắng nghe Tổng số lượng
        cartManager.getTotalQuantity().observe(this, quantity -> {
            if (quantity > 0) {
                // Cập nhật text của nút
                String btnText = "Giao hàng (" + quantity + ")";
                binding.btnDelivery.setText(btnText);
            }
        });

        // 3. Lắng nghe danh sách item (để ẩn/hiện thanh giỏ hàng)
        cartManager.getCartItems().observe(this, cartItems -> {
            if (cartItems == null || cartItems.isEmpty()) {
                binding.layoutCartBar.setVisibility(View.GONE);
            } else {
                binding.layoutCartBar.setVisibility(View.VISIBLE);
            }
        });

        // 4. Lắng nghe các thông báo (như "Xóa giỏ hàng cũ")
        cartManager.getCartMessageEvent().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        // 5. Gán sự kiện click cho nút "Giao hàng"
        binding.btnDelivery.setOnClickListener(v -> {
            // TODO: Mở màn hình giỏ hàng (CartActivity)
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void loadDishes() {
        dishViewModel.fetchDishesByRestaurant(restaurantId, 1, 10).observe(this, result -> {
            if (result == null) return;
            switch (result.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getData() != null) {
                        foodAdapter.updateData(result.data.getData());
                    }
                    break;
                case ERROR:
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();
                    break;
            }
            dishViewModel.fetchDishesByRestaurant(restaurantId, 1, 10).removeObservers(this);
        });
    }

    @Override
    public void onAddClick(DishResponse food) {
        cartManager.addItem(food, this.restaurantId);
    }

    @Override
    public void onItemClick(DishResponse food) {
        Toast.makeText(this, "Xem chi tiết: " + food.getDishName(), Toast.LENGTH_SHORT).show();
    }
}