package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This activity serves as the home screen for employers, providing access to the different features of the app.
 */
public class EmployerHomeActivity extends AppCompatActivity {

    Button newJob;
    Button postedJob;
    Button makePayment;


    /**
     * This method is called when the activity is created.
     * It initializes the UI components and sets click listeners for the buttons.
     *
     * @param savedInstanceState saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home);
        setTitle(R.string.employer_dashboard_title);

        newJob = findViewById(R.id.newJob);
        newJob.setOnClickListener(buttonClickListener);

        postedJob = findViewById(R.id.postedJobs);
        postedJob.setOnClickListener(buttonClickListener);

        makePayment = findViewById(R.id.makePayment);
        makePayment.setOnClickListener(buttonClickListener);
    }


    /**
     * This click listener handles clicks on the buttons in the activity.
     * It redirects the user to the appropriate activity based on the button that was clicked.
     */
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // checking which button is clicked, then redirecting accordingly
            switch (v.getId()) {
                case R.id.newJob:
                    Intent newJobActivity = new Intent(EmployerHomeActivity.this, EmployerNewPostActivity.class);
                    startActivity(newJobActivity);
                    break;
                case R.id.postedJobs:
                    Intent postedJobsActivity = new Intent(EmployerHomeActivity.this, EmployerPostedJobs.class);
                    startActivity(postedJobsActivity);
                    break;
                case R.id.makePayment:
                    Intent makePaymentactivity = new Intent(EmployerHomeActivity.this, EmployerMakePayment.class);
                    startActivity(makePaymentactivity);
                    break;
                default:
                    break;
            }
        }
    };
}