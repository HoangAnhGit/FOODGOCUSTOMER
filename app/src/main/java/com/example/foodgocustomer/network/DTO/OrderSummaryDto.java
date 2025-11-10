package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OrderSummaryDto {
    @SerializedName("items")
    private List<OrderItemDetailDto> items;
    @SerializedName("subtotal")
    private double subtotal;
    @SerializedName("shippingFee")
    private double shippingFee;
    @SerializedName("serviceFee")
    private double serviceFee;
    @SerializedName("discountAmount")
    private double discountAmount;
    @SerializedName("grandTotal")
    private double grandTotal;
    @SerializedName("paymentStatusText")
    private String paymentStatusText;

    // Getters
    public List<OrderItemDetailDto> getItems() { return items; }
    public double getSubtotal() { return subtotal; }
    public double getShippingFee() { return shippingFee; }
    public double getServiceFee() { return serviceFee; }
    public double getDiscountAmount() { return discountAmount; }
    public double getGrandTotal() { return grandTotal; }
    public String getPaymentStatusText() { return paymentStatusText; }
}