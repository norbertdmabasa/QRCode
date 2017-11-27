package com.filmetrics.eqrcodeapp;

import android.widget.EditText;

/**
 * Created by NMABASA on 11/27/2017.
 */

public class Util {
    public static boolean isEmpty(EditText editText) {
        if(editText.getText() != null && editText.getText().toString().trim().equals("")) {
            return true;
        }
        return false;
    }
}
