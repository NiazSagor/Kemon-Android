package com.angik.kemon.AdapterClass;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.HelperClass.DishDetailClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeaturedDishListAdapter extends RecyclerView.Adapter<FeaturedDishListAdapter.FeaturedDishListViewHolder> {

    private List<DishDetailClass> mDishes;

    private OnItemClickListener mListener;

    public FeaturedDishListAdapter(List<DishDetailClass> mDishes) {
        this.mDishes = mDishes;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class FeaturedDishListViewHolder extends RecyclerView.ViewHolder {

        ImageView dishImageView;

        TextView dishNameTextView;
        TextView dishPriceTextView;

        TextView dishResNameTextView;

        public FeaturedDishListViewHolder(@NonNull View itemView, final OnItemClickListener mListener) {
            super(itemView);

            dishImageView = itemView.findViewById(R.id.dishImage);

            dishNameTextView = itemView.findViewById(R.id.dishName);
            dishPriceTextView = itemView.findViewById(R.id.dishPrice);

            dishResNameTextView = itemView.findViewById(R.id.resName);

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
    public FeaturedDishListAdapter.FeaturedDishListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recom_dish_layout, viewGroup, false);
        return new FeaturedDishListViewHolder(layout, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedDishListAdapter.FeaturedDishListViewHolder featuredDishListViewHolder, int i) {

        String dishImageUrl = mDishes.get(i).getDishImageUrl();

        featuredDishListViewHolder.dishNameTextView.setText(mDishes.get(i).getDishName());
        featuredDishListViewHolder.dishPriceTextView.setText(mDishes.get(i).getDishPrice());
        featuredDishListViewHolder.dishResNameTextView.setText(mDishes.get(i).getDishResName());

        Picasso.get()
                .load(dishImageUrl)
                //TODO placeHolder()
                .into(featuredDishListViewHolder.dishImageView);
    }

    @Override
    public int getItemCount() {
        return mDishes.size();
    }
}
