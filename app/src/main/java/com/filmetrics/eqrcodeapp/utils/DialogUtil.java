package com.filmetrics.eqrcodeapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by NMABASA on 12/18/2017.
 */

public class DialogUtil {
    public static void AlertYNDialog(DialogInterface.OnClickListener dialogClickListener, Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public static void Dialog(DialogInterface.OnClickListener dialogClickListener, Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setPositiveButton("Ok", dialogClickListener).show();
    }

    public static android.support.v7.app.AlertDialog alertOkDialog(Context context, String msg, String title) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);

        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alert = builder.create();
        return alert;
    }
}
