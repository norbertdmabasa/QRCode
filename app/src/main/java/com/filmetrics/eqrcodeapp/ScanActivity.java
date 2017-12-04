package com.filmetrics.eqrcodeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScanActivity extends Base2Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_scan, frameLayout);
    }
}
