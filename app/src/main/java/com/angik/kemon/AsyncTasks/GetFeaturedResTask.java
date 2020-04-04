package com.angik.kemon.AsyncTasks;

import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.angik.kemon.HelperClass.OfferDetailClass;
import com.angik.kemon.HelperClass.RestaurantDetailClass;
import com.angik.kemon.Interfaces.OfferCallback;
import com.angik.kemon.Interfaces.ResDetailCallback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GetFeaturedResTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "GetOfferTask";

    private static String DB_ADDRESS = "https://kemon-3d101.firebaseio.com/";

    Handler handler = new Handler();

    ResDetailCallback callback;
    String mNode;

    GetFeaturedResTask() {

    }

    public GetFeaturedResTask(ResDetailCallback back, String node) {
        this.callback = back;
        this.mNode = node;
    }

    List<RestaurantDetailClass> list = new ArrayList<>();


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(DB_ADDRESS).child(mNode);

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        RestaurantDetailClass offer = dataSnapshot.getValue(RestaurantDetailClass.class);
                        list.add(offer);
                        callback.onResDetailCallback(list);
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
            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
