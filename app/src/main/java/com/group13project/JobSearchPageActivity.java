package com.group13project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class JobSearchPageActivity extends AppCompatActivity {

    Button employeePageButton;
    Button employerPageButton;
    Button log_outButton;

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


        ArrayList<JobPosting> jobPostingArrayList = new ArrayList<JobPosting>();
        //fillArray(jobPostingArrayList);

        //the following data just for test purpose------------------------
//        JobPosting job1 = new JobPosting("walk dog","no description", "1 hour", "21 street", "not urgen", "$13");
//        JobPosting job2 = new JobPosting("look after baby", "19 year-old or above", "2 days", "young st", "urgen", "$25");
//
//        jobPostingArrayList.add(job1);
//        jobPostingArrayList.add(job2);
        //-----------------------------------------------------------------------

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