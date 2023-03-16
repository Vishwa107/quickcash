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

    // declaring Firebase authentication
    private FirebaseAuth mAuth;

    // declaring input fields
    private EditText emailAddress, password;

    // declaring login button
    private Button logInButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        // initializing Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // initializing data fields
        emailAddress = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);

        // initializing login button
        logInButton = findViewById(R.id.checkValidButton);
        logInButton.setOnClickListener(this);
    }

    /**
     * This method handles the log in process for the user. It also alerts the user if any data fields are invalid or if the login did not succeed.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        logInUser();
    }

    private void logInUser(){
        // retrieving fields
        String emailId = emailAddress.getText().toString().trim();
        String pass = password.getText().toString().trim();

        // validating fields
        if(!validateInputLogin(emailId, pass)){
            return;
        }

        // calling Firebase authentication to log the user in
        mAuth.signInWithEmailAndPassword(emailId,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user profile or home page for employee or employer
                    startActivity(new Intent(LoginPageActivity.this,ChooseViewActivity.class));

                }else{
                    Toast.makeText(LoginPageActivity.this,"Failed to login! Please check your credentials.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * This method validates input fields and sets an error message for the invalid fields.
     * @param emailId User's  email address
     * @param pass password
     * @return true if all fields are valid, false if any field is invalid
     */
    protected boolean validateInputLogin(String emailId, String pass) {
        // validating fields
        if(validateEmailAddress(emailId).equals("empty")){
            emailAddress.setError("Fill in your email address");
            emailAddress.requestFocus();
            return false;
        }else if(validateEmailAddress(emailId).equals("invalid")){
            emailAddress.setError("Enter a valid email");
            emailAddress.requestFocus();
            return false;
        }
        if(validatePassword(pass).equals("empty")){
            password.setError("Fill in your password");
            password.requestFocus();
            return false;
        }else if(validatePassword(pass).equals("short")){
            password.setError("Password must contain at least 6 characters");
            password.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * This method is for checking the validity of email using regex
     * @param email User's email address
     * @return String describing the error
     */
    protected String validateEmailAddress(String email){
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(email.isEmpty()){
            return "empty";
        }else if(!email.matches(regex)){
            return "invalid";
        }else{
            return "valid";
        }
    }

    /**
     * This method is for checking the validity of password
     * @param pass User's password
     * @return String describing the error
     */
    protected String validatePassword(String pass){
        if(pass.isEmpty()){
            return "empty";
        }else if(pass.length() < 6){
            return "short";
        }else{
            return "valid";
        }
    }
    
}