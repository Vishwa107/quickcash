package com.group13project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployerProfile extends Activity {
    Button newJob;
    Button postedJob;
    static String empDetails = new String();
    FirebaseDatabase database = null;
    DatabaseReference firstNameRef;
    DatabaseReference lastNameRef;
    DatabaseReference emailRef;
    DatabaseReference phoneNumberRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

    }
}
