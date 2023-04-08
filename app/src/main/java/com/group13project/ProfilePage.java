package com.group13project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {
    FirebaseUser user = null;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);
        setTitle(R.string.employer_profile);

        TextView empName = (TextView)findViewById(R.id.empName);
        TextView empFirstName = (TextView)findViewById(R.id.empFirstName);
        TextView empLastName = (TextView)findViewById(R.id.empLastName);
        TextView empEmail = (TextView)findViewById(R.id.empEmail);
        TextView empPhone = (TextView)findViewById(R.id.empPhone);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users/"+ userID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User current = snapshot.getValue(User.class);
                if (current != null && current.firstName!= null) {
                    empName.setText(current.firstName.toUpperCase() + " " + current.lastName.toUpperCase());
                    empFirstName.setText(current.firstName.toUpperCase());
                    empLastName.setText(current.lastName.toUpperCase());
                    empPhone.setText(current.phoneNumber);
                    empEmail.setText(current.emailAddress);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                final String errorRead = error.getMessage();
                empName.setText(errorRead);
            }
        });

    }

}
