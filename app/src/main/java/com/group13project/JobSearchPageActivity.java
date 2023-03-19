package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class JobSearchPageActivity extends AppCompatActivity {

    Button employeePageButton;
    Button employerPageButton;
    Button logOutButton;

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


    //this method should take the job data from database and fill the array
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