package com.filmetrics.eqrcodeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.filmetrics.eqrcodeapp.utils.Util;
import com.filmetrics.eqrcodeapp.webservice.HttpRequestTask;
import com.filmetrics.eqrcodeapp.webservice.ServiceCallInterface;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, ServiceCallInterface {
    private Button registerBtn;
    private ImageView gsign;
    private LoginButton fsign;
    private SignInButton signInButton;

    private CallbackManager callbackManager;

    private static int GSIGN = 2001;
    private static int FSIGN = 64206;

    private GoogleApiClient googleApiClient;
    private SharedPreferences prefs;
    private SharedPreferences.Editor ed;
    private ProgressDialog progressDialog;

    private EditText email, password, repassword;
    private String[] res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FacebookSdk.sdkInitialize(this);

        email = (EditText) findViewById(R.id.email_txt_reg);
        password = (EditText) findViewById(R.id.password_txt_reg);
        repassword = (EditText) findViewById(R.id.repassword_txt_reg);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ed = prefs.edit();
        res = Util.getPDetails(getApplicationContext());
        registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(this);

        signInButton = (SignInButton) findViewById(R.id.signInButton);
        fsign = (LoginButton) findViewById(R.id.fsign_btn);

        signInButton.setOnClickListener(this);
        fsign.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");


        fsign.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFSign(loginResult);

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
                boolean userEmpty = Util.isEmpty(email.getText().toString());
                boolean passEmpty = Util.isEmpty(password.getText().toString());
                boolean rpassEmpty = Util.isEmpty(repassword.getText().toString());
                boolean connection = Util.checkConnect(getApplicationContext());
                Log.e("RegistrationActivity", (userEmpty || passEmpty || rpassEmpty) + "");
//                if(connection || userEmpty || passEmpty || rpassEmpty) {
//                    if(userEmpty && passEmpty && rpassEmpty) {
//
//                    }
//
//                    if(userEmpty && passEmpty) {
//
//                    }
//
//                    if(passEmpty && rpassEmpty) {
//
//                    }
//
//                    if(userEmpty && rpassEmpty) {
//
//                    }
//
//                    if (passEmpty) {
//
//
//                    }
//
//                    if (rpassEmpty) {
//
//
//                    }
//                } else {
                    String emailadd = email.getText().toString();
                    String passwordS = password.getText().toString();
                    String repasswordS = repassword.getText().toString();
                    String params = "";
                    params = Util.webApiParam("username", emailadd, "&");
                    params += Util.webApiParam("password", passwordS, "&");
                    params += Util.webApiParam("mobile_name", res[0], "&");
                    params += Util.webApiParam("mobile_brand", res[1], "&");
                    params += Util.webApiParam("levelid", "3", "&");
                    params += Util.webApiParam("macaddress", res[0], "");
                    String url = "";
                    url = "Register?" + params;
                    Log.e("Register", url);
                    HttpRequestTask requestTask = new HttpRequestTask(this, url, progressDialog, registerBtn);
                    requestTask.execute();
//                }

                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void onGsign() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, GSIGN);
    }

    private void handleGsign(GoogleSignInResult result) {
        if(result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String image = account.getPhotoUrl().toString();

            ed.putString(getString(R.string.firstname), account.getGivenName());
            ed.putString(getString(R.string.lastname), account.getFamilyName());
            ed.putString(getString(R.string.photo), account.getPhotoUrl().toString());
            ed.putString(getString(R.string.email), account.getEmail());
            ed.commit();
        } else {

        }
    }

    private boolean checkEmpty(EditText input) {
        try {
            if(input.getText().toString() == null || input.getText().toString().equals("")) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }

        return false;
    }

    private void onFsign() {

    }

    public void showDialog() {
        progressDialog.show();
        registerBtn.setEnabled(false);
    }

    public void dismissDialog() {
        progressDialog.dismiss();
        registerBtn.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(RegistrationActivity.class.getName(), "" + requestCode + " resultCode" + resultCode);
        if(requestCode == GSIGN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGsign(result);
            Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
            startActivity(intent);
        } else if(requestCode == FSIGN) {
            callbackManager.onActivityResult(requestCode, resultCode, data);


        } else {

        }
    }

    private void handleFSign(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response",response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");
                            String picture = response.getJSONObject().getString("picture");
                            String birthday = response.getJSONObject().getString("birthday");

                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            String link = profile.getLinkUri().toString();
                            Log.i("Link",link);
                            if (Profile.getCurrentProfile()!=null)
                            {
                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            }

                            Log.i("Login" + "Email", email);
                            Log.i("Login"+ "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            Log.i("Login" + "Gender", gender);
                            Log.i("Login" + "Picture", picture);
                            Log.i("Login" + "Birthday", birthday);

                            ed.putString(getString(R.string.firstname), firstName);
                            ed.putString(getString(R.string.lastname), lastName);
                            ed.putString(getString(R.string.photo), picture);
                            ed.putString(getString(R.string.email), email);
                            ed.putString(getString(R.string.email), birthday);
                            ed.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender,picture,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onPreExecute() {
        showDialog();
    }

    @Override
    public void onPostExecute(String result) {
        result = result.replace("\\", "");
        result = result.substring(1, result.length()-1);
        Log.e("Register", result);
        try {

            JSONObject jsonObject = new JSONObject(result + "");;
            Log.e("Register", jsonObject.getString("message"));

            if(result.contains("Authentication failed.")) {
                dismissDialog();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Registration");

                builder.setMessage("Unauthorized Email!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            } else {
                dismissDialog();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelled() {

    }
}