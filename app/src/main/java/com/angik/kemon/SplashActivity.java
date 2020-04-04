package com.angik.kemon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.AsyncTasks.GetFeaturedResTask;
import com.angik.kemon.AsyncTasks.GetOfferTask;
import com.angik.kemon.AsyncTasks.GetResTask;
import com.angik.kemon.HelperClass.Common;
import com.angik.kemon.HelperClass.OfferDetailClass;
import com.angik.kemon.HelperClass.RestaurantDetailClass;
import com.angik.kemon.Interfaces.OfferCallback;
import com.angik.kemon.Interfaces.ResCallback;
import com.angik.kemon.Interfaces.ResDetailCallback;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);


        new GetFeaturedResTask(new ResDetailCallback() {
            @Override
            public void onResDetailCallback(List<RestaurantDetailClass> resCollection) {
                Common.restaurants = resCollection;

                /*progressBar.setVisibility(View.GONE);

                startActivity(new Intent(SplashActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                finish();*/
            }
        }, "Featured Restaurant").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        new GetOfferTask(new OfferCallback() {
            @Override
            public void onOfferCallback(List<OfferDetailClass> offers) {
                progressBar.setVisibility(View.GONE);

                startActivity(new Intent(SplashActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                finish();

                Common.offers = offers;
            }
        }, "sample node").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        new GetResTask(new ResCallback() {
            @Override
            public void onResCallback(List<RestaurantDetailClass> resCollection) {
                Common.allRestaurants = resCollection;
            }
        }, "Restaurants").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
