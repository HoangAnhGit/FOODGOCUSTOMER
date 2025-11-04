package com.example.foodgocustomer.Model;

public class Order {
        private String restaurantName;
        private String status;
        private String orderDate;
        private String totalPrice;
        private String orderSummary;

        public Order(String restaurantName, String status, String orderDate, String totalPrice, String orderSummary) {
            this.restaurantName = restaurantName;
            this.status = status;
            this.orderDate = orderDate;
            this.totalPrice = totalPrice;
            this.orderSummary = orderSummary;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public String getStatus() {
            return status;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public String getOrderSummary() {
            return orderSummary;
        }
    }

