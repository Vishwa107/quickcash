package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class EmployeeHomeActivity extends AppCompatActivity{

    Button employerPageButton;
    Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        setTitle(R.string.employee_dashboard_title);

        employerPageButton = findViewById(R.id.employerPage);
        employerPageButton.setOnClickListener(buttonClickListener);

        logOutButton = findViewById(R.id.logout);
        logOutButton.setOnClickListener(buttonClickListener);
        //the array that store all the JobPosting class in this array
        ArrayList<JobPosting> jobPostingArrayList = new ArrayList<>();


        //The list view will show all the jobs on the screen
        ListView jobList = (ListView)findViewById(R.id.jobList);
        JobDetailAdapter adapter = new JobDetailAdapter(getApplicationContext(), R.layout.list_view_for_job_search, jobPostingArrayList);
        jobList.setAdapter(adapter);


    }
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // checking which button is clicked, then redirecting accordingly
            switch (v.getId()) {

                case R.id.employerPage:
                    Intent employerPageIntent = new Intent(EmployeeHomeActivity.this, EmployerHomeActivity.class);
                    startActivity(employerPageIntent);
                    break;
                case R.id.logout:
                    Intent loginIntent = new Intent(EmployeeHomeActivity.this, LoginPageActivity.class);
                    startActivity(loginIntent);
                    break;
                default:
                    break;
            }
        }
    };

}