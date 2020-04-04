package com.angik.kemon.HelperClass;

public class ResPlatterDetailClass {

    private String platterName;
    private String platterDetail;
    private String platterPrice;
    private String platterImageUrl;

    public ResPlatterDetailClass() {
    }

    public ResPlatterDetailClass(String platterName, String platterDetail, String platterPrice, String platterImageUrl) {
        this.platterName = platterName;
        this.platterDetail = platterDetail;
        this.platterPrice = platterPrice;
        this.platterImageUrl = platterImageUrl;
    }

    public String getPlatterDetail() {
        return platterDetail;
    }

    public void setPlatterDetail(String platterDetail) {
        this.platterDetail = platterDetail;
    }

    public String getPlatterName() {
        return platterName;
    }

    public void setPlatterName(String platterName) {
        this.platterName = platterName;
    }

    public String getPlatterPrice() {
        return platterPrice;
    }

    public void setPlatterPrice(String platterPrice) {
        this.platterPrice = platterPrice;
    }

    public String getPlatterImageUrl() {
        return platterImageUrl;
    }

    public void setPlatterImageUrl(String platterImageUrl) {
        this.platterImageUrl = platterImageUrl;
    }
}
