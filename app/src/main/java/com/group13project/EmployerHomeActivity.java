package com.group13project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EmployerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home);
        setTitle(R.string.employer_dashboard_title);
    }
}