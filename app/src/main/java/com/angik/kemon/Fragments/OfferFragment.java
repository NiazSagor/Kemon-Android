package com.angik.kemon.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.AdapterClass.OfferListAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OfferFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OfferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfferFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText searchOfferEditText;

    private List<OfferDetailClass> mOffers;

    private TextView offerCountTextView;
    private RecyclerView offerRecyclerView;
    private OfferListAdapter mAdapter;

    private Picasso mPicasso;

    private DatabaseReference db_offer;
    private ChildEventListener mChildEventListener;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public OfferFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OfferFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OfferFragment newInstance(String param1, String param2) {
        OfferFragment fragment = new OfferFragment();
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
        mPicasso = Picasso.get();
        db_offer = FirebaseDatabase.getInstance().getReference().child("Offer");
        new GetAllOfferTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer, container, false);

        offerCountTextView = view.findViewById(R.id.offerCountTextView);

        offerRecyclerView = view.findViewById(R.id.offerRecyclerView);

        progressBar = view.findViewById(R.id.progressBar);

        mOffers = new ArrayList<>();

        return view;
    }

    private void addListener(final OfferCallback callback) {
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                OfferDetailClass offer = dataSnapshot.getValue(OfferDetailClass.class);
                mOffers.add(offer);
                assert offer != null;
                mPicasso.load(offer.getOfferImageUrl()).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        callback.onOfferCallback(mOffers);
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
        };

        db_offer.addChildEventListener(mChildEventListener);
    }

    private void detachListener() {
        db_offer.removeEventListener(mChildEventListener);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        detachListener();
    }

    private class GetAllOfferTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            addListener(new OfferCallback() {
                @Override
                public void onOfferCallback(List<OfferDetailClass> offers) {
                    mAdapter = new OfferListAdapter(offers);
                    offerRecyclerView.setAdapter(mAdapter);
                    offerCountTextView.setText("Looking at " + mAdapter.getItemCount() +" offers to discover");
                    progressBar.setVisibility(View.GONE);
                }
            });

            return null;
        }
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
