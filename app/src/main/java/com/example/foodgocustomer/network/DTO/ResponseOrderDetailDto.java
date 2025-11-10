package com.example.foodgocustomer.network.DTO;

import com.google.gson.annotations.SerializedName;

public class ResponseOrderDetailDto {
    @SerializedName("statusText")
    private String statusText;
    @SerializedName("estimatedDeliveryTime")
    private String estimatedDeliveryTime;
    @SerializedName("orderStatusKey")
    private String orderStatusKey;
    @SerializedName("orderCode")
    private String orderCode;
    @SerializedName("note")
    private String note;
    @SerializedName("shipperInfo")
    private ShipperInfoDto shipperInfo;
    @SerializedName("addressInfo")
    private AddressInfoDto addressInfo;
    @SerializedName("summary")
    private OrderSummaryDto summary;

    // Getters
    public String getStatusText() { return statusText; }
    public String getEstimatedDeliveryTime() { return estimatedDeliveryTime; }
    public String getOrderStatusKey() { return orderStatusKey; }
    public String getOrderCode() { return orderCode; }
    public String getNote() { return note; }
    public ShipperInfoDto getShipperInfo() { return shipperInfo; }
    public AddressInfoDto getAddressInfo() { return addressInfo; }
    public OrderSummaryDto getSummary() { return summary; }
}