package com.rh.rentals;

public class Car {
    private int id;
    private String name;
    private double price;
    private String imageUris; // Store as comma-separated string

    public Car(int id, String name, double price, String imageUris) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUris = imageUris;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUris() {
        return imageUris;
    }
}
