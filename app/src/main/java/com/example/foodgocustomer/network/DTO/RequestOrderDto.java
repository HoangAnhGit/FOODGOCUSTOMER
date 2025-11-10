package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RequestOrderDto {
    @SerializedName("restaurantId")
    private int restaurantId;

    @SerializedName("items")
    private List<RequestOrderItemDto> items;

    public RequestOrderDto(int restaurantId, List<RequestOrderItemDto> items) {
        this.restaurantId = restaurantId;
        this.items = items;
    }

    // Getters
    public int getRestaurantId() { return restaurantId; }
    public List<RequestOrderItemDto> getItems() { return items; }
}