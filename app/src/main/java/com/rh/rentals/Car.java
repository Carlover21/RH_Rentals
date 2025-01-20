package com.rh.rentals; // Ensure this matches your package name

import java.util.List;

public class Car {
    private String name;
    private double price;
    private List<Integer> imageResourceIds; // List of image resource IDs

    // Constructor
    public Car(String name, double price, List<Integer> imageResourceIds) {
        this.name = name;
        this.price = price;
        this.imageResourceIds = imageResourceIds;
    }

    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public List<Integer> getImageResourceIds() { return imageResourceIds; }
}
