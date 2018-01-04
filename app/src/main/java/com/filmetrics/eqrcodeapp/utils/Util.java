package com.filmetrics.eqrcodeapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import com.filmetrics.eqrcodeapp.MainActivity;

/**
 * Created by NMABASA on 11/27/2017.
 */

public class Util {
    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public static boolean isEmpty(String editText) {
        if(editText != null && editText.toString().trim().equals("")) {
            return true;
        }

        return false;
    }

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static String webApiParam(String key, String value, String end) {
        String res = key + "=" + value + "" + end;
        return res;
    }

    public static String[] getPDetails(Context context) {
        String[] res = new String[] {
                "0","0","0"
        };
        String address = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");

        if(address == null) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            address = wInfo.getMacAddress();
        }

        res[0] = address;
        res[1] = android.os.Build.MODEL;
        res[2]= Build.MANUFACTURER;
        return res;
    }

    public static boolean checkConnect(Context context) {
        ConnectivityManager ConnectionManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
//            Toast.makeText(context, "Network Available", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(context, "Network Not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
