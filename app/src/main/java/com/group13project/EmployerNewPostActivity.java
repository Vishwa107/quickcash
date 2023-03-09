package com.group13project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group13project.JobPosting;

public class EmployerNewPostActivity extends AppCompatActivity {

    private EditText jobTitleEditText;
    private EditText jobDescriptionEditText;
    private EditText expectedDurationEditText;
    private EditText placeEditText;
    private EditText urgencyEditText;
    private EditText salaryEditText;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_new_post);

        // Initialize UI components
        jobTitleEditText = findViewById(R.id.jobTitleEditText);
        jobDescriptionEditText = findViewById(R.id.jobDescriptionEditText);
        expectedDurationEditText = findViewById(R.id.expectedDurationEditText);
        placeEditText = findViewById(R.id.placeEditText);
        urgencyEditText = findViewById(R.id.urgencyEditText);
        salaryEditText = findViewById(R.id.salaryEditText);
        postButton = findViewById(R.id.postButton);

        // Set click listener for post button
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get job posting details from UI components
                String jobTitle = jobTitleEditText.getText().toString();
                String jobDescription = jobDescriptionEditText.getText().toString();
                String expectedDuration = expectedDurationEditText.getText().toString();
                String place = placeEditText.getText().toString();
                String urgency = urgencyEditText.getText().toString();
                String salary = salaryEditText.getText().toString();

                // Create a new job posting
                JobPosting jobPosting = new JobPosting(jobTitle, jobDescription, expectedDuration, place, urgency, salary);

                // TODO: Save the job posting to the database or server

                // Show a toast message to confirm job posting creation
                Toast.makeText(EmployerNewPostActivity.this, "Job posting created successfully", Toast.LENGTH_SHORT).show();

                // Clear the UI components
                jobTitleEditText.setText("");
                jobDescriptionEditText.setText("");
                expectedDurationEditText.setText("");
                placeEditText.setText("");
                urgencyEditText.setText("");
                salaryEditText.setText("");
            }
        });
    }
}