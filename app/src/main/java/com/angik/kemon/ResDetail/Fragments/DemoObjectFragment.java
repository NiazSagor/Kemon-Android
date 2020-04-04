package com.angik.kemon.ResDetail.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.AdapterClass.ItemListAdapter;
import com.angik.kemon.AsyncTasks.GetItemTask;
import com.angik.kemon.HelperClass.ResItemDetailClass;
import com.angik.kemon.HelperClass.RestaurantDetailClass;
import com.angik.kemon.Interfaces.ItemDetailCallback;
import com.angik.kemon.Interfaces.ResDetailCallback;
import com.angik.kemon.UtilityClass.DatabaseNameUtility;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DemoObjectFragment extends Fragment {

    private static String DB_ADDRESS = "https://kemon-3d101.firebaseio.com/";

    private Bundle bundle;

    private DatabaseReference databaseReference;

    private ChildEventListener childEventListener;

    public final static String ARG_OBJECT = "TAB_NAME";

    private RecyclerView recyclerView;

    private ItemListAdapter mAdapter;

    private List<RestaurantDetailClass> collectionRes;

    private ProgressBar middleProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        bundle = getArguments();

        //TODO get different database url according to name

        DatabaseNameUtility utility = new DatabaseNameUtility(bundle.getString("name"));

        databaseReference = FirebaseDatabase.getInstance().getReference().child(bundle.getString("name")).child(bundle.getString(ARG_OBJECT));

        middleProgressBar = view.findViewById(R.id.progressBarMiddle);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //new LoadData().execute();

        new GetItemTask(new ItemDetailCallback() {
            @Override
            public void onItemDetailCallback(final List<ResItemDetailClass> itemCollection) {
                mAdapter = new ItemListAdapter(itemCollection);
                recyclerView.setAdapter(mAdapter);

                middleProgressBar.setVisibility(View.GONE);

            }
        }, bundle.getString("name"), bundle.getString(ARG_OBJECT)).execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        detachListener();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void attachListener(final ResDetailCallback callback) {

        collectionRes = new ArrayList<>();

        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    RestaurantDetailClass detailClass = dataSnapshot.getValue(RestaurantDetailClass.class);
                    collectionRes.add(detailClass);

                    callback.onResDetailCallback(collectionRes);

                    middleProgressBar.setVisibility(View.GONE);
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
            };

            databaseReference.addChildEventListener(childEventListener);
        }
    }

    private void detachListener() {
        if (childEventListener != null) {
            databaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }
}
