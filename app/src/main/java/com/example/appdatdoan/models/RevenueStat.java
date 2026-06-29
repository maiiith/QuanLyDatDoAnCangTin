package com.example.appdatdoan.models;

public class RevenueStat {
    private String period;
    private double totalRevenue;
    private String topFoods;

    public RevenueStat(String period, double totalRevenue) {
        this.period = period;
        this.totalRevenue = totalRevenue;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getTopFoods() {
        return topFoods;
    }

    public void setTopFoods(String topFoods) {
        this.topFoods = topFoods;
    }
}
