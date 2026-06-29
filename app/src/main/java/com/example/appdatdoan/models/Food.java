package com.example.appdatdoan.models;

import java.io.Serializable;

public class Food implements Serializable {
    private int id;
    private String name;
    private int categoryId;
    private double price; // Size M price
    private double priceL; // Size L price
    private String description;
    private String imageResource;

    public Food() {}

    public Food(int id, String name, int categoryId, double price, double priceL, String description, String imageResource) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.priceL = priceL;
        this.description = description;
        this.imageResource = imageResource;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getPriceL() { return priceL; }
    public void setPriceL(double priceL) { this.priceL = priceL; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageResource() { return imageResource; }
    public void setImageResource(String imageResource) { this.imageResource = imageResource; }
}
