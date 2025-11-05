package com.example.foodgocustomer.network.API;

import com.example.foodgocustomer.network.DTO.FoodResponse;
import com.example.foodgocustomer.network.DTO.PagedResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApi {


    @GET("/api/Customer/dishes")
    Call<PagedResponse<FoodResponse>> getDishes(
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );
}
