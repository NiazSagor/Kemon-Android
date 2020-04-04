package com.angik.kemon.HelperClass;

public class DishDetailClass {

    private String dishName;
    private String dishImageUrl;
    private String dishResName;
    private String dishPrice;

    public DishDetailClass() {

    }

    public DishDetailClass(String dishName, String dishImageUrl, String dishResName, String dishPrice) {
        this.dishName = dishName;
        this.dishImageUrl = dishImageUrl;
        this.dishResName = dishResName;
        this.dishPrice = dishPrice;
    }

    public String getDishImageUrl() {
        return dishImageUrl;
    }

    public void setDishImageUrl(String dishImageUrl) {
        this.dishImageUrl = dishImageUrl;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishResName() {
        return dishResName;
    }

    public void setDishResName(String dishResName) {
        this.dishResName = dishResName;
    }

    public String getDishPrice() {
        return dishPrice + " TK";
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }
}
