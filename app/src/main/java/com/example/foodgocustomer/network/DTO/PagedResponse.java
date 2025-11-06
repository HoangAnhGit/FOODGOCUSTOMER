package com.example.foodgocustomer.network.DTO;

import java.util.List;

public class PagedResponse<T> {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private int totalRecords;
    private List<DishResponse> data;

    public int getPageNumber() { return pageNumber; }
    public int getPageSize() { return pageSize; }
    public int getTotalPages() { return totalPages; }
    public int getTotalRecords() { return totalRecords; }
    public List<DishResponse> getData() { return data; }

    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public void setTotalRecords(int totalRecords) { this.totalRecords = totalRecords; }
    public void setData(List<DishResponse> data) { this.data = data; }
}
