package com.example.foodgocustomer.network.API;

import com.example.foodgocustomer.network.DTO.DishResponse;
import com.example.foodgocustomer.network.DTO.PagedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DishApi {


    @GET("/api/Customer/dishes")
    Call<PagedResponse<DishResponse>> getAllFoods(
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );

    @GET("/api/Customer/restaurants/{id}/dishes")
    Call<PagedResponse<DishResponse>> getDishesByRestaurant(
            @Path("id") int restaurantId,
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );
}
