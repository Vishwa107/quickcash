package com.group13project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * this class helps a user to update his/her job preference
 * this helps to show relevant jobs in the homepage
 */
public class JobPrefActivity extends AppCompatActivity {

    private final String PREFS = "Prefs";

    private EditText duration, location, salary;
    private Button savePref;

    private JobPref jobPref;

    FirebaseUser user = null;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_pref);
        setTitle("My Job Preference");
        init();
    }

    /**
     * for a cleaner working environment,
     * we called all the working methods from a single method
     */
    private void init() {
        initVars();
        initUi();
        enableEdit(false);
        initCallback();
        getJobPref();
    }

    /**
     * initialize all the UI components
     */
    private void initUi() {
        duration = findViewById(R.id.jobDuration);
        location = findViewById(R.id.jobLocation);
        salary = findViewById(R.id.jobSalary);
        savePref = findViewById(R.id.savePref);
    }

    /**
     * initialize all the runtime variables
     */
    private void initVars() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(PREFS + "/" + user.getUid());
    }

    /**
     * register callback functions for the UI components
     */
    private void initCallback() {
        savePref.setOnClickListener(view -> savePref());
    }

    /**
     * fetches the current job preference
     * of the user, if available, at all
     */
    private void getJobPref() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JobPref tempData = snapshot.getValue(JobPref.class);
                enableEdit(true);
                if (tempData != null) {
                    jobPref = tempData;
                    fillUpPref();
                } else {
                    Utils.toast(JobPrefActivity.this, "No Existing Preference Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                final String errorRead = error.getMessage();
                Utils.toast(JobPrefActivity.this, errorRead);
                finish();
            }
        });
    }

    /**
     * saves the modified job preference
     * against the currently logged-in user
     */
    private void savePref() {
        if (duration.getText().toString().isEmpty()) {
            Utils.toast(JobPrefActivity.this, "Enter Job Duration");
            return;
        }
        if (location.getText().toString().isEmpty()) {
            Utils.toast(JobPrefActivity.this, "Enter Job Location");
            return;
        }
        if (salary.getText().toString().isEmpty()) {
            Utils.toast(JobPrefActivity.this, "Enter Job Salary");
            return;
        }

        if (jobPref == null) {
            jobPref = new JobPref();
        }

        jobPref.setJobDuration(duration.getText().toString());
        jobPref.setPlace(location.getText().toString());
        jobPref.setSalary(salary.getText().toString());

        enableEdit(false);

        FirebaseDatabase.getInstance().getReference(PREFS + "/")
                .child(user.getUid())
                .setValue(jobPref).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Utils.toast(JobPrefActivity.this, "Preference Saved Successfully!");
                    } else {
                        enableEdit(true);
                        Utils.toast(JobPrefActivity.this, "Failed to Save!");
                    }
                });

    }

    /**
     * fill up the edittext with already available
     * job preference values obtained from the cloud (realtime db)
     */
    private void fillUpPref() {
        duration.setText(jobPref.getJobDuration());
        location.setText(jobPref.getPlace());
        salary.setText(jobPref.getSalary());
    }

    /**
     * enable or disable all the edittext
     * so that user can modify the job preference
     *
     * @param enable determines whether we need to enable
     *               or disable the edittext
     */
    private void enableEdit(boolean enable) {
        duration.setEnabled(enable);
        location.setEnabled(enable);
        salary.setEnabled(enable);
    }

}