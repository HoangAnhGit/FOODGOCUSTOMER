package com.example.foodgocustomer.network.API;

import com.example.foodgocustomer.network.DTO.DishResponse;
import com.example.foodgocustomer.network.DTO.PagedResponse;
import com.example.foodgocustomer.network.DTO.RequestOrderDto;
import com.example.foodgocustomer.network.DTO.ResponseOrderDetailDto;
import com.example.foodgocustomer.network.DTO.ResponseOrderDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DishApi {
    @GET("/api/Customer/dishes")
    Call<PagedResponse<DishResponse>> getAllDishes(
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );

    @GET("/api/Customer/orders/{orderId}")
    Call<ResponseOrderDetailDto> getOrderDetail(@Path("orderId") int orderId);

    @POST("/api/Customer/orders")
    Call<ResponseOrderDto> createOrder(@Body RequestOrderDto orderRequest);

    @GET("/api/Customer/restaurants/{restaurantId}/dishes")
    Call<PagedResponse<DishResponse>> getDishesByRestaurant(
            @Path("restaurantId") int restaurantId,
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );
}
