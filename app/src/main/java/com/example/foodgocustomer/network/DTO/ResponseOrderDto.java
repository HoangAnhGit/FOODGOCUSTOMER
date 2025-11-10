package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

public class ResponseOrderDto {
    @SerializedName("orderId")
    private int orderId;
    @SerializedName("orderCode")
    private String orderCode;
    @SerializedName("restaurantName")
    private String restaurantName;
    @SerializedName("deliveryAddress")
    private String deliveryAddress;
    @SerializedName("subtotal")
    private double subtotal;
    @SerializedName("shippingFee")
    private double shippingFee;
    @SerializedName("discountAmount")
    private double discountAmount;
    @SerializedName("totalAmount")
    private double totalAmount;
    @SerializedName("orderStatus")
    private String orderStatus;
    @SerializedName("paymentMethod")
    private String paymentMethod;
    @SerializedName("paymentStatus")
    private String paymentStatus;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("items")
    private List<ResponseOrderItemDto> items;

    public int getOrderId() { return orderId; }
    public String getOrderCode() { return orderCode; }

}