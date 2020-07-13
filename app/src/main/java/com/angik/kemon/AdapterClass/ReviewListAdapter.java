package com.angik.kemon.AdapterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.HelperClass.ReviewDetailClass;
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView;

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

        RatingBar averageRate;

        NameInitialsCircleImageView profileImage;

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

            profileImage = itemView.findViewById(R.id.profileImage);

            averageRate = itemView.findViewById(R.id.averageRate);

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

        setThumbnail(reviewListViewHolder, i);
        setAverage(reviewListViewHolder, i);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    private void setThumbnail(ReviewListAdapter.ReviewListViewHolder reviewListViewHolder, int i) {
        String[] split = mReviews.get(i).getUserName().split("\\s+");
        String initials = split[0].charAt(0) + "" + split[1].charAt(0);
        NameInitialsCircleImageView.ImageInfo imageInfo = new NameInitialsCircleImageView.ImageInfo
                .Builder(initials)
                .build();
        reviewListViewHolder.profileImage.setImageInfo(imageInfo);
    }

    private void setAverage(ReviewListAdapter.ReviewListViewHolder reviewListViewHolder, int i) {
        float average = (float) (mReviews.get(i).getEnvironmentRate()
                + mReviews.get(i).getBehaviourRate()
                + mReviews.get(i).getServiceRate()) / 3;

        reviewListViewHolder.averageRate.setRating(average);
    }
}
