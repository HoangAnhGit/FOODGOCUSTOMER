package com.example.foodgocustomer.network;

import com.example.foodgocustomer.Model.LoginRequest;
import com.example.foodgocustomer.Model.LoginResponse;
import com.example.foodgocustomer.Model.RegisterRequest;
import com.example.foodgocustomer.Model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    // Đăng ký khách hàng
    @POST("/api/Auth/register/customer")
    Call<RegisterResponse> register(@Body RegisterRequest body);


    // Đăng nhập
    @POST("/api/Auth/login")
    Call<LoginResponse> login(@Body LoginRequest body);

}
