package com.filmetrics.eqrcodeapp.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.filmetrics.eqrcodeapp.RegistrationActivity;

import org.json.JSONObject;

/**
 * Created by nmabasa on 5/17/2016.
 */
public class HttpRequestTask extends AsyncTask<String, String, String> {
    private final ProgressDialog progressDialog;
    private final Button registerBtn;
    private String urls = "http://192.168.21.106:9001/api/MainApi/";
    private ServiceCallInterface serviceCall;

    public HttpRequestTask(ServiceCallInterface serviceCall, String url, ProgressDialog progressDialog, Button registerBtn) {
        this.serviceCall = serviceCall;
        this.urls += url;
        this.progressDialog = progressDialog;
        this.registerBtn = registerBtn;
        Log.e("Register URL","" + urls);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        serviceCall.onPreExecute();
    }

    String result = "Failed";
    @Override
    protected String doInBackground(String[] params) {
        result = "Failed";
        try {
            result = WebRequest.get(urls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        serviceCall.onPostExecute(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        serviceCall.onCancelled();
    }
}