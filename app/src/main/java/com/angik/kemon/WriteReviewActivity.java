package com.angik.kemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.angik.fooduprestaurant.R;

import java.util.Objects;

public class WriteReviewActivity extends AppCompatActivity {

    private EditText itemEditText;
    private EditText itemPriceEditText;
    private EditText restaurantEditText;

    private RatingBar service, behaviour, environment;

    private EditText review;

    private CardView postCardView;

    private TextView addPhotoTextView;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        itemEditText = findViewById(R.id.itemName);
        itemPriceEditText = findViewById(R.id.itemPrice);
        restaurantEditText = findViewById(R.id.resName);

        service = findViewById(R.id.serviceRating);
        behaviour = findViewById(R.id.behaviourRating);
        environment = findViewById(R.id.environmentRating);

        review = findViewById(R.id.writeReviewEditText);

        postCardView = findViewById(R.id.postCardView);

        postCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addPhotoTextView = findViewById(R.id.addPhotoTextView);
    }
}
