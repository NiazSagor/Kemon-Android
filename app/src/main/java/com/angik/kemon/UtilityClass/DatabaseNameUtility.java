package com.angik.kemon.UtilityClass;

import android.content.Context;
import android.util.Log;

import com.angik.fooduprestaurant.R;
import com.angik.kemon.HelperClass.MyApplication;

public class DatabaseNameUtility {

    private static final String TAG = "DatabaseNameUtility";

    private Context mContext;
    private String url;

    public DatabaseNameUtility(String resName) {
        mContext = MyApplication.getAppContext();
        setDatabaseURL(resName);
    }

    private void setDatabaseURL(String mResName) {
        switch (mResName) {
            case "Bellpepper":
                url = mContext.getResources().getString(R.string.bellpepper);
                break;
            case "Afghan":
                url = mContext.getResources().getString(R.string.bellpepper);
                break;
            case "":
                url = mContext.getResources().getString(R.string.bellpepper);
                break;
            case "Apon":
                url = mContext.getResources().getString(R.string.bellpepper);
                break;
            case "Barcode":
                url = mContext.getResources().getString(R.string.bellpepper);
                break;

            default:
                url = null;

        }
    }

    public String getDatabaseUrl() {
        return url;
    }
}
