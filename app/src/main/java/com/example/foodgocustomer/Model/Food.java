package com.example.foodgocustomer.Model;

public class Food {
    private String name;
    private double price;
    private double rating;
    private int sold;
    private String imageUrl;

    public Food(String name, double price, double rating, int sold, String imageUrl) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.sold = sold;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public int getSold() { return sold; }
    public String getImageUrl() { return imageUrl; }
}
