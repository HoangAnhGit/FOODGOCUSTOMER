package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class OrderItemDetailDto {
    @SerializedName("dishName")
    private String dishName;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("priceAtOrder")
    private double priceAtOrder;

    public String getDishName() { return dishName; }
    public int getQuantity() { return quantity; }
    public double getPriceAtOrder() { return priceAtOrder; }
}