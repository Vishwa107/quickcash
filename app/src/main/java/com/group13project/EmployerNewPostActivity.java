package com.group13project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.group13project.JobPosting;

/**
 * This activity allows employers to create new job postings by entering job details and posting them to the database.
 */
public class EmployerNewPostActivity extends AppCompatActivity {

    private EditText jobTitleEditText;
    private EditText jobDescriptionEditText;
    private EditText expectedDurationEditText;
    private EditText placeEditText;
    private EditText urgencyEditText;
    private EditText salaryEditText;
    private Button postButton;
    private DatabaseReference mDatabase;



    /**
     * This method is called when the activity is created.
     * It initializes the UI components and sets a click listener for the post button.
     *
     * @param savedInstanceState saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_new_post);
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
            /**
             * This method is called when the post button is clicked.
             * It gets the job posting details from the UI components, creates a new job posting, and saves it to the database.
             *
             * @param view the view that was clicked
             */
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
                JobPosting jobPosting = new JobPosting(jobTitle, jobDescription, expectedDuration, place, urgency, salary, FirebaseAuth.getInstance().getCurrentUser().getUid());

                // Save the job posting to the database
                saveJobPosting(jobPosting);

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

    /**
     * This method saves a job posting to the Firebase database.
     *
     * @param jobPosting the job posting to save
     */
    private void saveJobPosting(JobPosting jobPosting) {
        String key = mDatabase.child("job_postings").push().getKey();
        mDatabase.child("job_postings").child(key).setValue(jobPosting)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    /**
                     * This method is called when the job posting is saved successfully.
                     *
                     * @param aVoid the result of the operation
                     */
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EmployerNewPostActivity.this, "Job posting created successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    /**
                     * This method is called when there is an error saving the job posting to the database.
                     *
                     * @param e the exception that was thrown
                     */
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EmployerNewPostActivity.this, "Error creating job posting: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
