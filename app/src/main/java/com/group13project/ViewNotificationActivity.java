package com.group13project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewNotificationActivity extends AppCompatActivity {

    private TextView nTitle;
    private TextView nBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_push_notification);
        init();
        setData();
    }

    private void init() {
        //binding the views with the variables
        nTitle = findViewById(R.id.nTitle);
        nBody = findViewById(R.id.nBody);
    }

    private void setData() {
        //whatever data is received in the push notification, the variables are being set to that
        final Bundle extras = getIntent().getExtras();
        final String title = extras.getString("title");
        final String body = extras.getString("body");
        final String jobId = extras.getString("jobId");
        final String jobLocation = extras.getString("jobLocation");

        nTitle.setText(title);
        nBody.setText(body);
    }
}
