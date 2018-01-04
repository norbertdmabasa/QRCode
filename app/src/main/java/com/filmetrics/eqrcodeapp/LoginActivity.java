package com.filmetrics.eqrcodeapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.filmetrics.eqrcodeapp.utils.Util;
import com.filmetrics.eqrcodeapp.webservice.HttpRequestTask;
import com.filmetrics.eqrcodeapp.webservice.ServiceCallInterface;
import com.koushikdutta.ion.Ion;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ServiceCallInterface {
    private EditText usernameTxt;
    private EditText passwordTxt;
    private TextView registerHereTxt;
    private TextView forgotTxt;
    private TextView errTxt;
    private TextView guestTxt;
    private Button loginBtn;
    private ImageView loadingImg;

    private TextInputLayout emailtxtLayout;
    private TextInputLayout passtxtLayout;

    public static AppCompatActivity activity;
    private static final int RC_BARCODE_CAPTURE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        usernameTxt = findViewById(R.id.username_txt_log);
        passwordTxt = findViewById(R.id.password_txt_log);
        errTxt = findViewById(R.id.error_txt_log);

//        emailtxtLayout = findViewById(R.id.username_txt_layout);
//        passtxtLayout = findViewById(R.id.password_txt_layout);

//        emailtxtLayout.setHintAnimationEnabled(false);
//        passtxtLayout.setHintAnimationEnabled(false);

        registerHereTxt = findViewById(R.id.registerhere_txt);
        forgotTxt = findViewById(R.id.forgotpass_txt_log);
//        guestTxt = findViewById(R.id.guest_txt_log);
        registerHereTxt.setOnClickListener(this);
        forgotTxt.setOnClickListener(this);
//        guestTxt.setOnClickListener(this);

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

        loadingImg = findViewById(R.id.loading_img_log);

        Ion.with(loadingImg).centerCrop().load("android.resource://" + getPackageName() + "/" + R.drawable.loading);
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
        boolean userEmpty = Util.isEmpty(user);
        boolean passEmpty = Util.isEmpty(pass);
        Log.e("LoginActivity", "userEmpty" + android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches());
        if(userEmpty && passEmpty) {
            errTxt.setError("Email and Password Missing");
            errTxt.setText("Email and Password Missing  ");
            loadingImg.setVisibility(View.GONE);
        } else if(passEmpty) {
            errTxt.setError("Password Required");
            errTxt.setText("Password Required  ");
            loadingImg.setVisibility(View.GONE);
        } else if(userEmpty) {
            errTxt.setError("EmailAddress Required");
            errTxt.setText("EmailAddress Required  ");
            loadingImg.setVisibility(View.GONE);
        } else if(!Util.isValidEmail(user)) {
            errTxt.setError("Invalid Email Address!");
            errTxt.setText("Invalid Email Address  ");
            loadingImg.setVisibility(View.GONE);
        } else {
            doLogin();
        }
    }

    private void doLogin() {

//        String params = Util.webApiParam("username", emailadd, "&");
//        params += Util.webApiParam("password", passwordS, "&");
//        params += Util.webApiParam("mobile_name", model, "&");
//        params += Util.webApiParam("mobile_brand", brand, "&");
//        params += Util.webApiParam("levelid", "3", "&");
//        params += Util.webApiParam("macaddress", macadd, "");
//        String url = "Login?" + params;
//        HttpRequestTask requestTask = new HttpRequestTask(this, url);
//
//        requestTask.execute();

        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

        startActivityForResult(intent, RC_BARCODE_CAPTURE);
        finish();
    }

    private void onRegHere() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void onForgotPass() {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }

    private void onGuest() {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(String result) {

    }

    @Override
    public void onCancelled() {

    }
}