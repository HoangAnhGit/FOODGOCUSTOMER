package com.example.foodgocustomer.Model;

public class ApiResponse {
    private String title;
    private String status;
    private Object errors; // có thể là Map<String, List<String>>

    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public Object getErrors() { return errors; }
}
