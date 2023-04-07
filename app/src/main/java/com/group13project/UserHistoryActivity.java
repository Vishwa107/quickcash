package com.group13project;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * UserHistoryActivity displays job history, income, and user names
 * for users in the application.
 */
public class UserHistoryActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseJobPostings;
    private ListView mUserHistoryList;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mUserHistoryData;
    private HashMap<String, String> userNames;

    /**
     * onCreate sets up the activity layout, initializes Firebase references,
     * and sets up the ListView and ArrayAdapter.
     *
     * @param savedInstanceState a Bundle containing previously saved activity state, if applicable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        // Set up Firebase references
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseJobPostings = FirebaseDatabase.getInstance().getReference().child("job_postings");

        // Initialize ListView and ArrayList
        mUserHistoryList = (ListView) findViewById(R.id.user_history_list);
        mUserHistoryData = new ArrayList<>();

        // Initialize HashMap to store user names
        userNames = new HashMap<>();

        // Set up ArrayAdapter for the ListView
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mUserHistoryData);
        mUserHistoryList.setAdapter(mAdapter);

        // Load user names from the "Users" node in the database
        loadUserNames();
    }

    /**
     * loadUserNames retrieves user names from the Firebase Realtime Database
     * and stores them in a HashMap for later use.
     */
    private void loadUserNames() {
        mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    String userName = userSnapshot.child("firstName").getValue(String.class) + " " + userSnapshot.child("lastName").getValue(String.class);
                    userNames.put(userId, userName);
                }

                // Load job history once user names are loaded
                loadJobHistory();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserHistoryActivity.this, "Failed to load user names.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * loadJobHistory fetches job postings from the Firebase Realtime Database
     * and combines them with the associated user names to display in the ListView.
     */
    private void loadJobHistory() {
        mDatabaseJobPostings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserHistoryData.clear();

                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    String employerId = jobSnapshot.child("employerId").getValue(String.class);
                    String jobTitle = jobSnapshot.child("jobTitle").getValue(String.class);
                    String salary = jobSnapshot.child("salary").getValue(String.class);
                    String place = jobSnapshot.child("place").getValue(String.class);

                    // Combine user name with job posting data if the user name is available
                    if (userNames.containsKey(employerId)) {
                        String userName = userNames.get(employerId);
                        String jobHistoryEntry = userName + ": " + jobTitle + ", " + salary + ", " + place;
                        mUserHistoryData.add(jobHistoryEntry);
                    }
                }

                // Notify the adapter that the data set has changed, updating the ListView
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserHistoryActivity.this, "Failed to load job history.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}