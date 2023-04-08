package com.group13project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * EmployeeHomeActivity class is the main activity class for the employee dashboard.
 * This class displays a list of job postings and provides the functionality for
 * employees to navigate to the employer dashboard or log out of the application.
 */
public class EmployeeHomeActivity extends AppCompatActivity {

    Button employerPageButton;
    Button logOutButton;

    Button prefButton;

    SwitchCompat jobPrefSwitch;

    ListView jobList;

    FirebaseAuth firebaseAuth;

    FirebaseUser user;

    private final String PREFS = "Prefs";
    private final String JOB_POSTINGS = "job_postings";

    DatabaseReference dbRefPref;
    DatabaseReference dbRefJobPosting;

    private JobPref jobPref;

    JobDetailAdapter adapter;

    ArrayList<JobPosting> jobPostingArrayList;

    ArrayList<JobPosting> allJobs;
    ArrayList<JobPosting> jobPostingFilteredArrayList;

    /**
     * Sets up the activity when it is first created. This method initializes the UI elements
     * and sets up the job list view.
     *
     * @param savedInstanceState the saved instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        setTitle(R.string.employee_dashboard_title);

        init();

        employerPageButton = findViewById(R.id.employerPage);
        employerPageButton.setOnClickListener(buttonClickListener);

        prefButton = findViewById(R.id.pref);
        prefButton.setOnClickListener(buttonClickListener);

        logOutButton = findViewById(R.id.logout);
        logOutButton.setOnClickListener(buttonClickListener);

        jobPrefSwitch = findViewById(R.id.prefJob);

        //the array that store all the JobPosting class in this array
        jobPostingArrayList = new ArrayList<>();

        //The list view will show all the jobs on the screen
        jobList = findViewById(R.id.jobList);
        adapter = new JobDetailAdapter(getApplicationContext(), R.layout.list_view_for_job_search, jobPostingArrayList);
        jobList.setAdapter(adapter);

        jobPrefSwitch.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                if (jobPostingArrayList.isEmpty()) {
                    Utils.toast(EmployeeHomeActivity.this, "No Jobs Available");
                    jobPrefSwitch.setChecked(false);
                } else filterJobs();
            } else {
                jobPostingArrayList.clear();
                jobPostingArrayList.addAll(allJobs);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        initVars();
        getJobPref();
        getAllJobs();
    }

    private void initVars() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        dbRefPref = FirebaseDatabase.getInstance().getReference(PREFS + "/" + user.getUid());
        dbRefJobPosting = FirebaseDatabase.getInstance().getReference(JOB_POSTINGS);
    }

    private void getJobPref() {
        dbRefPref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JobPref tempData = snapshot.getValue(JobPref.class);
                if (tempData != null) {
                    jobPref = tempData;
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
                    jobPostingArrayList.clear();
                    jobPostingArrayList.addAll(allJobs);
                    adapter.notifyDataSetChanged();
                    jobPrefSwitch.setEnabled(true);
                } else Utils.toast(EmployeeHomeActivity.this, "No Jobs Found");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                final String errorRead = error.getMessage();
                Utils.toast(EmployeeHomeActivity.this, errorRead);
            }
        });
    }

    private void filterJobs() {
        jobPostingFilteredArrayList = new ArrayList<>();
        for (JobPosting posting : jobPostingArrayList) {
            if (
                    Objects.equals(posting.getExpectedDuration(), jobPref.getJobDuration())
                            || Objects.equals(posting.getPlace(), jobPref.getPlace())
                            || Objects.equals(posting.getSalary(), jobPref.getSalary())
            ) {
                jobPostingFilteredArrayList.add(posting);
            }
        }
        if (jobPostingFilteredArrayList.isEmpty()) {
            Utils.toast(EmployeeHomeActivity.this, "No Matching Jobs Found!");
            return;
        }
        jobPostingArrayList.clear();
        jobPostingArrayList.addAll(jobPostingFilteredArrayList);
        adapter.notifyDataSetChanged();
    }

    /**
     * OnClickListener for the employerPage and logOut buttons. Redirects the user to the employer
     * dashboard or the login page based on which button is clicked.
     */
    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            // checking which button is clicked, then redirecting accordingly
            switch (v.getId()) {
                case R.id.employerPage:
                    Intent employerPageIntent = new Intent(EmployeeHomeActivity.this, EmployerHomeActivity.class);
                    startActivity(employerPageIntent);
                    break;
                case R.id.pref:
                    Intent prefPageIntent = new Intent(EmployeeHomeActivity.this, JobPrefActivity.class);
                    startActivity(prefPageIntent);
                    break;
                case R.id.logout:
                    firebaseAuth.signOut();
                    finish();
                    Intent loginIntent = new Intent(EmployeeHomeActivity.this, LoginPageActivity.class);
                    startActivity(loginIntent);
                    break;
                default:
                    break;
            }
        }
    };

}