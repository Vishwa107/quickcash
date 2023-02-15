package com.group13project;


import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class LoginPageActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
    }

    protected String getEmailAddress() {
        EditText emailAddress = findViewById(R.id.emailField);
        return emailAddress.getText().toString().trim();
    }

    protected String getPassword() {
        EditText password = findViewById(R.id.passwordField);
        return password.getText().toString().trim();
    }
}