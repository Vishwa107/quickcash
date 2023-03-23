package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * EmployeeHomeActivity class is the main activity class for the employee dashboard.
 * This class displays a list of job postings and provides the functionality for
 * employees to navigate to the employer dashboard or log out of the application.
 */
public class EmployeeHomeActivity extends AppCompatActivity{

    ArrayList<String> jobsList = new ArrayList<>();
    DatabaseReference databaseReference;
    ListView jobsListView;
    EditText searchBox;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        setTitle(R.string.employee_dashboard_title);

        jobsListView = findViewById(R.id.jobListView);
        searchBox = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("job_postings");

        searchButton.setOnClickListener(view -> {
            String searchQuery = searchBox.getText().toString();
            searchJobPostings(searchQuery);
        });

        // Load all job postings initially
        loadAllJobPostings();
    }

    /**
     * This method loads and displays all the job postings in the Realtime database when the activity starts.
     */
    private void loadAllJobPostings() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jobsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    JobPosting jobPosting = snapshot.getValue(JobPosting.class);
                    if (jobPosting != null) {
                        final String jobInfo = jobPosting.getJobTitle() + "\nUrgency:" + jobPosting.getUrgency() + "\n" + jobPosting.getPlace() + "\n" + jobPosting.getExpectedDuration() + "\nSalary: " + jobPosting.getSalary() + "\n" + jobPosting.getJobDescription() + "\n\n";
                        jobsList.add(jobInfo);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(EmployeeHomeActivity.this, android.R.layout.simple_list_item_1, jobsList);
                jobsListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EmployeeHomeActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * This method searches the job postings that were loaded from the database for any matches with the given search query.
     * @param searchQuery a string to search the for matches with the job postings.
     */
    private void searchJobPostings(String searchQuery) {
        searchQuery = searchQuery.toLowerCase();
        ArrayList<String> filteredJobsList = new ArrayList<>();
        for (String jobInfo : jobsList) {
            if (jobInfo.toLowerCase().contains(searchQuery)) {
                filteredJobsList.add(jobInfo);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EmployeeHomeActivity.this, android.R.layout.simple_list_item_1, filteredJobsList);
        jobsListView.setAdapter(adapter);
    }



}
