package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class DishResponse {

    @SerializedName("dishId")
    private int dishId;

    @SerializedName("dishName")
    private String dishName;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("price")
    private double price;

    // --- THÊM CÁC TRƯỜNG TỪ API C# ---
    @SerializedName("averageRating")
    private double averageRating;

    @SerializedName("ratingCount")
    private int ratingCount;

    @SerializedName("totalSold")
    private int totalSold;

    public int getDishId() { return dishId; }
    public String getDishName() { return dishName; }
    public String getImageUrl() { return imageUrl; }
    public double getPrice() { return price; }
    public double getAverageRating() { return averageRating; }
    public int getRatingCount() { return ratingCount; }
    public int getTotalSold() { return totalSold; }
}