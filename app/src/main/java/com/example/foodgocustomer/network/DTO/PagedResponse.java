package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PagedResponse<T> {

    @SerializedName("data")
    private List<T> data;

    @SerializedName("pageNumber")
    private int pageNumber;

    @SerializedName("pageSize")
    private int pageSize;

    @SerializedName("totalPages")
    private int totalPages;

    @SerializedName("totalRecords")
    private int totalRecords;

    // --- TRƯỜNG MỚI CHO API RESTAURANT ---
    @SerializedName("hasCustomerAddress")
    private Boolean hasCustomerAddress;

    @SerializedName("customerDefaultAddress")
    private String customerDefaultAddress;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Boolean getHasCustomerAddress() {
        return hasCustomerAddress;
    }

    public void setHasCustomerAddress(Boolean hasCustomerAddress) {
        this.hasCustomerAddress = hasCustomerAddress;
    }

    public String getCustomerDefaultAddress() {
        return customerDefaultAddress;
    }

    public void setCustomerDefaultAddress(String customerDefaultAddress) {
        this.customerDefaultAddress = customerDefaultAddress;
    }
}