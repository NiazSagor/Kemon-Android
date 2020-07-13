package com.angik.kemon.AsyncTasks;

import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.angik.kemon.HelperClass.ReviewDetailClass;
import com.angik.kemon.Interfaces.ReviewLoadCallback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GetReviewsTask extends AsyncTask<Void, Void, Void> {

    private static String DB_ADDRESS = "https://kemon-3d101.firebaseio.com/";

    private Handler handler = new Handler();

    private ReviewLoadCallback callback;

    private Picasso mPicasso;

    private List<ReviewDetailClass> reviewList = new ArrayList<>();

    public GetReviewsTask(ReviewLoadCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(DB_ADDRESS).child("Reviews");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ReviewDetailClass review = dataSnapshot.getValue(ReviewDetailClass.class);
                reviewList.add(review);
                callback.onReviewLoadCallback(reviewList);
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
