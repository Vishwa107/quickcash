package com.group13project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.List;
import java.util.Objects;

public class ApplicationsActivity extends AppCompatActivity {
    ArrayList<String> applicationsStrings = new ArrayList<>();

    ArrayList<JobApplication> applications = new ArrayList<>();

    ListView applicationsListView;

    FirebaseUser user = null;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);
        setTitle("Applications");

        applicationsListView = findViewById(R.id.applicationsListView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference("job_applications");
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    JobApplication tempApplication = snapshot.getValue(JobApplication.class);
                    if(tempApplication != null && tempApplication.getEmployerID().equals(userID)){
                        applications.add(tempApplication);
                    }
                }

                // creating the strings from the retrieved application objects
                createApplicationsStrings(applications, applicationsStrings -> {
                    // do something with the applications strings
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ApplicationsActivity.this, android.R.layout.simple_list_item_1, applicationsStrings);
                    applicationsListView.setAdapter(adapter);
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void createApplicationsStrings(ArrayList<JobApplication> applications, OnApplicationsStringsCreatedListener listener){
        for (JobApplication application : applications ) {
            if(application != null){
                final String[] jobTitle = {""};
                final String[] employeeInfo = {"","","",""};

                // getting the job title
                DatabaseReference job_postings = FirebaseDatabase.getInstance().getReference("job_postings");
                job_postings.child(application.getJobPostingID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        JobPosting jobPosting = dataSnapshot.getValue(JobPosting.class);
                        if (jobPosting != null) {
                            jobTitle[0] = jobPosting.getJobTitle();
                        }
                        // getting the employee information
                        DatabaseReference employees = FirebaseDatabase.getInstance().getReference("Users");
                        employees.child(application.getEmployeeID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                if (user != null) {
                                    employeeInfo[0] = user.firstName;
                                    employeeInfo[1] = user.lastName;
                                    employeeInfo[2] = user.emailAddress;
                                    employeeInfo[3] = user.phoneNumber;
                                }
                                // creating the application string
                                String applicationInfo = "Job title: " + jobTitle[0] + "\nEmployee's information: \nName: " + employeeInfo[0] + " " + employeeInfo[1] + "\nEmail address: " + employeeInfo[2] + "\nPhone number: " + employeeInfo[3];

                                // adding the string to the applications strings
                                applicationsStrings.add(applicationInfo);
                                if (applicationsStrings.size() == applications.size()) {
                                    // all strings have been created, notify the listener
                                    listener.onApplicationsStringsCreated(applicationsStrings);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // handle error
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // handle error
                    }
                });
            }
        }
    }

    public interface OnApplicationsStringsCreatedListener {
        void onApplicationsStringsCreated(ArrayList<String> applicationsStrings);
    }




//    public void createApplicationsStrings(ArrayList<JobApplication> applications){
//        for (JobApplication application : applications ) {
//            if(application != null){
//                final String[] jobTitle = {""};
//                final String[] employeeInfo = {"","","",""};
//
//                // getting the job title
//                DatabaseReference job_postings = FirebaseDatabase.getInstance().getReference("job_postings");
//                job_postings.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            String tempJobPostingID = snapshot.getKey();
//                            if(tempJobPostingID != null && tempJobPostingID.equals(application.getJobPostingID())){
//                                jobTitle[0] = Objects.requireNonNull(snapshot.getValue(JobPosting.class)).getJobTitle();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//                // getting the employee information
//                DatabaseReference employees = FirebaseDatabase.getInstance().getReference("Users");
//                employees.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            String tempEmployeeID = snapshot.getKey();
//                            if(tempEmployeeID != null && tempEmployeeID.equals(application.getEmployeeID())){
//                                employeeInfo[0] = Objects.requireNonNull(snapshot.getValue(User.class)).firstName;
//                                employeeInfo[1] = Objects.requireNonNull(snapshot.getValue(User.class)).lastName;
//                                employeeInfo[2] = Objects.requireNonNull(snapshot.getValue(User.class)).emailAddress;
//                                employeeInfo[3] = Objects.requireNonNull(snapshot.getValue(User.class)).phoneNumber;
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//                // creating the application string
//                String applicationInfo = "Job title: " + jobTitle[0] + "\nEmployee's information: \nName: " + employeeInfo[0] + " " + employeeInfo[1] + "\nEmail address: " + employeeInfo[2] + "\nPhone number: " + employeeInfo[3];
//
//                // adding the string to the applications strings
//                applicationsStrings.add(applicationInfo);
//            }
//        }
//    }
}

