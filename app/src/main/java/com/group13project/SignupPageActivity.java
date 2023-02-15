package com.group13project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class SignupPageActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
    }

    protected String getEmailAddress() {
        EditText emailAddress = findViewById(R.id.emailAddress);
        return emailAddress.getText().toString().trim();
    }

    protected String getPhoneNumber() {
        EditText phoneNumber = findViewById(R.id.phoneNumber);
        return phoneNumber.getText().toString().trim();
    }

    protected String getFirstName() {
        EditText firstName = findViewById(R.id.firstName);
        return firstName.getText().toString().trim();
    }

    protected String getLastName() {
        EditText lastName = findViewById(R.id.lastName);
        return lastName.getText().toString().trim();
    }

    protected String getPassword() {
        EditText password = findViewById(R.id.password1);
        return password.getText().toString().trim();
    }

    protected String getPasswordConfirmation() {
        EditText password2 = findViewById(R.id.password2);
        return password2.getText().toString().trim();
    }


}
