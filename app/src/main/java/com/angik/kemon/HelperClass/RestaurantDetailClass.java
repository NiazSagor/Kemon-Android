package com.angik.kemon.HelperClass;

public class RestaurantDetailClass {

    private String resName;
    private String resAddress;
    private String resImageUrl;
    private double rating;

    public RestaurantDetailClass() {

    }

    public RestaurantDetailClass(String resName, String resAddress, String resImageUrl, float rating) {
        this.resName = resName;
        this.resAddress = resAddress;
        this.resImageUrl = resImageUrl;
        this.rating = rating;
    }

    public String getResImageUrl() {
        return resImageUrl;
    }

    public void setResImageUrl(String resImageUrl) {
        this.resImageUrl = resImageUrl;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResAddress() {
        return resAddress;
    }

    public void setResAddress(String resAddress) {
        this.resAddress = resAddress;
    }

    public String getResRate() {
        return "" + rating;
    }

    public void setResRate(double rating) {
        this.rating = rating;
    }
}
