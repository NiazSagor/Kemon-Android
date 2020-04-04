package com.angik.kemon.AdapterClass;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.HelperClass.OfferDetailClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.OfferListViewHolder> {

    private List<OfferDetailClass> mOffers;

    private OnItemClickListener mListener;

    public OfferListAdapter(List<OfferDetailClass> offers) {
        this.mOffers = offers;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class OfferListViewHolder extends RecyclerView.ViewHolder {

        ImageView offerImageView;

        public OfferListViewHolder(@NonNull View itemView, final OnItemClickListener mListener) {
            super(itemView);

            offerImageView = itemView.findViewById(R.id.offerImageView);

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

    @NonNull
    @Override
    public OfferListAdapter.OfferListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_layout, viewGroup, false);
        return new OfferListViewHolder(layout, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferListViewHolder offerListViewHolder, int i) {
        String offerImageUrl = mOffers.get(i).getOfferImageUrl();

        Picasso.get()
                .load(offerImageUrl)
                //TODO placeHolder()
                .into(offerListViewHolder.offerImageView);
    }

    @Override
    public int getItemCount() {
        return mOffers.size();
    }

    public void filterList(List<OfferDetailClass> filteredList) {
        mOffers = filteredList;
        notifyDataSetChanged();
    }
}
