package com.filmetrics.eqrcodeapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.filmetrics.eqrcodeapp.webservice.WebServiceInterface;
import com.koushikdutta.ion.Ion;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, WebServiceInterface{
    private EditText usernameTxt;
    private EditText passwordTxt;
    private TextView registerHereTxt;
    private TextView forgotTxt;
    private TextView guestTxt;
    private Button loginBtn;
    private ImageView loadingImg;

    private TextInputLayout emailtxtLayout;
    private TextInputLayout passtxtLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTxt = findViewById(R.id.username_txt_log);
        passwordTxt = findViewById(R.id.password_txt_log);

        emailtxtLayout = findViewById(R.id.username_txt_layout);
        passtxtLayout = findViewById(R.id.password_txt_layout);

        emailtxtLayout.setHintAnimationEnabled(false);
        passtxtLayout.setHintAnimationEnabled(false);

        registerHereTxt = findViewById(R.id.registerhere_txt);
        forgotTxt = findViewById(R.id.forgotpass_txt_log);
//        guestTxt = findViewById(R.id.guest_txt_log);
        registerHereTxt.setOnClickListener(this);
        forgotTxt.setOnClickListener(this);
//        guestTxt.setOnClickListener(this);

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

        loadingImg = findViewById(R.id.loading_img_log);

        Ion.with(loadingImg)
                .centerCrop()
                .load("android.resource://" + getPackageName() + "/" + R.drawable.loading);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                onClickLoginBtn();
                return;
            case R.id.registerhere_txt:
                onRegHere();
                return;
            case R.id.forgotpass_txt_log:
                onForgotPass();
                return;
//            case R.id.guest_txt_log:
//                onGuest();
//                return;
            default:
                return;
        }
    }

    private void onClickLoginBtn() {
        loadingImg.setVisibility(View.VISIBLE);

        String user = usernameTxt.getText().toString();
        String pass = passwordTxt.getText().toString();
        boolean userEmpty = Util.isEmpty(usernameTxt);
        boolean passEmpty = Util.isEmpty(passwordTxt);
        if(userEmpty) {
            emailtxtLayout.setError(" ");
        }

        if(passEmpty) {
            passtxtLayout.setError(" ");
        }

        if(userEmpty || userEmpty) {

        } else if(android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {

        } else {
            doLogin();
        }
    }

    private void doLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("User" , "");
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

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onPostExecute(Object o) {

    }
}
