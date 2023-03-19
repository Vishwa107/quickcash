package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * The `JobSearchPageActivity` class is responsible for displaying the list of job postings to the user.
 */

public class JobSearchPageActivity extends AppCompatActivity {

    Button employeePageButton;
    Button employerPageButton;
    Button logOutButton;


    /**
     * Initializes the `JobSearchPageActivity` instance and sets up the layout.
     * Also initializes the buttons and the `JobPosting` array list.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search_page);

        employeePageButton = findViewById(R.id.employeePage);
        employeePageButton.setOnClickListener(buttonClickListener);

        employerPageButton = findViewById(R.id.employerPage);
        employerPageButton.setOnClickListener(buttonClickListener);

        logOutButton = findViewById(R.id.logout);
        logOutButton.setOnClickListener(buttonClickListener);


        ArrayList<JobPosting> jobPostingArrayList = new ArrayList<>();


        ListView jobList = (ListView)findViewById(R.id.jobList);
        JobDetailAdapter adapter = new JobDetailAdapter(getApplicationContext(), R.layout.list_view_for_job_search, jobPostingArrayList);
        jobList.setAdapter(adapter);
    }



    /**
     * This method retrieves the job postings data from the database and fills the `jobPostingArrayList` array.
     *
     * @param jobPostingArrayList The array list to fill with job postings data.
     */
    public void fillArray(ArrayList<JobPosting> jobPostingArrayList){
        //need implementation
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // checking which button is clicked, then redirecting accordingly
            switch (v.getId()) {
                case R.id.employeePage:
                    Intent employeePageIntent = new Intent(JobSearchPageActivity.this, EmployeeHomeActivity.class);
                    startActivity(employeePageIntent);
                    break;
                case R.id.employerPage:
                    Intent employerPageIntent = new Intent(JobSearchPageActivity.this, EmployerHomeActivity.class);
                    startActivity(employerPageIntent);
                    break;
                case R.id.logout:
                    Intent loginIntent = new Intent(JobSearchPageActivity.this, LoginPageActivity.class);
                    startActivity(loginIntent);
                    break;
                default:
                    break;
            }
        }
    };
}