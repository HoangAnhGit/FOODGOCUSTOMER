package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class ItemFoodDto {
    @SerializedName("dishId")
    private int dishId;

    @SerializedName("dishName")
    private String dishName;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("price")
    private double price; // Dùng double cho giá cả và rating

    @SerializedName("averageRating")
    private double averageRating;

    @SerializedName("ratingCount")
    private int ratingCount;

    @SerializedName("totalSold")
    private int totalSold;

    // --- Constructor (Không bắt buộc, nhưng hữu ích) ---
    public ItemFoodDto(int dishId, String dishName, String imageUrl, double price, double averageRating, int ratingCount, int totalSold) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.totalSold = totalSold;
    }

    // --- Getters (Rất quan trọng cho GSON/Retrofit và Data Binding) ---

    public int getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public int getTotalSold() {
        return totalSold;
    }
}