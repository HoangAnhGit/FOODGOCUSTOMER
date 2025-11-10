package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class AddressInfoDto {
    @SerializedName("restaurantName")
    private String restaurantName;
    @SerializedName("restaurantAddress")
    private String restaurantAddress;
    @SerializedName("deliveryAddress")
    private String deliveryAddress;
    @SerializedName("customerName")
    private String customerName;
    @SerializedName("customerPhone")
    private String customerPhone;

    // Getters
    public String getRestaurantName() { return restaurantName; }
    public String getRestaurantAddress() { return restaurantAddress; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
}