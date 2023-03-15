package com.group13project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EmployeeHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        setTitle(R.string.employee_dashboard_title);
    }
}