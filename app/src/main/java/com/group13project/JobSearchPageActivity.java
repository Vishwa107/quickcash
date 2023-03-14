package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class JobSearchPageActivity extends AppCompatActivity {

    Button employeePageButton;
    Button employerPageButton;
    Button log_outButton;

    ArrayList<JobPosting> jobArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search_page);

        employeePageButton = findViewById(R.id.employeePage);
        employeePageButton.setOnClickListener(buttonClickListener);

        employerPageButton = findViewById(R.id.employerPage);
        employerPageButton.setOnClickListener(buttonClickListener);

        log_outButton = findViewById(R.id.logout);
        log_outButton.setOnClickListener(buttonClickListener);

       // ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.)
    }

    //this method will fill the job array with jobs from the filter
    public ArrayList<JobPosting> fillJobArray(ArrayList<JobPosting> jobArray){
        //incomplete method
        return jobArray;
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