package com.group13project;

import android.content.Intent;
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

        JobPosting clicked = getIntent().getExtras().getParcelable("JobClicked");
        TextView clickedJob = (TextView)findViewById(R.id.clickedJob);
        clickedJob.setText(clicked.toString());
    }
}
