package com.example.appdatdoan.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Food food;
    private int quantity;
    private String size;

    public CartItem(Food food, int quantity, String size) {
        this.food = food;
        this.quantity = quantity;
        this.size = size;
    }

    public Food getFood() { return food; }
    public void setFood(Food food) { this.food = food; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    
    public double getTotalPrice() { 
        double unitPrice = "L".equals(size) ? food.getPriceL() : food.getPrice();
        return unitPrice * quantity; 
    }
}
