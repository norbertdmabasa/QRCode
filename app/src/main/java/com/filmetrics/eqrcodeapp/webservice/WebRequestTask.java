package com.filmetrics.eqrcodeapp.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.filmetrics.eqrcodeapp.objects.User;

//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;

/**
 * Created by NMABASA on 11/29/2017.
 */

public class WebRequestTask extends AsyncTask<Void, Void, Object> {
    private String url;
    private WebServiceInterface serviceInterface;
    public  WebRequestTask(String url, WebServiceInterface serviceInterface) {
        this.url = url;
        this.serviceInterface = serviceInterface;
    }

    @Override
    protected Object doInBackground(Void... voids) {
//        try {
//            serviceInterface.onRequestStart();
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            User user = restTemplate.getForObject(url, User.class);
//            return user;
//        } catch (Exception e) {
//            Log.e("MainActivity", e.getMessage(), e);
//        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        serviceInterface.onPostExecute(o);
    }
}
