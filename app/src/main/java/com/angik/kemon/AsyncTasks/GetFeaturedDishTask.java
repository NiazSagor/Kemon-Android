package com.angik.kemon.AsyncTasks;

import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.angik.kemon.HelperClass.DishDetailClass;
import com.angik.kemon.Interfaces.DishCallback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GetFeaturedDishTask extends AsyncTask<Void, Void, Void> {

    private static String DB_ADDRESS = "https://kemon-3d101.firebaseio.com/";

    private Handler handler = new Handler();

    private DishCallback callback;

    private Picasso mPicasso;

    private List<DishDetailClass> featuredDish = new ArrayList<>();

    public GetFeaturedDishTask(DishCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        mPicasso = Picasso.get();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(DB_ADDRESS).child("Featured Dishes");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DishDetailClass dish = dataSnapshot.getValue(DishDetailClass.class);
                featuredDish.add(dish);

                assert dish != null;
                mPicasso.load(dish.getDishImageUrl()).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        callback.onDishCallback(featuredDish);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }
}
