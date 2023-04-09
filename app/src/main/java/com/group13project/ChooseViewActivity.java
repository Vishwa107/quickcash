package com.group13project;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * ChooseViewActivity class allows the user to choose between the employer view and employee view.
 * This class also checks for location permissions and prompts the user to enable GPS if it is disabled.
 */
public class ChooseViewActivity extends AppCompatActivity implements LocationListener{

    private static final int PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;


    /**
     * Sets up the activity when it is first created. This method initializes the UI elements,
     * requests location permissions, and sets up click listeners for the employer and employee view buttons.
     *
     * @param savedInstanceState the saved instance state of the activity
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_view_activity);
        setTitle("Choose View");

        requestPermissionsIfNeeded();

        Button employerView=(Button) findViewById(R.id.employer_view);
        employerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseViewActivity.this,EmployerHomeActivity.class));
            }
        });

        Button employeeView=(Button) findViewById(R.id.employee_view);
        employeeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseViewActivity.this,EmployeeHomeActivity.class));
            }
        });


        Button my_profile_view = (Button) findViewById(R.id.myProfile_view);

        my_profile_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseViewActivity.this,EmployerProfile.class));
            }
        });
    }

    private void requestPermissionsIfNeeded() {
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

    /**
     * Handles the result of the permission request. If permissions have been granted,
     * this method gets the user's location and checks if GPS is enabled. If permissions have been denied,
     * this method requests permissions again.
     *
     * @param requestCode the request code of the permission request
     * @param permissions an array of permissions requested
     * @param grantResults an array of results for the corresponding permissions requested
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                // Permissions have been granted
                // Get location
                getLocation();
                // Check GPS status again here
                new Handler().postDelayed(this::checkGPSStatus, 2000);
            } else {
                // Permissions have been denied, request again
                requestPermissionsIfNeeded();
            }
        }
    }

    private void checkGPSStatus() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // GPS is not enabled, prompt the user to turn it on
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("GPS is disabled. Do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
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

    /**
     * Called when the location has changed. This method uses the Geocoder class to get the city name
     * based on the latitude and longitude coordinates of the user's location. It then displays the city name
     * in a toast message. Finally, this method removes location updates.
     *
     * @param location the new location
     */
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


    /**
     * Called when the provider is disabled. This method is currently empty because this functionality
     * has not been implemented yet.
     *
     * @param provider the name of the disabled provider
     */
    @Override
    public void onProviderDisabled(String provider) {        // Method empty because function not implemented yet
    }

    /**
     * Called when the provider is enabled. This method is currently empty because this functionality
     * has not been implemented yet.
     *
     * @param provider the name of the enabled provider
     */
    @Override
    public void onProviderEnabled(String provider) {
        // Method empty because function not implemented yet

    }

    /**
     * This method is deprecated and should not be used. It is currently empty because this functionality
     * has not been implemented yet.
     *
     * @param provider the name of the provider
     * @param status the status code for the provider
     * @param extras additional provider-specific information
     */
    @Deprecated
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Method empty because function not implemented yet
    }

}
