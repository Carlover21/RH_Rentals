package com.rh.rentals;

public class Car {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imageUris;

    // Constructor
    public Car(int id, String name, double price, String description, String imageUris) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUris = imageUris;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUris() {
        return imageUris;
    }
}
