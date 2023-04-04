package com.group13project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

/**
 * An activity that displays the job postings created by the currently logged in employer user.
 */
public class EmployerPostedJobs extends AppCompatActivity {
    ArrayList<String> jobsList = new ArrayList<>();
    ListView jobListView;
    FirebaseUser user = null;
    DatabaseReference databaseReference;


    /**
     * Sets up the activity's UI and retrieves the job postings created by the currently logged in employer user from Firebase.
     *
     * @param savedInstanceState    The saved state of the activity, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_posted_job);
        setTitle("Posted Jobs");

        jobListView = findViewById(R.id.jobListView);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("job_postings");
        databaseReference
            .addValueEventListener(new ValueEventListener() {
                /**
                 * Retrieves the job postings from Firebase and adds them to the list if they were created by the currently logged in employer user. Displays the list of job postings in the UI.
                 *
                 * @param dataSnapshot  The snapshot of the job postings from Firebase.
                 */
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        JobPosting jobPosting = snapshot.getValue(JobPosting.class);
                        if(jobPosting != null  && jobPosting.getEmployerId()!=null && jobPosting.getEmployerId().equals(userID)){
                            final String info = jobPosting.getJobTitle().toUpperCase() + "\nUrgency:" + jobPosting.getUrgency() + "\n" + jobPosting.getPlace() + "\n" + jobPosting.getExpectedDuration() + "\nSalary: " + jobPosting.getSalary() + "\n" + jobPosting.getJobDescription() + "\n\n";
                            jobsList.add(info);
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EmployerPostedJobs.this, android.R.layout.simple_list_item_1, jobsList);
                    jobListView.setAdapter(adapter);
                }

                /**
                 * Displays an error message in the UI if the retrieval of the job postings from Firebase fails.
                 *
                 * @param databaseError The error message from Firebase.
                 */

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(EmployerPostedJobs.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

    }

}
