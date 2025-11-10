package com.example.foodgocustomer.Util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodgocustomer.Model.CartItem;
import com.example.foodgocustomer.network.DTO.DishResponse;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;

    private int currentRestaurantId = -1;
    private final List<CartItem> currentItems = new ArrayList<>();

    private final MutableLiveData<List<CartItem>> itemsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Double> totalPriceLiveData = new MutableLiveData<>(0.0);
    private final MutableLiveData<Integer> totalQuantityLiveData = new MutableLiveData<>(0);

    private final SingleLiveEvent<String> cartMessageEvent = new SingleLiveEvent<>();

    private CartManager() {}

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }


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

    public void increaseQuantity(CartItem cartItem) {
        for (CartItem item : currentItems) {
            if (item.getDish().getDishId() == cartItem.getDish().getDishId()) {
                item.setQuantity(item.getQuantity() + 1);
                recalculateTotals();
                return;
            }
        }
    }

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

    public void clearCart() {
        currentRestaurantId = -1;
        currentItems.clear();
        recalculateTotals();
    }

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

    public int getCurrentRestaurantId() {
        return currentRestaurantId;
    }
}