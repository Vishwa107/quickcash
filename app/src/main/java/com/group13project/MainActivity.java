package com.group13project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button signUpButton;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(buttonClickListener);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // checking which button is clicked, then redirecting accordingly
            switch (v.getId()) {
                case R.id.signUpButton:
                    Intent signUpIntent = new Intent(MainActivity.this, SignupPageActivity.class);
                    startActivity(signUpIntent);
                    break;
                case R.id.loginButton:
                    Intent loginIntent = new Intent(MainActivity.this, LoginPageActivity.class);
                    startActivity(loginIntent);
                    break;
                default:
                    break;
            }
        }
    };
}