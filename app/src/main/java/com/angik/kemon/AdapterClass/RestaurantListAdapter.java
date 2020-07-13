package com.angik.kemon.AdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.HelperClass.RestaurantDetailClass;
import com.angik.kemon.UtilityClass.FontUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantListViewHolder> {

    private static int FEATURED = 1;
    private static int NON_FEATURED = 2;

    private Context mContext;
    private List<RestaurantDetailClass> mRestaurants;
    private Picasso mPicasso;

    private String mSender;

    private OnItemClickListener mListener;

    public RestaurantListAdapter(Context context, List<RestaurantDetailClass> mRestaurants, String sender) {
        mContext = context;
        this.mRestaurants = mRestaurants;
        mSender = sender;
        mPicasso = Picasso.get();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class RestaurantListViewHolder extends RecyclerView.ViewHolder {

        ImageView resImageView;

        TextView resNameTextView;
        TextView resAddressTextView;
        TextView resRateTextView;

        public RestaurantListViewHolder(Context mContext, @NonNull View itemView, final OnItemClickListener mListener) {
            super(itemView);

            resImageView = itemView.findViewById(R.id.resImage);
            resNameTextView = itemView.findViewById(R.id.resName);
            resAddressTextView = itemView.findViewById(R.id.resAddress);
            resRateTextView = itemView.findViewById(R.id.resRate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if (mListener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position, view);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSender.equals("featured")) {
            return FEATURED;
        } else if (mSender.equals("restaurant")) {
            return NON_FEATURED;
        }
        return -1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RestaurantListAdapter.RestaurantListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (i) {
            case 1:
                View view = inflater.inflate(R.layout.res_detail_layout, viewGroup, false);
                return new RestaurantListViewHolder(mContext, view, mListener);

            case 2:
                View view_2 = inflater.inflate(R.layout.new_res_detail_non_featured, viewGroup, false);
                return new RestaurantListViewHolder(mContext, view_2, mListener);

            default:
                View view_3 = inflater.inflate(R.layout.res_detail_layout_non_featured, viewGroup, false);
                return new RestaurantListViewHolder(mContext, view_3, mListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.RestaurantListViewHolder restaurantListViewHolder, final int i) {

        String rateString = "" + mRestaurants.get(i).getResRate();
        String resImageUrl = mRestaurants.get(i).getResImageUrl();

        restaurantListViewHolder.resNameTextView.setText(mRestaurants.get(i).getResName());
        restaurantListViewHolder.resAddressTextView.setText(mRestaurants.get(i).getResAddress());
        restaurantListViewHolder.resRateTextView.setText(rateString);
        new FontUtility(mContext, restaurantListViewHolder.resRateTextView).changeToMedium();

        if (resImageUrl != null) {
            mPicasso.load(resImageUrl)
                    //TODO .placeholder()
                    .fit()
                    .into(restaurantListViewHolder.resImageView);
        } else {
            restaurantListViewHolder.resImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public void filterList(List<RestaurantDetailClass> filteredList) {
        mRestaurants = filteredList;
        notifyDataSetChanged();
    }
}
