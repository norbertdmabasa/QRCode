package com.filmetrics.eqrcodeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {
    private Button forgetBtn;
    private Button returnLogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resetpass_btn_fgt:
                onClickReset();
                return;
            case R.id.returnlog_btn_fgt:
                onClickRetLog();
                return;
            default:
                return;
        }
    }

    private void onClickReset() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void onClickRetLog() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
