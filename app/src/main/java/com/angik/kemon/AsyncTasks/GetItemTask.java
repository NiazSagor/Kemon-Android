package com.angik.kemon.AsyncTasks;

import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.angik.kemon.HelperClass.ResItemDetailClass;
import com.angik.kemon.Interfaces.ItemDetailCallback;
import com.angik.kemon.ResDetail.Fragments.DemoObjectFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GetItemTask extends AsyncTask<Void, Void, Void> {

    private static String DB_ADDRESS = "https://kemon-3d101.firebaseio.com/";

    private Handler handler = new Handler();

    private ItemDetailCallback callback;
    private String mNode;
    private String mResName;

    public GetItemTask(ItemDetailCallback back, String resName, String node) {
        this.callback = back;
        this.mResName = resName;
        this.mNode = node;
    }

    List<ResItemDetailClass> list = new ArrayList<>();

    DemoObjectFragment fragment = new DemoObjectFragment();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        handler.post(new Runnable() {
            @Override
            public void run() {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(DB_ADDRESS).child(mResName).child(mNode);

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        ResItemDetailClass detailClass = dataSnapshot.getValue(ResItemDetailClass.class);
                        list.add(detailClass);
                        callback.onItemDetailCallback(list);
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
