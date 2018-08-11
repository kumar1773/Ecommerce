package com.heady.ecommerce.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;

import com.heady.ecommerce.R;
import com.heady.ecommerce.data.source.local.Entity.ProductEntity;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Utils {
    private static final int UPPER_BOUND = 5;
    public static Random random = new Random();

    public static boolean isNetworkAvailable(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static int getRandomColor(Context context) {
        switch (random.nextInt(UPPER_BOUND)) {
            case 0:
                return ContextCompat.getColor(context, R.color.mat_0);
            case 1:
                return ContextCompat.getColor(context, R.color.mat_1);
            case 2:
                return ContextCompat.getColor(context, R.color.mat_2);
            case 3:
                return ContextCompat.getColor(context, R.color.mat_3);
            case 4:
                return ContextCompat.getColor(context, R.color.mat_4);
            case 5:
                return ContextCompat.getColor(context, R.color.mat_5);
            case 6:
                return ContextCompat.getColor(context, R.color.mat_6);
            case 7:
                return ContextCompat.getColor(context, R.color.mat_7);
            case 8:
                return ContextCompat.getColor(context, R.color.mat_8);
            case 9:
                return ContextCompat.getColor(context, R.color.mat_9);
            default:
                return ContextCompat.getColor(context, R.color.mat_0);
        }
    }

    public static boolean isValidCollection(Collection list) {
        return list != null && list.size() > 0;
    }
}
