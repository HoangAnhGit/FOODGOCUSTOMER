package com.example.foodgocustomer.network.DTO;

public class LoginResponse {
    private String token;
    private String userId;
    private String name;

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
