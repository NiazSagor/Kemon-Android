package com.angik.kemon.HelperClass;

public class OfferDetailClass {

    private String offerImageUrl;
    private String offeredRestaurant;
    private String mOfferedPrice;
    private String mOfferedPercentage;

    public OfferDetailClass() {
    }

    public OfferDetailClass(String offerImageUrl, String offeredPercentage, String offeredPrice, String offeredRestaurant) {
        this.offerImageUrl = offerImageUrl;
        this.offeredRestaurant = offeredRestaurant;

        if (offeredPrice == null) {
            this.mOfferedPrice = "Price Not Mentioned";
        } else {
            this.mOfferedPrice = offeredPrice;
        }

        if (offeredPercentage == null) {
            this.mOfferedPercentage = "Percentage Not Mentioned";
        } else {
            this.mOfferedPercentage = offeredPercentage;
        }
    }

    public String getOfferImageUrl() {
        return offerImageUrl;
    }

    public void setOfferImageUrl(String offerImageUrl) {
        this.offerImageUrl = offerImageUrl;
    }

    public String getOfferedRestaurant() {
        return offeredRestaurant;
    }

    public void setOfferedRestaurant(String offeredRestaurant) {
        this.offeredRestaurant = offeredRestaurant;
    }

    public String getmOfferedPrice() {
        return mOfferedPrice;
    }

    public void setmOfferedPrice(String mOfferedPrice) {
        this.mOfferedPrice = mOfferedPrice;
    }

    public String getmOfferedPercentage() {
        return mOfferedPercentage;
    }

    public void setmOfferedPercentage(String mOfferedPercentage) {
        this.mOfferedPercentage = mOfferedPercentage;
    }
}
