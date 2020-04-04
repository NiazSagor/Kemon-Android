package com.angik.kemon.AdapterClass;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.HelperClass.BookDetailClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookListViewHolder> {

    private List<BookDetailClass> mBooks;

    private OnItemClickListener mListener;

    public BookListAdapter(List<BookDetailClass> mBooks) {
        this.mBooks = mBooks;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class BookListViewHolder extends RecyclerView.ViewHolder {

        ImageView resImageView;

        TextView resNameTextView;
        TextView resAddressTextView;
        TextView resPrePayTextView;

        public BookListViewHolder(@NonNull View itemView, final OnItemClickListener mListener) {
            super(itemView);

            resImageView = itemView.findViewById(R.id.resImage);

            resNameTextView = itemView.findViewById(R.id.resName);
            resAddressTextView = itemView.findViewById(R.id.resAddress);
            resPrePayTextView = itemView.findViewById(R.id.prePayTextView);

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
    public BookListAdapter.BookListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.res_book_layout, viewGroup, false);
        return new BookListViewHolder(layout, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.BookListViewHolder bookListViewHolder, int i) {
        String resImageUrl = mBooks.get(i).getResImageUrl();

        bookListViewHolder.resNameTextView.setText(mBooks.get(i).getResName());
        bookListViewHolder.resAddressTextView.setText(mBooks.get(i).getResName());
        bookListViewHolder.resPrePayTextView.setText(mBooks.get(i).getPrePayment());

        Picasso.get()
                .load(resImageUrl)
                //TODO placeHolder()
                .into(bookListViewHolder.resImageView);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public void filterList(List<BookDetailClass> filteredList) {
        mBooks = filteredList;
        notifyDataSetChanged();
    }
}
