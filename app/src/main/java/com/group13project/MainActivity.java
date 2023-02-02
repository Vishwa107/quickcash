package com.group13project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button employeeDashboardBtn, employerDashboardBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employeeDashboardBtn = (Button) findViewById(R.id.employeeDashboardBtn);
        employerDashboardBtn = (Button) findViewById(R.id.employerDashboardBtn);

        init();
    }


    private void init() {
        employeeDashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EmployeeHomeActivity.class));
            }
        });

        employerDashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EmployerHomeActivity.class));
            }
        });
    }
}