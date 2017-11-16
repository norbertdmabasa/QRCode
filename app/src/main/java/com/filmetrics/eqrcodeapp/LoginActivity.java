package com.filmetrics.eqrcodeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameTxt;
    private EditText passwordTxt;
    private TextView registerHereTxt;
    private TextView forgotTxt;
    private TextView guestTxt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTxt = (EditText) findViewById(R.id.username_txt_log);
        passwordTxt = (EditText) findViewById(R.id.password_txt_log);

        registerHereTxt = (TextView) findViewById(R.id.registerhere_txt);
        forgotTxt = (TextView) findViewById(R.id.forgotpass_txt_log);
        guestTxt = (TextView) findViewById(R.id.guest_txt_log);
        registerHereTxt.setOnClickListener(this);
        forgotTxt.setOnClickListener(this);
        guestTxt.setOnClickListener(this);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                onLogin();
                return;
            case R.id.registerhere_txt:
                onRegHere();
                return;
            case R.id.forgotpass_txt_log:
                onForgotPass();
                return;
            case R.id.guest_txt_log:
                onGuest();
                return;
            default:
                return;
        }
    }

    private void onLogin() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void onRegHere() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void onForgotPass() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void onGuest() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
