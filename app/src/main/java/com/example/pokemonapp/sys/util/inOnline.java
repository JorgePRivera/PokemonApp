package com.example.pokemonapp.sys.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class inOnline {
    public static boolean checkNetwork(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }
}
