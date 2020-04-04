package com.angik.kemon.HelperClass;

public class ReviewDetailClass {

    private String postingDate;
    private String userName;
    private String reviewedItemName;//Or can be platter name
    private String reviewedItemPrice;
    private String reviewedResName;
    private String userReview;
    private String reviewPhotoText;

    private double serviceRate;
    private double behaviourRate;
    private double environmentRate;

    public ReviewDetailClass() {
    }

    public ReviewDetailClass(String postingDate, String userName, String reviewedItemName, String reviewedItemPrice, String reviewedResName, String userReview, String reviewPhotoText, double serviceRate, double behaviourRate, double environmentRate) {
        this.postingDate = postingDate;
        this.userName = userName;
        this.reviewedItemName = reviewedItemName;
        this.reviewedItemPrice = reviewedItemPrice;
        this.reviewedResName = reviewedResName;
        this.userReview = userReview;

        if (reviewPhotoText == null) {
            this.reviewPhotoText = null;
        } else {
            this.reviewPhotoText = reviewPhotoText;
        }

        this.serviceRate = serviceRate;
        this.behaviourRate = behaviourRate;
        this.environmentRate = environmentRate;
    }

    public double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public double getBehaviourRate() {
        return behaviourRate;
    }

    public void setBehaviourRate(double behaviourRate) {
        this.behaviourRate = behaviourRate;
    }

    public double getEnvironmentRate() {
        return environmentRate;
    }

    public void setEnvironmentRate(double environmentRate) {
        this.environmentRate = environmentRate;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReviewedItemName() {
        return reviewedItemName;
    }

    public void setReviewedItemName(String reviewedItemName) {
        this.reviewedItemName = reviewedItemName;
    }

    public String getReviewedItemPrice() {
        return reviewedItemPrice;
    }

    public void setReviewedItemPrice(String reviewedItemPrice) {
        this.reviewedItemPrice = reviewedItemPrice;
    }

    public String getReviewedResName() {
        return reviewedResName;
    }

    public void setReviewedResName(String reviewedResName) {
        this.reviewedResName = reviewedResName;
    }

    public String getReviewPhotoText() {
        return reviewPhotoText;
    }

    public void setReviewPhotoText(String reviewPhotoText) {
        this.reviewPhotoText = reviewPhotoText;
    }
}
