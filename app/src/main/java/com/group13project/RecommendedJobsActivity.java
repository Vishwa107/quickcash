package com.group13project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class RecommendedJobsActivity extends AppCompatActivity {

    ListView jobList;

    FirebaseAuth firebaseAuth;

    FirebaseUser user;

    DatabaseReference dbRefPref;
    DatabaseReference dbRefJobPosting;

    private JobPref jobPref;

    ArrayList<JobPosting> allJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_jobs);
        setTitle("Recommended Jobs");
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rec_jobs_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editPref) {
            startActivity(new Intent(RecommendedJobsActivity.this, JobPrefActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        initViews();
        initVars();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getJobPref();
    }

    private void initVars() {
        allJobs = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String PREFS = "Prefs";
        dbRefPref = FirebaseDatabase.getInstance().getReference(PREFS + "/" + user.getUid());
        String JOB_POSTINGS = "job_postings";
        dbRefJobPosting = FirebaseDatabase.getInstance().getReference(JOB_POSTINGS);
    }

    private void initViews() {
        jobList = findViewById(R.id.recJobListView);
        jobList.setOnItemClickListener((adapterView, view, i, l) -> {
            if (allJobs != null && !allJobs.isEmpty()) {
                Intent jobDescription = new Intent(RecommendedJobsActivity.this, JobDescription.class);
                JobPosting clickedJob = (JobPosting) allJobs.get(i);
                Toast.makeText(RecommendedJobsActivity.this, clickedJob.getJobTitle(), Toast.LENGTH_LONG).show();
                jobDescription.putExtra("JobClicked", clickedJob);
                startActivity(jobDescription);
            }
        });
    }

    private void getJobPref() {
        dbRefPref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JobPref tempData = snapshot.getValue(JobPref.class);
                if (tempData != null) {
                    jobPref = tempData;
                    getAllJobs();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllJobs() {
        dbRefJobPosting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    allJobs = new ArrayList<>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        JobPosting tempData = postSnapshot.getValue(JobPosting.class);
                        if (tempData != null) {
                            if (!allJobs.contains(tempData)) {
                                allJobs.add(tempData);
                            }
                        }
                    }
                    filterJobs();
                } else
                    Toast.makeText(RecommendedJobsActivity.this, "No Jobs Found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                final String errorRead = error.getMessage();
                Toast.makeText(RecommendedJobsActivity.this, errorRead, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterJobs() {
        ArrayList<JobPosting> tempJobs = new ArrayList<>();
        for (JobPosting posting : allJobs) {
            if (Objects.equals(posting.getExpectedDuration(), jobPref.getJobDuration()) || Objects.equals(posting.getPlace(), jobPref.getPlace()) || Objects.equals(posting.getSalary(), jobPref.getSalary())) {
                tempJobs.add(posting);
                Log.e("TAG", "filterJobs: " + posting);
            }
        }
        if (tempJobs.isEmpty()) {
            Toast.makeText(this, "No Matching Jobs Found!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Showing Recommended Jobs", Toast.LENGTH_SHORT).show();
        allJobs.clear();
        allJobs.addAll(tempJobs);
        ArrayAdapter<JobPosting> adapter = new ArrayAdapter<>(RecommendedJobsActivity.this, android.R.layout.simple_list_item_1, allJobs);
        jobList.setAdapter(adapter);
    }

}