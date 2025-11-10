package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class ShipperInfoDto {
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("phoneNumber")
    private String phoneNumber;

    // Getters
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
}