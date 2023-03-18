package com.group13project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmployeeHomeActivity extends AppCompatActivity implements LocationListener{

    Button employerPageButton;
    Button log_outButton;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        setTitle(R.string.employee_dashboard_title);

        employerPageButton = findViewById(R.id.employerPage);
        employerPageButton.setOnClickListener(buttonClickListener);

        log_outButton = findViewById(R.id.logout);
        log_outButton.setOnClickListener(buttonClickListener);
        //the array that store all the JobPosting class in this array
        ArrayList<JobPosting> jobPostingArrayList = new ArrayList<JobPosting>();

        //the following data just for test purpose------------------------
        JobPosting job1 = new JobPosting("walk dog","no description", "1 hour", "21 street", "not urgen", "$13","JOGP2gnsNhf9S2E0kIXkkBGKXk32");
        JobPosting job2 = new JobPosting("look after baby", "19 year-old or above", "2 days", "young st", "urgen", "$25","JOGP2gnsNhf9S2E0kIXkkBGKXk32");

        jobPostingArrayList.add(job1);
        jobPostingArrayList.add(job2);
        //-----------------------------------------------------------------------

        //The list view will show all the jobs on the screen
        ListView jobList = (ListView)findViewById(R.id.jobList);
        JobDetailAdapter adapter = new JobDetailAdapter(getApplicationContext(), R.layout.list_view_for_job_search, jobPostingArrayList);
        jobList.setAdapter(adapter);

        // Check for permissions
        if (checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permissions have been granted
            // Get location
            getLocation();
        } else {
            // Permissions have not been granted
            // Request permissions
            String[] permissions = {android.Manifest.permission.INTERNET,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                // Permissions have been granted
                // Get location
                getLocation();
            } else {
                // Permissions have been denied
                // Show an explanation or disable functionality
                getLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 5F, (LocationListener) this);
        } else {
            Toast.makeText(this, "Please enable GPS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String cityName = addresses.get(0).getLocality();
            Toast.makeText(this, "City: " + cityName, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        locationManager.removeUpdates(this);
    }


    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // checking which button is clicked, then redirecting accordingly
            switch (v.getId()) {
//                case R.id.employeePage:
//                    Intent employeePageIntent = new Intent(JobSearchPageActivity.this, EmployeeHomeActivity.class);
//                    startActivity(employeePageIntent);
//                    break;
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