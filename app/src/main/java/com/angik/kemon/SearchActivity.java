package com.angik.kemon;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.AdapterClass.RestaurantListAdapter;
import com.angik.kemon.HelperClass.Common;
import com.angik.kemon.HelperClass.RestaurantDetailClass;
import com.angik.kemon.Interfaces.ImageLoadCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<RestaurantDetailClass> filteredList;

    private RecyclerView searchResultRecyclerView;
    private RestaurantListAdapter mAdapter;

    private TextView searchResultTextView;
    private ProgressBar progressBar;

    private Picasso mPicasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchResultRecyclerView = findViewById(R.id.searchResultRecyclerView);
        searchResultTextView = findViewById(R.id.searchResultTextView);

        progressBar = findViewById(R.id.progressBar);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        searchResultRecyclerView.setLayoutManager(manager);

        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String searchQuery = intent.getStringExtra(SearchManager.QUERY);

            filteredList = new ArrayList<>();

            for (RestaurantDetailClass item : Common.allRestaurants) {
                if (item.getResName().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            if(filteredList.size() == 0){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Sorry no match found", Toast.LENGTH_SHORT).show();
                return;
            }

            getImage(new ImageLoadCallback() {
                @Override
                public void onImageLoadCallback() {
                    mAdapter = new RestaurantListAdapter(SearchActivity.this, filteredList, "restaurant");
                    searchResultRecyclerView.setAdapter(mAdapter);
                    progressBar.setVisibility(View.GONE);

                    String searchText = mAdapter.getItemCount() + " Results Found";

                    searchResultTextView.setText(searchText);
                }
            });
        }
    }

    private void getImage(final ImageLoadCallback callback) {
        mPicasso = Picasso.get();
        for (int i = 0; i < filteredList.size(); i++) {
            mPicasso.load(filteredList.get(i).getResImageUrl()).fetch(new Callback() {
                @Override
                public void onSuccess() {
                    callback.onImageLoadCallback();
                }

                @Override
                public void onError(Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SearchActivity.this, "Sorry no match found", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
