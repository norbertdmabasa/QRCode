package com.filmetrics.eqrcodeapp.objects;

import java.io.Serializable;

/**
 * Created by NMABASA on 11/27/2017.
 */

public class User implements Serializable {
    private String username;
    private String userid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
