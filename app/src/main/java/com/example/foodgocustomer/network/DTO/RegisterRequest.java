package com.example.foodgocustomer.network.DTO;

public class RegisterRequest {
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String email;

    public RegisterRequest(String phoneNumber, String password, String confirmPassword, String fullName, String email) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        this.email = email;
    }
}
