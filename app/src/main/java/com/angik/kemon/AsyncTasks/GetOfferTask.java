package com.angik.kemon.AsyncTasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.angik.kemon.HelperClass.OfferDetailClass;
import com.angik.kemon.Interfaces.OfferCallback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GetOfferTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "GetOfferTask";
    
    private static String DB_ADDRESS = "https://kemon-3d101.firebaseio.com/";

    private Handler handler = new Handler();

    private OfferCallback callback;
    private String mNode;

    private Picasso mPicasso;

    public GetOfferTask(OfferCallback back, String node) {
        this.callback = back;
        this.mNode = node;
    }

    List<OfferDetailClass> list = new ArrayList<>();


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND + Process.THREAD_PRIORITY_MORE_FAVORABLE);

        mPicasso = Picasso.get();

        handler.post(new Runnable() {
            @Override
            public void run() {
                
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(DB_ADDRESS).child(mNode);

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        final OfferDetailClass offer = dataSnapshot.getValue(OfferDetailClass.class);
                        list.add(offer);
                        assert offer != null;
                        mPicasso.load(offer.getOfferImageUrl()).fetch(new Callback() {
                            @Override
                            public void onSuccess() {
                                callback.onOfferCallback(list);
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
            }

        });

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
