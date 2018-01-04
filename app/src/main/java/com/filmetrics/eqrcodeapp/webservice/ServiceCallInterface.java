package com.filmetrics.eqrcodeapp.webservice;

/**
 * Created by nmabasa on 5/17/2016.
 */
public interface ServiceCallInterface {
    public void onPreExecute();
    public void onPostExecute(String result);
    public void onCancelled();
}
