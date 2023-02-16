package com.group13project;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginPageActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText emailAddress, password;
    private Button logInbutton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        mAuth = FirebaseAuth.getInstance();

        emailAddress = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);

        logInbutton = findViewById(R.id.checkValidButton);
        logInbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        logInUser();
    }

    private void logInUser(){
        String emailId = emailAddress.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(emailId.isEmpty()){
            emailAddress.setError("Email is required!");
            emailAddress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailId).matches() ){
            emailAddress.setError("Enter a valid email");
            emailAddress.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if(pass.length() < 6){
            password.setError("Password must contain at least 6 characters");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailId,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user profile or home page for employee or employer
                    startActivity(new Intent(LoginPageActivity.this,EmployeeHomeActivity.class));

                }else{
                    Toast.makeText(LoginPageActivity.this,"Failed to login! Please check your crendentials.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    
}