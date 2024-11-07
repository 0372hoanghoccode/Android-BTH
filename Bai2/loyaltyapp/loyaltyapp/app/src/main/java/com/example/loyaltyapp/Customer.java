package com.example.loyaltyapp;

public class Customer {
    private String name;
    private String phone;
    private int points;
    private String createdDate; // Thêm thuộc tính ngày tạo

    public Customer(String name, String phone, int points, String createdDate) {
        this.name = name;
        this.phone = phone;
        this.points = points;
        this.createdDate = createdDate; // Gán giá trị cho ngày tạo
    }

    // Getter và Setter cho các thuộc tính
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getPoints() {
        return points;
    }

    public String getCreatedDate() {
        return createdDate; // Getter cho ngày tạo
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
