package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class RequestOrderItemDto {
    @SerializedName("dishId")
    private int dishId;

    @SerializedName("quantity")
    private int quantity;

    public RequestOrderItemDto(int dishId, int quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    // Getters
    public int getDishId() { return dishId; }
    public int getQuantity() { return quantity; }
}