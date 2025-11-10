package com.example.foodgocustomer.Model;

import com.example.foodgocustomer.network.DTO.DishResponse;

public class CartItem {
    private DishResponse dish;
    private int quantity;

    public CartItem(DishResponse dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    // Getters
    public DishResponse getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setter
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}