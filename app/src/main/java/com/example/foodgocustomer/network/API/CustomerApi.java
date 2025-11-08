package com.example.foodgocustomer.network.API;

import com.example.foodgocustomer.Model.Address;
import com.example.foodgocustomer.Model.OrderHistory;
import com.example.foodgocustomer.network.DTO.AddressDto;
import com.example.foodgocustomer.network.DTO.ApiResponse;
import com.example.foodgocustomer.network.DTO.OrderHistoryResponse;
import com.example.foodgocustomer.network.DTO.UserProfileDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CustomerApi {

    @PUT("/api/Customer/profile")
    Call<ApiResponse> editProfile(
            @Body UserProfileDto userProfileDto);

    @GET("/api/Customer/profile")
    Call<UserProfileDto> getProfile();


    @POST("/api/Customer/addresses")
    Call<ApiResponse> addAddress(@Body AddressDto addAddressRequest);

    @GET("/api/Customer/addresses")
    Call<List<Address>> getAddress();

    @GET("/api/Customer/orders/history")
    Call<OrderHistoryResponse> getOrderHistory(
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize,
            @Query("status") String status
    );
}
