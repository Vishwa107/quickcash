package com.group13project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserHistoryActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> jobList;
    private HashMap<String, String> userNames;
    private DatabaseReference jobRef;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        listView = findViewById(R.id.user_history_list);
        jobList = new ArrayList<>();
        userNames = new HashMap<>();

        // Retrieve the user ID from the Intent Extra
        String selectedUserId = getIntent().getStringExtra("selectedUserId");

        // Initialize Firebase database references
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        jobRef = database.getReference("job_postings");
        userRef = database.getReference("Users");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobList);
        listView.setAdapter(adapter);

        // ValueEventListener for job postings
        ValueEventListener jobListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobList.clear();
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    String employerId = jobSnapshot.child("employerId").getValue(String.class);
                    if (employerId.equals(selectedUserId)) {
                        String jobTitle = jobSnapshot.child("jobTitle").getValue(String.class);
                        String salary = jobSnapshot.child("salary").getValue(String.class);
                        String place = jobSnapshot.child("place").getValue(String.class);
                        String jobInfo = userNames.get(employerId) + ": " + jobTitle + ", " + salary + ", " + place;
                        jobList.add(jobInfo);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };


        // ValueEventListener for user names
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    String userName = userSnapshot.child("firstName").getValue(String.class) + " " + userSnapshot.child("lastName").getValue(String.class);
                    userNames.put(userId, userName);
                }
                // Attach the ValueEventListener for job postings after fetching user names
                jobRef.addValueEventListener(jobListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        userRef.addValueEventListener(userListener);
    }
}
