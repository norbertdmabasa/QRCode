package com.filmetrics.eqrcodeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.filmetrics.eqrcodeapp.adapters.ProductDetailAdapter;
import com.filmetrics.eqrcodeapp.qrcode.BarcodeCaptureActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int RC_BARCODE_CAPTURE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

        startActivityForResult(intent, RC_BARCODE_CAPTURE);

        finish();
    }
}
