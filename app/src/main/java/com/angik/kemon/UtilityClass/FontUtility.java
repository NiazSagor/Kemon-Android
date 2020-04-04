package com.angik.kemon.UtilityClass;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontUtility {

    private TextView mTextView;
    private Context mContext;

    public FontUtility(Context context, TextView textView) {
        mContext = context;
        mTextView = textView;
    }

    public void changeToMedium() {
        mTextView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/montserrat_medium.ttf"));
    }

    public void changeToBold() {
        mTextView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/bontserrat_bold.otf"));
    }
}
