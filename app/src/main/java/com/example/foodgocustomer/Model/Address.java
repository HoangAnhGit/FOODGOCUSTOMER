package com.example.foodgocustomer.Model;

public class Address {
    private int addressId;
    private String customerName;
    private String customerPhone; // <-- Đã thêm lại
    private String fullAddress;
    private boolean isDefault;

    public Address(int addressId, String customerName, String customerPhone, String fullAddress, boolean isDefault) {
        this.addressId = addressId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.fullAddress = fullAddress;
        this.isDefault = isDefault;
    }

    // Getters
    public int getAddressId() {
        return addressId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public boolean isDefault() {
        return isDefault;
    }
}