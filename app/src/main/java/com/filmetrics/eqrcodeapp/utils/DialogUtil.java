package com.filmetrics.eqrcodeapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by NMABASA on 12/18/2017.
 */

public class DialogUtil {
    public static void AlertDialog(DialogInterface.OnClickListener dialogClickListener, Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public static void ErrDialog(DialogInterface.OnClickListener dialogClickListener, Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setPositiveButton("Ok", dialogClickListener).show();
    }
}
