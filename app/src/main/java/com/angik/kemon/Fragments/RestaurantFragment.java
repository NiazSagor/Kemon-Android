package com.angik.kemon.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.AdapterClass.RestaurantListAdapter;
import com.angik.kemon.HelperClass.Common;
import com.angik.kemon.HelperClass.RestaurantDetailClass;
import com.angik.kemon.Interfaces.ResCallback;
import com.angik.kemon.ResDetail.ResDetailActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment {

    private static final String TAG = "RestaurantFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText searchRestaurantEditText;
    private TextView resCountTextView;
    private RecyclerView resRecyclerView;
    private LinearLayoutManager manager;
    private ProgressBar progressBar;

    private RestaurantListAdapter mAdapter;

    private List<RestaurantDetailClass> mRestaurants;

    private Boolean isScrolling = false;

    private int currentItems, totalItems, scrollOutItems;

    private Picasso mPicasso;

    private OnFragmentInteractionListener mListener;

    //NEW
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Restaurants");
    private String oldestPostId;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mRestaurants = new ArrayList<>();

        mRestaurants = Common.allRestaurants;

        mPicasso = Picasso.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        resCountTextView = view.findViewById(R.id.resCountTextView);

        resRecyclerView = view.findViewById(R.id.restaurantRecyclerView);

        progressBar = view.findViewById(R.id.progressBar);

        manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        resRecyclerView.setLayoutManager(manager);
        //addListener();

        /*

        //NEW
        getData(new ResCallback() {
            @Override
            public void onResCallback(List<RestaurantDetailClass> resCollection) {
                mAdapter = new RestaurantListAdapter(getContext(), resCollection, "restaurant");
                resRecyclerView.setAdapter(mAdapter);
                progressBar.setVisibility(View.GONE);

                mAdapter.setOnItemClickListener(new RestaurantListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View view) {
                        Intent intent = new Intent(getActivity(), ResDetailActivity.class);
                        intent.putExtra("name", Common.restaurants.get(position).getResName());
                        intent.putExtra("address", Common.restaurants.get(position).getResAddress());
                        intent.putExtra("rate", Common.restaurants.get(position).getResRate());
                        startActivity(intent);
                    }
                });

                //Log.d(TAG, "onResCallback: " + oldestPostId);
            }
        });*/


      /*  getData(new ImageLoadCallback() {
            @Override
            public void onImageLoadCallback() {
                progressBar.setVisibility(View.GONE);
            }
        });*/

        mAdapter = new RestaurantListAdapter(getContext(), mRestaurants, "restaurant");
        resRecyclerView.setAdapter(mAdapter);
        progressBar.setVisibility(View.GONE);

        mAdapter.setOnItemClickListener(new RestaurantListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(getActivity(), ResDetailActivity.class);
                intent.putExtra("name", mRestaurants.get(position).getResName());
                intent.putExtra("address", mRestaurants.get(position).getResAddress());
                intent.putExtra("rate", mRestaurants.get(position).getResRate());
                startActivity(intent);
            }
        });

        resCountTextView.setText("Looking at " + mRestaurants.size() + " restaurants to discover");

        searchRestaurantEditText = view.findViewById(R.id.searchEditText);
        searchRestaurantEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        return view;
    }

    private void filter(String givenString) {
        List<RestaurantDetailClass> filteredList = new ArrayList<>();


        for (RestaurantDetailClass item : mRestaurants) {
            if (item.getResName().toLowerCase().contains(givenString.toLowerCase())) {
                filteredList.add(item);
            }
        }

        //TODO add a method in the adapter class to pass the filtered list
        mAdapter.filterList(filteredList);
    }

    private void addListener() {
        resRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                Log.d(TAG, "isScrolling " + isScrolling);

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    progressBar.setVisibility(View.VISIBLE);
                    getData_2(new ResCallback() {
                        @Override
                        public void onResCallback(List<RestaurantDetailClass> resCollection) {
                            mAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }

            }
        });
    }

    //NEW
    private void getData(final ResCallback callback) {
        db.limitToFirst(5).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                RestaurantDetailClass data = dataSnapshot.getValue(RestaurantDetailClass.class);
                mRestaurants.add(data);
                assert data != null;
                mPicasso.load(data.getResImageUrl()).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        oldestPostId = dataSnapshot.getKey();
                        callback.onResCallback(mRestaurants);
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

    //NEW
    private void getData_2(final ResCallback callback) {
        db.orderByKey().startAt(oldestPostId).limitToFirst(5).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                RestaurantDetailClass data = dataSnapshot.getValue(RestaurantDetailClass.class);
                mRestaurants.add(data);
                assert data != null;
                mPicasso.load(data.getResImageUrl()).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        oldestPostId = dataSnapshot.getKey();
                        callback.onResCallback(mRestaurants);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
