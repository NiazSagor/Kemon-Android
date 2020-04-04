package com.angik.kemon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.Fragments.BookFragment;
import com.angik.kemon.Fragments.HomeFragment;
import com.angik.kemon.Fragments.OfferFragment;
import com.angik.kemon.Fragments.RestaurantFragment;
import com.angik.kemon.Fragments.ReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, RestaurantFragment.OnFragmentInteractionListener,
        OfferFragment.OnFragmentInteractionListener, BookFragment.OnFragmentInteractionListener, ReviewFragment.OnFragmentInteractionListener, AdapterView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    private ImageView writeReview;
    private ImageView openNavigation;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        initializeView();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(mNavListener);

        drawerLayout = findViewById(R.id.drawer);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

        writeReview = findViewById(R.id.review);
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WriteReviewActivity.class));
            }
        });

        openNavigation = findViewById(R.id.openSideNav);
        openNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void initializeView() {

        Spinner locationSpinner = findViewById(R.id.locationSpinner);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        // attaching data adapter to spinner
        locationSpinner.setAdapter(dataAdapter);

        locationSpinner.setOnItemSelectedListener(this);

        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.super.onSearchRequested();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_restaurants:
                            selectedFragment = new RestaurantFragment();
                            break;

                        case R.id.nav_offer:
                            selectedFragment = new OfferFragment();
                            break;

                        case R.id.nav_book:
                            selectedFragment = new BookFragment();
                            break;

                        case R.id.nav_review:
                            selectedFragment = new ReviewFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
