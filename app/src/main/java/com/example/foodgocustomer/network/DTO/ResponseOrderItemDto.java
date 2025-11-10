package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class ResponseOrderItemDto {
    @SerializedName("dishId")
    private int dishId;
    @SerializedName("dishName")
    private String dishName;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("priceAtOrder")
    private double priceAtOrder;
    @SerializedName("total")
    private double total;


    public int getDishId() { return dishId; }
    public String getDishName() { return dishName; }

}