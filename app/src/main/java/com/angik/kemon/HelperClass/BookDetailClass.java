package com.angik.kemon.HelperClass;

public class BookDetailClass {

    private String resName;
    private String resAddress;
    private String resImageUrl;

    private String phoneNumber;
    private String prePayment;

    public BookDetailClass() {
    }

    public BookDetailClass(String resName, String resAddress, String phoneNumber, String prePayment) {
        this.resName = resName;
        this.resAddress = resAddress;

        if (prePayment == null) {
            this.phoneNumber = phoneNumber;
        } else {
            this.prePayment = prePayment;
            this.phoneNumber = phoneNumber;
        }
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPrePayment() {
        return prePayment;
    }

    public void setPrePayment(String prePayment) {
        this.prePayment = prePayment;
    }

    public String getResImageUrl() {
        return resImageUrl;
    }

    public void setResImageUrl(String resImageUrl) {
        this.resImageUrl = resImageUrl;
    }
}
