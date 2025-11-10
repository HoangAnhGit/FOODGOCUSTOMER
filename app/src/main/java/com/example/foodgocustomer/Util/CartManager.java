package com.example.foodgocustomer.Util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodgocustomer.Model.CartItem;
import com.example.foodgocustomer.network.DTO.DishResponse;

import java.util.ArrayList;
import java.util.List;
import com.example.foodgocustomer.common.SingleLiveEvent;

public class CartManager {
    private static CartManager instance;

    // Dữ liệu giỏ hàng
    private int currentRestaurantId = -1;
    private final List<CartItem> currentItems = new ArrayList<>();

    // LiveData để giao diện tự động cập nhật
    private final MutableLiveData<List<CartItem>> itemsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Double> totalPriceLiveData = new MutableLiveData<>(0.0);
    private final MutableLiveData<Integer> totalQuantityLiveData = new MutableLiveData<>(0);

    // LiveData cho các thông báo (ví dụ: "Đã xóa giỏ hàng cũ")
    private final com.example.foodgocustomer.common.SingleLiveEvent<String> cartMessageEvent = new SingleLiveEvent<>();

    // Constructor riêng tư (Singleton)
    private CartManager() {}

    // Hàm để lấy đối tượng duy nhất
    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    /**
     * Thêm một món ăn vào giỏ hàng
     */
    public void addItem(DishResponse dish, int restaurantId) {

        if (currentRestaurantId == -1) {
            currentRestaurantId = restaurantId;
        } else if (currentRestaurantId != restaurantId) {
            clearCart();
            currentRestaurantId = restaurantId;
            cartMessageEvent.setValue("Đã xóa giỏ hàng từ quán cũ");
        }

        for (CartItem item : currentItems) {
            if (item.getDish().getDishId() == dish.getDishId()) {
                item.setQuantity(item.getQuantity() + 1);
                recalculateTotals();
                return;
            }
        }

        currentItems.add(new CartItem(dish, 1));
        recalculateTotals();
    }

    /**
     * GIẢM SỐ LƯỢNG
     */
    public void decreaseQuantity(CartItem cartItem) {
        for (CartItem item : currentItems) {
            if (item.getDish().getDishId() == cartItem.getDish().getDishId()) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    currentItems.remove(item);
                }
                recalculateTotals();
                return;
            }
        }
    }

    /**
     * TĂNG SỐ LƯỢNG
     */
    public void increaseQuantity(CartItem cartItem) {
        for (CartItem item : currentItems) {
            if (item.getDish().getDishId() == cartItem.getDish().getDishId()) {
                item.setQuantity(item.getQuantity() + 1);
                recalculateTotals();
                return;
            }
        }
    }

    /**
     * Tính toán lại tổng tiền, tổng số lượng và cập nhật LiveData
     */
    private void recalculateTotals() {
        double total = 0.0;
        int quantity = 0;

        for (CartItem item : currentItems) {
            total += item.getDish().getPrice() * item.getQuantity();
            quantity += item.getQuantity();
        }

        totalPriceLiveData.setValue(total);
        totalQuantityLiveData.setValue(quantity);
        itemsLiveData.setValue(currentItems);
    }

    /**
     * Xóa sạch giỏ hàng
     */
    public void clearCart() {
        currentRestaurantId = -1;
        currentItems.clear();
        recalculateTotals();
    }

    // === CÁC HÀM GETTER ĐỂ ACTIVITY LẮNG NGHE ===

    public LiveData<List<CartItem>> getCartItems() {
        return itemsLiveData;
    }

    public LiveData<Double> getTotalPrice() {
        return totalPriceLiveData;
    }

    public LiveData<Integer> getTotalQuantity() {
        return totalQuantityLiveData;
    }

    public LiveData<String> getCartMessageEvent() {
        return cartMessageEvent;
    }

    /**
     * HÀM MỚI: Lấy ID của nhà hàng hiện tại trong giỏ hàng
     */
    public int getCurrentRestaurantId() {
        return currentRestaurantId;
    }
}