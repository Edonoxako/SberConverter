package com.edonoxako.sber.sberconverter.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Eugeny.Martinenko on 11.07.2017.
 */

public class DefaultNetworkChecker implements NetworkChecker {

    private Context context;

    public DefaultNetworkChecker(Context context) {
        this.context = context;
    }

    @Override
    public boolean networkIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
