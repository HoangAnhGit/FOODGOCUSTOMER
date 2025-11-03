package com.example.foodgocustomer.network;

import com.example.foodgocustomer.Model.ApiResponse;
import com.example.foodgocustomer.Model.LoginRequest;
import com.example.foodgocustomer.Model.LoginResponse;
import com.example.foodgocustomer.Model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FoodApi {

    // Đăng ký khách hàng
    @POST("/api/Auth/register/customer")
    Call<ApiResponse> registerCustomer(@Body RegisterRequest request);


    // Đăng nhập
    @POST("/api/Auth/login")
    Call<LoginResponse> login(@Body LoginRequest body);

}
