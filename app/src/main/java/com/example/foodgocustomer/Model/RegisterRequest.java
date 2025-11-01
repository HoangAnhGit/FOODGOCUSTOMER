package com.example.foodgocustomer.Model;

public class RegisterRequest {
    public String fullName;
    public String phone;
    public String password;

    public RegisterRequest(String fullName, String phone, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
    }
}
