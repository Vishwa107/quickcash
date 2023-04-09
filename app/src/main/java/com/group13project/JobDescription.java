package com.group13project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JobDescription extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String jobPostingId;
    private String employeeId;
    private String employerId;
    Button applyButton;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_job_description);
        setTitle("Description");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        JobPosting clicked = getIntent().getExtras().getParcelable("JobClicked");
        TextView clickedJob = (TextView)findViewById(R.id.clickedJob);
        clickedJob.setText(clicked.toString());

        // getting the job ID from Firebase
        Query query = mDatabase.child("job_postings")
                .orderByChild("jobTitle")
                .equalTo(clicked.getJobTitle());

        // Add a ValueEventListener to the query to listen for changes in the query results
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve the ID of the job posting from the DataSnapshot
                    jobPostingId = snapshot.getKey();
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        applyButton = findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Application");
                builder.setMessage("Are you sure you want to apply for this job?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        employeeId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        employerId = clicked.getEmployerId();

                        JobApplication application = new JobApplication(jobPostingId, employeeId, employerId);

                        checkExistingApplication(application);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    /**
     * Method that checks if there is already an application by the employee to the selected job posting.
     * @param application the application to check if exists.
     */
    private void checkExistingApplication(JobApplication application) {
        Query applications = mDatabase.child("job_applications");

        // Attach a listener to the query to check if the application already exists
        applications.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean exists = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    JobApplication tempApplication = snapshot.getValue(JobApplication.class);
                    if (tempApplication != null && tempApplication.getEmployeeID().equals(application.getEmployeeID()) && tempApplication.getJobPostingID().equals(application.getJobPostingID()) && tempApplication.getEmployerID().equals(application.getEmployerID())){
                        Toast.makeText(JobDescription.this, "Your have already applied for this job.", Toast.LENGTH_SHORT).show();
                        applyButton.setText("Application Sent");
                        applyButton.setClickable(false);
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    applyForJob(application);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error here
            }
        });
    }

    private void applyForJob(JobApplication application) {
        String key = mDatabase.child("job_applications").push().getKey();
        mDatabase.child("job_applications").child(key).setValue(application)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    /**
                     * This method is called when the job applications is saved successfully.
                     *
                     * @param aVoid the result of the operation
                     */
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(JobDescription.this, "Your application has been sent successfully", Toast.LENGTH_SHORT).show();
                        applyButton.setText("Application Sent");
                        applyButton.setClickable(false);
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
                        Toast.makeText(JobDescription.this, "Error in sending application: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
