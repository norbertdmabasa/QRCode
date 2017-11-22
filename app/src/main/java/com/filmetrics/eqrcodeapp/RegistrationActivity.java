package com.filmetrics.eqrcodeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernameEdt;
    private EditText passwordEdt;
    private EditText repasswordEdt;
    private EditText emailAddEdt;
    private EditText firstnameEdt;
    private EditText middlenameEdt;
    private EditText lastnameEdt;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

//        usernameEdt = (EditText) findViewById(R.id.username_txt_reg);
        passwordEdt = (EditText) findViewById(R.id.password_txt_reg);
        repasswordEdt = (EditText) findViewById(R.id.repassword_txt_reg);
        emailAddEdt = (EditText) findViewById(R.id.email_txt_reg);
//        firstnameEdt = (EditText) findViewById(R.id.firstname_txt_reg);
//        middlenameEdt = (EditText) findViewById(R.id.middlename_txt_reg);
//        lastnameEdt = (EditText) findViewById(R.id.lastname_txt_reg);

        registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.register_btn) {

        }
    }
}