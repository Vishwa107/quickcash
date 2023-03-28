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
        setContentView(R.layout.activity_profile_page);
        setTitle(R.string.employer_profile);

        TextView details = (TextView)findViewById(R.id.empDetails);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users/"+ userID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User current = snapshot.getValue(User.class);
                if (current != null && current.firstName!= null) {
                    String neww = current.firstName + "\n" + current.lastName + "\n" + current.phoneNumber + "\n" + current.emailAddress;
                    details.setText(neww);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                final String errorRead = error.getMessage();
                details.setText(errorRead);
            }
        });

    }

}
