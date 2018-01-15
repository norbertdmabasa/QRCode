package com.filmetrics.eqrcodeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.filmetrics.eqrcodeapp.utils.DialogUtil;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, ServiceCallInterface {
    private static final String TAG = "RegistrationActivity";
    private Button registerBtn;
//    private ImageView gsign;
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
    private RegistrationActivity context;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = this;
        FacebookSdk.sdkInitialize(context);
        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email_txt_reg);
        password = (EditText) findViewById(R.id.password_txt_reg);
//        repassword = (EditText) findViewById(R.id.repassword_txt_reg);

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ed = prefs.edit();
        res = Util.getPDetails(getApplicationContext());
        registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(context);

        signInButton = (SignInButton) findViewById(R.id.signInButton);
        fsign = (LoginButton) findViewById(R.id.fsign_btn);

        fsign.setOnClickListener(context);
        signInButton.setOnClickListener(context);
        callbackManager = CallbackManager.Factory.create();

        progressDialog = new ProgressDialog(context);
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

//        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(context).enableAutoManage(context, context).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                boolean userEmpty = Util.isEmpty(email.getText().toString());
                boolean passEmpty = Util.isEmpty(password.getText().toString());
//                boolean rpassEmpty = Util.isEmpty(repassword.getText().toString());
                boolean connection = Util.checkConnect(getApplicationContext());
                Log.e("RegistrationActivity", (userEmpty || passEmpty) + "");
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
//                    String repasswordS = repassword.getText().toString();
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
                    HttpRequestTask requestTask = new HttpRequestTask(context, url, registerBtn);
                    requestTask.execute();
//                }
                break;

            case R.id.signInButton:
                Toast.makeText(this, "GSIGN", Toast.LENGTH_SHORT).show();
                onGsign();
                break;
            default:
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

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            handleGsign(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void handleGsign(FirebaseUser result) {
        String name = result.getDisplayName();
        String email = result.getEmail();
        String image = result.getPhotoUrl().toString();

        ed.putString(getString(R.string.firstname), name);
        ed.putString(getString(R.string.photo), image);
        ed.putString(getString(R.string.email), email);
        ed.commit();

        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        startActivity(intent);
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
            firebaseAuthWithGoogle(result.getSignInAccount());
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
                DialogUtil.alertOkDialog(context, "An error occured in registration.", "Registration").show();
            } else if(result.contains("Phone is already registered.")) {
                dismissDialog();
                DialogUtil.alertOkDialog(context, "Phone already registered.", "Registration").show();
            } else if(jsonObject.getString("message").equals("ok")) {
                ed.putString(getString(R.string.id), jsonObject.getString("id"));
                ed.putString(getString(R.string.firstname), jsonObject.getString("firstname"));
                ed.putString(getString(R.string.middlename), jsonObject.getString("middlename"));
                ed.putString(getString(R.string.lastname), jsonObject.getString("lastname"));
                ed.putString(getString(R.string.suffix), jsonObject.getString("suffix"));
                ed.putString(getString(R.string.contact), jsonObject.getString("contact"));
                ed.putString(getString(R.string.gender), jsonObject.getString("gender"));
                ed.putString(getString(R.string.empnumber), jsonObject.getString("empnumber"));
                ed.putString(getString(R.string.jobtitle), jsonObject.getString("jobtitle"));

                ed.commit();
                dismissDialog();
                Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelled() {
        dismissDialog();
    }
}