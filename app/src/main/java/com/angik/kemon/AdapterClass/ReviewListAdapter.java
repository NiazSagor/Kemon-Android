package com.angik.kemon.AdapterClass;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.HelperClass.ReviewDetailClass;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder> {

    private List<ReviewDetailClass> mReviews;

    private OnItemClickListener mListener;

    public ReviewListAdapter(List<ReviewDetailClass> mReviews) {
        this.mReviews = mReviews;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ReviewListViewHolder extends RecyclerView.ViewHolder {

        TextView reviewDate;
        TextView userName;
        TextView userReview;

        TextView resName;
        TextView itemName;
        TextView itemPrice;

        TextView serviceRating;
        TextView environmentRating;
        TextView behaviourRating;

        TextView photoAvailableTextView;

        public ReviewListViewHolder(@NonNull View itemView, final OnItemClickListener mListener) {
            super(itemView);

            resName = itemView.findViewById(R.id.resName);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);

            reviewDate = itemView.findViewById(R.id.reviewDate);
            userName = itemView.findViewById(R.id.userName);
            userReview = itemView.findViewById(R.id.userReview);

            serviceRating = itemView.findViewById(R.id.serviceRating);
            environmentRating = itemView.findViewById(R.id.environmentRating);
            behaviourRating = itemView.findViewById(R.id.behaviourRating);

            photoAvailableTextView = itemView.findViewById(R.id.photoSelectionTextView);

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
    public ReviewListAdapter.ReviewListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_layout, viewGroup, false);
        return new ReviewListViewHolder(layout, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.ReviewListViewHolder reviewListViewHolder, int i) {

        String serviceRateString = "" + mReviews.get(i).getServiceRate();
        String environmentRateString = "" + mReviews.get(i).getEnvironmentRate();
        String behaviourRateString = "" + mReviews.get(i).getBehaviourRate();

        reviewListViewHolder.userName.setText(mReviews.get(i).getUserName());
        reviewListViewHolder.reviewDate.setText(mReviews.get(i).getPostingDate());
        reviewListViewHolder.userReview.setText(mReviews.get(i).getUserReview());

        reviewListViewHolder.resName.setText(mReviews.get(i).getReviewedResName());
        reviewListViewHolder.itemName.setText(mReviews.get(i).getReviewedItemName());
        reviewListViewHolder.itemPrice.setText(mReviews.get(i).getReviewedItemPrice());

        reviewListViewHolder.serviceRating.setText(serviceRateString);
        reviewListViewHolder.environmentRating.setText(environmentRateString);
        reviewListViewHolder.behaviourRating.setText(behaviourRateString);

        if (mReviews.get(i).getReviewPhotoText() == null) {
            reviewListViewHolder.photoAvailableTextView.setVisibility(View.GONE);
        } else {
            reviewListViewHolder.photoAvailableTextView.setText(mReviews.get(i).getReviewPhotoText());
        }
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }
}
