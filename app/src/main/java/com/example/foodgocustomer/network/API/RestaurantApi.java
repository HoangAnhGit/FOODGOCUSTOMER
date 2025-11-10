package com.example.foodgocustomer.network.API;

import com.example.foodgocustomer.network.DTO.DishResponse;
import com.example.foodgocustomer.network.DTO.ItemRestaurantDto;
import com.example.foodgocustomer.network.DTO.PagedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestaurantApi {
    @GET("/api/Customer/restaurants")
    Call<PagedResponse<ItemRestaurantDto>> getRestaurants(
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );


}
