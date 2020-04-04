package com.angik.kemon.AdapterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.HelperClass.ResItemDetailClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    private List<ResItemDetailClass> mResItemCollection;

    private OnItemClickListener mListener;

    private Picasso mPicasso;

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class ItemListViewHolder extends RecyclerView.ViewHolder {

        TextView mItemName;
        TextView mItemPrice;
        ImageView mItemImageView;

        ItemListViewHolder(View itemView, final OnItemClickListener mListener) {
            super(itemView);

            mItemName = itemView.findViewById(R.id.itemName);
            mItemPrice = itemView.findViewById(R.id.itemPrice);
            mItemImageView = itemView.findViewById(R.id.itemImage);

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


    public ItemListAdapter(List<ResItemDetailClass> resItemCollection) {
        this.mResItemCollection = resItemCollection;
        mPicasso = Picasso.get();
    }


    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.res_item_layout, viewGroup, false);
        return new ItemListViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder myViewHolder, int i) {

        myViewHolder.mItemName.setText(mResItemCollection.get(i).getItemName());
        myViewHolder.mItemPrice.setText(mResItemCollection.get(i).getItemPrice());

        if (mResItemCollection.get(i).getItemImageUrl() == null) {
            myViewHolder.mItemImageView.setVisibility(View.GONE);
        } else {
            mPicasso.load(mResItemCollection.get(i).getItemImageUrl())
                    .into(myViewHolder.mItemImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mResItemCollection.size();
    }
}
