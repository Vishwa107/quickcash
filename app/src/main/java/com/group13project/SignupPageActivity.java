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
import com.google.firebase.database.FirebaseDatabase;

public class SignupPageActivity extends Activity implements View.OnClickListener {

    // declaring Firebase authentication
    private FirebaseAuth mAuth;

    // declaring input fields
    private EditText firstName, lastName, emailAddress, phoneNumber, password, passwordConfirm;

    // declaring signup button
    private Button signupButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        // initializing Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // initializing data fields
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailAddress = findViewById(R.id.emailAddress);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password1);
        passwordConfirm = findViewById(R.id.password2);

        // initializing signup button
        signupButton = findViewById(R.id.signUpButton);
        signupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        signupUser();
    }

    /**
     * This method handles registering the user to the database using Firebase authentication.
     * It also alerts the user if any data fields are invalid or if the signup did not succeed.
     */
    private void signupUser() {
        // retrieving fields
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String email = emailAddress.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String passConfirm = passwordConfirm.getText().toString().trim();

        // validating fields
        if(!validateInput(fName, lName, email, phone, pass, passConfirm)){
            return;
        }

        // calling Firebase authentication to create and register the user
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fName, lName, email, phone);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignupPageActivity.this, "Signed up successfully!", Toast.LENGTH_LONG).show();
                                                Intent action = new Intent(SignupPageActivity.this, LoginPageActivity.class);
                                                startActivity(action);
                                            }else{
                                                Toast.makeText(SignupPageActivity.this, "Sign up failed, try again.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(SignupPageActivity.this, "Sign up failed, try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * This method validates input fields and sets an error message for the invalid fields.
     * @param fName User's first name
     * @param lName User's  last name
     * @param email User's  email address
     * @param phone User's  phone number
     * @param pass password
     * @param passConfirm password confirmation
     * @return true if all fields are valid, false if any field is invalid
     */
    protected boolean validateInput(String fName, String lName, String email, String phone, String pass, String passConfirm){
        // validating fields
        if(validateFirstName(fName)){
            return false;
        }
        if(validateLastName(lName)){
            return false;
        }
        if(validateEmailAddress(email)){
            return false;
        }
        if(validatePhoneNumber(phone)){
            return false;
        }
        if(validatePassword(pass)){
            return false;
        }
        if(validateConfirmPassword(pass, passConfirm)){
            return false;
        }
        return true;
    }

    private boolean validateFirstName(String fName){
        if(fName.isEmpty()){
            firstName.setError("Fill in your first name");
            firstName.requestFocus();
            return true;
        }
        return false;
    }

    private boolean validateLastName(String lName){
        if(lName.isEmpty()){
            lastName.setError("Fill in your last name");
            lastName.requestFocus();
            return true;
        }
        return false;
    }

    private boolean validateEmailAddress(String email){
        if(email.isEmpty()){
            emailAddress.setError("Fill in your email address");
            emailAddress.requestFocus();
            return true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
            emailAddress.setError("Enter a valid email");
            emailAddress.requestFocus();
            return true;
        }
        return false;
    }

    private boolean validatePhoneNumber(String phone){
        if(phone.isEmpty()){
            phoneNumber.setError("Fill in your phone number");
            phoneNumber.requestFocus();
            return true;
        }
        if(!Patterns.PHONE.matcher(phone).matches()){
            phoneNumber.setError("Enter a valid phone number");
            phoneNumber.requestFocus();
            return true;
        }
        return false;
    }

    private boolean validatePassword(String pass){
        if(pass.isEmpty()){
            password.setError("Fill in your password");
            password.requestFocus();
            return true;
        }
        if(pass.length() < 6){
            password.setError("Password must contain at least 6 characters");
            password.requestFocus();
            return true;
        }
        return false;
    }

    private boolean validateConfirmPassword(String pass, String passConfirm){
        if(passConfirm.isEmpty()){
            passwordConfirm.setError("Fill confirm your password");
            passwordConfirm.requestFocus();
            return true;
        }
        if(!pass.equals(passConfirm)){
            passwordConfirm.setError("Enter the same password");
            passwordConfirm.requestFocus();
            return true;
        }
        return false;
    }
}
