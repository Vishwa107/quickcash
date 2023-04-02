package com.group13project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * This activity allows the user to sign up for an account using Firebase authentication and saves the user's
 * information to the Firebase database.
 */

public class SignupPageActivity extends Activity implements View.OnClickListener {

    public static final String VALID = "valid";
    // declaring Firebase authentication
    private FirebaseAuth mAuth;

    // declaring input fields
    private EditText firstName, lastName, emailAddress, phoneNumber, password, passwordConfirm;

    // declaring signup button
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (!validateInput(fName, lName, email, phone, pass, passConfirm)) {
            return;
        }

        // calling Firebase authentication to create and register the user
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fName, lName, email, phone);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignupPageActivity.this, "Signed up successfully!", Toast.LENGTH_LONG).show();
                                                Intent action = new Intent(SignupPageActivity.this, LoginPageActivity.class);
                                                startActivity(action);
                                            } else {
                                                Toast.makeText(SignupPageActivity.this, "Sign up failed, try again.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(SignupPageActivity.this, "Sign up failed, try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * This method validates input fields and sets an error message for the invalid fields.
     *
     * @param fName       User's first name
     * @param lName       User's  last name
     * @param email       User's  email address
     * @param phone       User's  phone number
     * @param pass        password
     * @param passConfirm password confirmation
     * @return true if all fields are valid, false if any field is invalid
     */
    protected boolean validateInput(String fName, String lName, String email, String phone, String pass, String passConfirm) {
        // validating fields
        if (validateFirstName(fName)) {
            firstName.setError("Fill in your first name");
            firstName.requestFocus();
            return false;
        }
        if (validateLastName(lName)) {
            lastName.setError("Fill in your last name");
            lastName.requestFocus();
            return false;
        }
        if (validateEmailAddress(email).equals("empty")) {
            emailAddress.setError("Fill in your email address");
            emailAddress.requestFocus();
            return false;
        } else if (validateEmailAddress(email).equals("invalid")) {
            emailAddress.setError("Enter a valid email");
            emailAddress.requestFocus();
            return false;
        }
        if (validatePhoneNumber(phone).equals("empty")) {
            phoneNumber.setError("Fill in your phone number");
            phoneNumber.requestFocus();
            return false;
        } else if (validatePhoneNumber(phone).equals("invalid")) {
            phoneNumber.setError("Enter a valid phone number");
            phoneNumber.requestFocus();
            return false;
        }
        if (validatePassword(pass).equals("empty")) {
            password.setError("Fill in your password");
            password.requestFocus();
            return false;
        } else if (validatePassword(pass).equals("short")) {
            password.setError("Password must contain at least 6 characters");
            password.requestFocus();
            return false;
        }
        if (validateConfirmPassword(pass, passConfirm).equals("empty")) {
            passwordConfirm.setError("Fill confirm your password");
            passwordConfirm.requestFocus();
            return false;
        } else if (validateConfirmPassword(pass, passConfirm).equals("incorrect")) {
            passwordConfirm.setError("Enter the same password");
            passwordConfirm.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * This method checks if the First name is empty
     *
     * @param fName User's first name
     * @return if fName is empty, returns true else false
     */
    protected boolean validateFirstName(String fName) {
        return fName.isEmpty();
    }

    /**
     * This method checks if the Last name is empty
     *
     * @param lName User's last name
     * @return if lName is empty, returns true else false
     */
    protected boolean validateLastName(String lName) {
        return lName.isEmpty();
    }

    /**
     * This method is for checking the validity of email using regex
     *
     * @param email User's email address
     * @return String describing the error
     */
    protected String validateEmailAddress(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email.isEmpty()) {
            return "empty";
        } else if (!email.matches(regex)) {
            return "invalid";
        } else {
            return VALID;
        }
    }

    /**
     * This method is for checking the validity of phone number using regex
     *
     * @param phone User's phone number
     * @return String describing the error
     */
    protected String validatePhoneNumber(String phone) {
        String regex = "^(?:[0-9] ?){6,14}[0-9]$";
        if (phone.isEmpty()) {
            return "empty";
        } else if (!phone.matches(regex)) {
            return "invalid";
        } else {
            return VALID;
        }
    }

    /**
     * This method is for checking the validity of password
     *
     * @param pass User's password
     * @return String describing the error
     */
    protected String validatePassword(String pass) {
        if (pass.isEmpty()) {
            return "empty";
        } else if (pass.length() < 6) {
            return "short";
        } else {
            return VALID;
        }
    }

    /**
     * This method is for checking if the entered password and confirm password are same
     *
     * @param pass        User's password
     * @param passConfirm User's confirmed password
     * @return String describing the error
     */
    protected String validateConfirmPassword(String pass, String passConfirm) {
        if (passConfirm.isEmpty()) {
            return "empty";
        } else if (!pass.equals(passConfirm)) {
            return "incorrect";
        } else {
            return VALID;
        }
    }
}
