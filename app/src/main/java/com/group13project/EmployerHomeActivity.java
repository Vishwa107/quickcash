package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmployerHomeActivity extends AppCompatActivity {

    Button newJob;
    Button postedJob;
    Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home);
        setTitle(R.string.employer_dashboard_title);

        newJob = findViewById(R.id.newJob);
        newJob.setOnClickListener(buttonClickListener);

        postedJob = findViewById(R.id.postedJobs);
        postedJob.setOnClickListener(buttonClickListener);

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // checking which button is clicked, then redirecting accordingly
            switch (v.getId()) {
                case R.id.newJob:
                    Intent newJobActivity = new Intent(EmployerHomeActivity.this, EmployerNewPostActivity.class);
                    startActivity(newJobActivity);
                    break;
                case R.id.postedJobs:
                    Intent postedJobsActivity = new Intent(EmployerHomeActivity.this, EmployerPostedJobs.class);
                    startActivity(postedJobsActivity);
                    break;
                case R.id.profile:
                    Intent profileActivity = new Intent(EmployerHomeActivity.this, EmployerProfile.class);
                    startActivity(profileActivity);
                    break;
                default:
                    break;
            }
        }
    };
}