package com.angik.kemon.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.AdapterClass.OfferImageSlidingAdapter;
import com.angik.kemon.AdapterClass.RestaurantListAdapter;
import com.angik.kemon.HelperClass.Common;
import com.angik.kemon.HelperClass.MyApplication;
import com.angik.kemon.ResDetail.ResDetailActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "HomeFragment";
    
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    final Handler handler = new Handler();

    //Offer image sliding matter
    private ViewPager offerImageSlidingViewPager;
    private OfferImageSlidingAdapter mAdapter;
    private Timer timer;
    private int currentPosition = 0;
    //Dots
    private LinearLayout dotLayout;
    private int customPosition = 0;

    //RecyclerView for featured restaurants
    private RecyclerView restRecyclerView;
    private RestaurantListAdapter featuredResAdapter;
    //RecyclerView for featured dishes
    private RecyclerView dishRecyclerView;

    private ImageView restMoreImageView;
    private ImageView dishMoreImageView;

    private int[] dummyImages = {R.drawable.dot, R.drawable.inactivedot};

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        mAdapter = new OfferImageSlidingAdapter(getContext());

        featuredResAdapter = new RestaurantListAdapter(getContext(), Common.restaurants, "featured");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        restRecyclerView = view.findViewById(R.id.featuredResRecyclerView);
        dishRecyclerView = view.findViewById(R.id.recomDishRecyclerView);

        restRecyclerView.setLayoutManager(layoutManager);
        restRecyclerView.setAdapter(featuredResAdapter);

        featuredResAdapter.setOnItemClickListener(new RestaurantListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(getActivity(), ResDetailActivity.class);
                intent.putExtra("name", Common.restaurants.get(position).getResName());
                intent.putExtra("address", Common.restaurants.get(position).getResAddress());
                intent.putExtra("rate", Common.restaurants.get(position).getResRate());
                startActivity(intent);
            }
        });

        restMoreImageView = view.findViewById(R.id.restMore);
        dishMoreImageView = view.findViewById(R.id.dishMore);

        restMoreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO go to another activity to show more featured restaurants
            }
        });

        dishMoreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO go to another activity to show more featured dishes
            }
        });

        dotLayout = view.findViewById(R.id.dotLayout);

        offerImageSlidingViewPager = view.findViewById(R.id.imageSlidingViewPager);
        offerImageSlidingViewPager.setAdapter(mAdapter);

        if (mOnPageChangeListener == null) {
            Log.d(TAG, "listener is null");
            mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    if (customPosition > mAdapter.getCount() - 1) {
                        customPosition = 0;
                    }

                    prepareDots(i++);
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            };
            offerImageSlidingViewPager.addOnPageChangeListener(mOnPageChangeListener);
            Log.d(TAG, "listener is added");
        }

        //TODO change this
        createSlideShow();
        //TODO change this
        prepareDots(customPosition++);

        return view;
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

    private void createSlideShow() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition == mAdapter.getCount()) {
                    currentPosition = 0;
                } else {
                    offerImageSlidingViewPager.setCurrentItem(currentPosition++, true);
                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 2500);
    }

    private void prepareDots(int currentSlidePosition) {
        if (dotLayout.getChildCount() > 0) {
            dotLayout.removeAllViews();
        }

        ImageView[] dots = new ImageView[mAdapter.getCount()];

        for (int i = 0; i < mAdapter.getCount(); i++) {
            dots[i] = new ImageView(MyApplication.getAppContext());

            if (i == currentSlidePosition) {
                //dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot));

                dots[i].setImageResource(dummyImages[0]);
            } else {
                //dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.inactivedot));
                dots[i].setImageResource(dummyImages[1]);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(4, 0, 4, 0);
            dotLayout.addView(dots[i], layoutParams);
        }
    }
}
