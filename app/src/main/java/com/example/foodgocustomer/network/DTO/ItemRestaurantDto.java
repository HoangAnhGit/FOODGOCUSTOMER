package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class ItemRestaurantDto {

    @SerializedName("restaurantId")
    private int restaurantId;

    @SerializedName("name")
    private String name;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("averageRating")
    private double averageRating;

    @SerializedName("reviewCount")
    private int reviewCount;

    @SerializedName("completedOrderCount")
    private int completedOrderCount;

    @SerializedName("distanceInKm")
    private double distanceInKm;

    // --- Getters ---
    public int getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public double getAverageRating() { return averageRating; }
    public int getReviewCount() { return reviewCount; }
    public int getCompletedOrderCount() { return completedOrderCount; }
    public double getDistanceInKm() { return distanceInKm; }
}