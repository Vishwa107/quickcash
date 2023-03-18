package com.group13project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

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

public class EmployerPostedJobs extends AppCompatActivity {
    ArrayList<String> jobsList = new ArrayList<>();
    FirebaseUser user = null;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_posted_job);
        setTitle("Posted Jobs");

        TextView jobs = (TextView)findViewById(R.id.jobs);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("job_postings");
        databaseReference
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        JobPosting jobPosting = snapshot.getValue(JobPosting.class);
                        if(jobPosting != null  && jobPosting.getEmployerId()!=null && jobPosting.getEmployerId().equals(userID)){
                            final String info = jobPosting.getJobTitle() + "\nUrgency:" + jobPosting.getUrgency() + "\n" + jobPosting.getPlace() + "\n" + jobPosting.getExpectedDuration() + "\nSalary: " + jobPosting.getSalary() + "\n" + jobPosting.getJobDescription() + "\n\n";
                            jobsList.add(info);
                        }
                    }
                    String allJobs = "";
                    for(String text : jobsList){
                        allJobs += text;
                    }
                    jobs.setText(allJobs);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    jobs.setText(databaseError.getMessage());
                }
            });

    }

}
