package com.filmetrics.eqrcodeapp;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private Button registerBtn;
    private ImageView gsign;
    private LoginButton fsign;
    private SignInButton signInButton;

    private CallbackManager callbackManager;

    private static int GSIGN = 2001;
    private static int FSIGN = 64206;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FacebookSdk.sdkInitialize(this);

        registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(this);

        signInButton = (SignInButton) findViewById(R.id.signInButton);
        fsign = (LoginButton) findViewById(R.id.fsign_btn);

        signInButton.setOnClickListener(this);
        fsign.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();

        fsign.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                // Login Cancelled
            }

            @Override
            public void onError(FacebookException error) {
                // Login Error
            }
        });

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onGsign() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, GSIGN);
    }

    private void handleGsign(GoogleSignInResult result) {
        if(result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String image = account.getPhotoUrl().toString();
        } else {

        }
    }

    public void onFsign() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(RegistrationActivity.class.getName(), "" + requestCode + " resultCode" + resultCode);
        if(requestCode == GSIGN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
            startActivity(intent);
        } else if(requestCode == FSIGN) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else {

        }
    }
}