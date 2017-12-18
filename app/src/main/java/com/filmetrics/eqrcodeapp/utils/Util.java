package com.filmetrics.eqrcodeapp.utils;

import android.widget.EditText;

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
}
