package com.angik.kemon.AdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.HelperClass.Common;
import com.angik.kemon.HelperClass.OfferDetailClass;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferImageSlidingAdapter extends PagerAdapter {

    private boolean doNotifyDataSetChangedOnce = false;

    private List<OfferDetailClass> mOffers;

    private Context mContext;

    private Picasso mPicasso;

    public OfferImageSlidingAdapter(Context context) {
        this.mContext = context;
        //getOffers();
        mOffers = Common.offers;

        doNotifyDataSetChangedOnce = true;

        mPicasso = Picasso.get();
    }

    @Override
    public int getCount() {
        //return dummyImages.length;

        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
            notifyDataSetChanged();
        }

        return mOffers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (RelativeLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.offer_image_sliding_layout, container, false);

        final ImageView offerImage = view.findViewById(R.id.offerImageView);

        mPicasso.load(mOffers.get(position).getOfferImageUrl())
                .into(offerImage);


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    private void getOffers() {
        final DatabaseReference offers = FirebaseDatabase.getInstance().getReference().child("sample node");

        new Thread(new Runnable() {
            @Override
            public void run() {
                offers.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        OfferDetailClass offer = dataSnapshot.getValue(OfferDetailClass.class);

                        mOffers.add(offer);

                        doNotifyDataSetChangedOnce = true;

                        //notifyDataSetChanged();
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
        }).start();
    }
}
