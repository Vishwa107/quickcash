package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeHomeActivity extends AppCompatActivity {

    ArrayList<JobPosting> jobsList = new ArrayList<>();
    ArrayList<String> employerIdsList = new ArrayList<>();
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

        loadAllJobPostings();
    }

    private void loadAllJobPostings() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jobsList.clear();
                employerIdsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    JobPosting jobPosting = snapshot.getValue(JobPosting.class);
                    if (jobPosting != null) {
                        String employerId = jobPosting.getEmployerId();
                        employerIdsList.add(employerId);
                        jobsList.add(jobPosting);
                    }
                }
                JobListAdapter adapter = new JobListAdapter(EmployeeHomeActivity.this, jobsList, employerIdsList,
                        employerId -> {
                            Intent intent = new Intent(EmployeeHomeActivity.this, EmployerHistoryActivity.class);
                            intent.putExtra("selectedUserId", employerId);
                            startActivity(intent);
                        },
                        position -> {
                            Intent jobDescription = new Intent(EmployeeHomeActivity.this, JobDescription.class);
                            JobPosting clickedJob = jobsList.get(position);
                            jobDescription.putExtra("JobClicked", clickedJob);
                            startActivity(jobDescription);
                        });
                jobsListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EmployeeHomeActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void searchJobPostings(String searchQuery) {
        searchQuery = searchQuery.toLowerCase();
        ArrayList<JobPosting> filteredJobsList = new ArrayList<>();
        for (JobPosting jobInfo : jobsList) {
            if (jobInfo.toString().toLowerCase().contains(searchQuery)) {
                filteredJobsList.add(jobInfo);
            }
        }
        ArrayAdapter<JobPosting> adapter = new ArrayAdapter<>(EmployeeHomeActivity.this, android.R.layout.simple_list_item_1, filteredJobsList);
        jobsListView.setAdapter(adapter);
    }
}
