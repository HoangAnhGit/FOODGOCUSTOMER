package com.example.foodgocustomer.network.API;

import com.example.foodgocustomer.network.DTO.RegisterResponse;
import com.example.foodgocustomer.network.DTO.LoginRequest;
import com.example.foodgocustomer.network.DTO.LoginResponse;
import com.example.foodgocustomer.network.DTO.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/api/Auth/register/customer")
    Call<RegisterResponse> registerCustomer(@Body RegisterRequest request);


    @POST("/api/Auth/login")
    Call<LoginResponse> login(@Body LoginRequest body);



}
