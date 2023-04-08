package com.group13project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JobDescription extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);
        setTitle("Description");

        JobPosting clickedJob = (JobPosting) getIntent().getSerializableExtra("JobClicked");
        TextView jobDescriptionTextView = (TextView) findViewById(R.id.clickedJob);
        jobDescriptionTextView.setText(clickedJob.toString());
    }
}
