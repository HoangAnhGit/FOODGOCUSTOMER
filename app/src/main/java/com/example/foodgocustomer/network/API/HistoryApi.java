package com.example.foodgocustomer.network.API;

import com.example.foodgocustomer.network.DTO.DishResponse;
import com.example.foodgocustomer.network.DTO.PagedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoryApi {

    @GET("/api/Customer/orders/history")
    Call<PagedResponse<DishResponse>> getHistoryByStatus(
            @Path("id") int status,
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );
}
