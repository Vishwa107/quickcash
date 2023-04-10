package com.group13project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
public class EmployeeHomeActivity extends AppCompatActivity {

    ArrayList<JobPosting> jobsList = new ArrayList<>();
    DatabaseReference databaseReference;
    ListView jobsListView;
    EditText searchBox;
    Button searchButton;

    FloatingActionButton recJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        setTitle(R.string.employee_dashboard_title);

        jobsListView = findViewById(R.id.jobListView);
        searchBox = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);
        recJobs = findViewById(R.id.recommendedJobs);

        databaseReference = FirebaseDatabase.getInstance().getReference("job_postings");

        searchButton.setOnClickListener(view -> {
            String searchQuery = searchBox.getText().toString();
            searchJobPostings(searchQuery);
        });

        recJobs.setOnClickListener(view -> startActivity(new Intent(EmployeeHomeActivity.this, RecommendedJobsActivity.class)));

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
                        JobPosting job = new JobPosting(jobPosting.getJobTitle(), jobPosting.getJobDescription(), jobPosting.getExpectedDuration(), jobPosting.getPlace(), jobPosting.getUrgency(), jobPosting.getSalary(), jobPosting.getEmployerId());
                        jobsList.add(job);
                    }
                }
                ArrayAdapter<JobPosting> adapter = new ArrayAdapter<>(EmployeeHomeActivity.this, android.R.layout.simple_list_item_1, jobsList);
                jobsListView.setAdapter(adapter);

                jobsListView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Intent jobDescription = new Intent(EmployeeHomeActivity.this, JobDescription.class);
                    JobPosting clickedJob = (JobPosting) jobsList.get(i);
                    Toast.makeText(EmployeeHomeActivity.this, clickedJob.getJobTitle(), Toast.LENGTH_LONG).show();
                    jobDescription.putExtra("JobClicked", clickedJob);
                    startActivity(jobDescription);
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EmployeeHomeActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * This method searches the job postings that were loaded from the database for any matches with the given search query.
     *
     * @param searchQuery a string to search the for matches with the job postings.
     */
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
