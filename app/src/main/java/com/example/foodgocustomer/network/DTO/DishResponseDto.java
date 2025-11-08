package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DishResponseDto {
    @SerializedName("pageNumber")
    private int pageNumber;

    @SerializedName("pageSize")
    private int pageSize;

    @SerializedName("totalPages")
    private int totalPages;

    @SerializedName("totalRecords")
    private int totalRecords;

    @SerializedName("data")
    private List<ItemFoodDto> data;

    // --- Getters ---

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public List<ItemFoodDto> getData() {
        return data;
    }
}
