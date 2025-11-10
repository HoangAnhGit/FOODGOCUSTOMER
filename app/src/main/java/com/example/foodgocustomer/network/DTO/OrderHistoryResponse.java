package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OrderHistoryResponse {

    @SerializedName("pageNumber")
    private int pageNumber;

    @SerializedName("pageSize")
    private int pageSize;

    @SerializedName("totalPages")
    private int totalPages;

    @SerializedName("totalRecords")
    private int totalRecords;

    @SerializedName("data")
    private List<ItemOrderHistoryDto> data;


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

    public List<ItemOrderHistoryDto> getData() {
        return data;
    }
}