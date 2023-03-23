package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class EmployeeHomeActivity extends AppCompatActivity{

    ArrayList<String> jobsList = new ArrayList<>();
    DatabaseReference databaseReference;
    ListView jobsListView;

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

        jobsListView = findViewById(R.id.jobListView);

        databaseReference = FirebaseDatabase.getInstance().getReference("job_postings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            /**
             * Retrieves the job postings from Firebase and adds them to the list if they were created by the currently logged in employer user. Displays the list of job postings in the UI.
             *
             * @param dataSnapshot  The snapshot of the job postings from Firebase.
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    JobPosting jobPosting = snapshot.getValue(JobPosting.class);
                    if (jobPosting != null) {
                        final String jobInfo = jobPosting.getJobTitle() + "\nUrgency:" + jobPosting.getUrgency() + "\n" + jobPosting.getPlace() + "\n" + jobPosting.getExpectedDuration() + "\nSalary: " + jobPosting.getSalary() + "\n" + jobPosting.getJobDescription() + "\n\n";
                        jobsList.add(jobInfo);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(EmployeeHomeActivity.this, android.R.layout.simple_list_item_1, jobsList);
                jobsListView.setAdapter(adapter);
            }

            /**
             * Displays an error message in the UI if the retrieval of the job postings from Firebase fails.
             *
             * @param databaseError The error message from Firebase.
             */

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EmployeeHomeActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}