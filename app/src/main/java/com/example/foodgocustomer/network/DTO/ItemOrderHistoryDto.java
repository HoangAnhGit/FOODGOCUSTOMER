package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class ItemOrderHistoryDto {

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("restaurantName")
    private String restaurantName;

    @SerializedName("orderStatus")
    private String orderStatus;

    @SerializedName("orderDate")
    private String orderDate;

    @SerializedName("totalPrice")
    private double totalPrice;

    @SerializedName("orderSummary")
    private String orderSummary;


    public int getOrderId() {
        return orderId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getOrderSummary() {
        return orderSummary;
    }
}