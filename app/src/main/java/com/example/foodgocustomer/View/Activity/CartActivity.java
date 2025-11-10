package com.example.foodgocustomer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodgocustomer.Model.CartItem;
import com.example.foodgocustomer.Util.CartManager;
import com.example.foodgocustomer.View.Adapter.CartItemAdapter;
import com.example.foodgocustomer.ViewModel.OrderDetailViewModel;
import com.example.foodgocustomer.databinding.ActivityCartBinding;
import com.example.foodgocustomer.network.DTO.RequestOrderDto;
import com.example.foodgocustomer.network.DTO.RequestOrderItemDto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartItemAdapter.OnCartItemChangeListener {

    private ActivityCartBinding binding;
    private CartManager cartManager;
    private OrderDetailViewModel orderViewModel;
    private CartItemAdapter adapter;
    private final DecimalFormat priceFormat = new DecimalFormat("#,###đ");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartManager = CartManager.getInstance();
        orderViewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);

        binding.toolbar.setNavigationOnClickListener(v -> finish());
        setupRecyclerView();
        setupObservers();
        binding.btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void setupRecyclerView() {
        adapter = new CartItemAdapter(new ArrayList<>(), this);
        binding.rcvCartItems.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvCartItems.setAdapter(adapter);
    }

    private void setupObservers() {
        cartManager.getCartItems().observe(this, cartItems -> {
            if (cartItems == null || cartItems.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng rỗng", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                adapter.updateData(cartItems);
            }
        });

        cartManager.getTotalPrice().observe(this, totalPrice -> {
            binding.tvTotalPrice.setText(priceFormat.format(totalPrice));
        });
    }

    private void placeOrder() {
        binding.btnPlaceOrder.setEnabled(false);
        binding.btnPlaceOrder.setText("Đang xử lý...");

        List<CartItem> currentItems = cartManager.getCartItems().getValue();
        if (currentItems == null || currentItems.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng rỗng", Toast.LENGTH_SHORT).show();
            binding.btnPlaceOrder.setEnabled(true);
            return;
        }

        List<RequestOrderItemDto> orderItems = new ArrayList<>();
        for (CartItem item : currentItems) {
            orderItems.add(new RequestOrderItemDto(item.getDish().getDishId(), item.getQuantity()));
        }

        int restaurantId = cartManager.getCurrentRestaurantId();

        if (restaurantId == -1) {
            Toast.makeText(this, "Lỗi: Không xác định được nhà hàng", Toast.LENGTH_SHORT).show();
            binding.btnPlaceOrder.setEnabled(true);
            binding.btnPlaceOrder.setText("Đặt hàng");
            return;
        }

        RequestOrderDto orderRequest = new RequestOrderDto(restaurantId, orderItems);

        orderViewModel.createOrder(orderRequest).observe(this, result -> {
            if (result == null) return;

            switch (result.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    Toast.makeText(this, "Đặt hàng thành công! Mã đơn: " + result.data.getOrderCode(), Toast.LENGTH_LONG).show();
                    cartManager.clearCart();

                    Intent intent = new Intent(this, DetailOrderActivity.class);
                    intent.putExtra("ORDER_ID", result.data.getOrderId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    break;
                case ERROR:
                    Toast.makeText(this, "Đặt hàng thất bại: " + result.message, Toast.LENGTH_LONG).show();
                    binding.btnPlaceOrder.setEnabled(true);
                    binding.btnPlaceOrder.setText("Đặt hàng");
                    break;
            }
            orderViewModel.createOrder(orderRequest).removeObservers(this);
        });
    }

    @Override
    public void onIncrease(CartItem item) {
        cartManager.increaseQuantity(item);
    }

    @Override
    public void onDecrease(CartItem item) {
        cartManager.decreaseQuantity(item);
    }
}