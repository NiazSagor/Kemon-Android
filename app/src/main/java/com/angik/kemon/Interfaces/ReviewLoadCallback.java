package com.angik.kemon.Interfaces;

import com.angik.kemon.HelperClass.ReviewDetailClass;

import java.util.List;

public interface ReviewLoadCallback {
    void onReviewLoadCallback(List<ReviewDetailClass> reviews);
}
