package com.example.foodgocustomer.network.DTO;

public class AddressDto {
    private String street;
    private String ward;
    private String district;
    private String city;
    private String fullAddress;
    private boolean isDefault;

    public AddressDto(String street, String ward, String district, String city, String fullAddress, boolean isDefault) {
        this.street = street;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.fullAddress = fullAddress;
        this.isDefault = isDefault;
    }

    // Getter & Setter
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getFullAddress() { return fullAddress; }
    public void setFullAddress(String fullAddress) { this.fullAddress = fullAddress; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }

}
