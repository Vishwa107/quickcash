package com.group13project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;
import com.group13project.JobPosting;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This activity allows employers to create new job postings by entering job details and posting them to the database.
 */
public class EmployerNewPostActivity extends AppCompatActivity {

    private EditText jobTitleEditText;
    private EditText jobDescriptionEditText;
    private EditText expectedDurationEditText;
    private EditText placeEditText;
    private EditText urgencyEditText;
    private EditText salaryEditText;
    private Button postButton;
    private DatabaseReference mDatabase;
    private final static String userSignupTopic = "UserSignup";
    private final static String PUSH_NOTIFICATION_ENDPOINT = "https://fcm.googleapis.com/fcm/send";
    private RequestQueue requestQueue;
    private FirebaseMessageReceiver messageReceiver;

    private void init(){
        postButton= findViewById(R.id.postButton);
        requestQueue=Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("jobs");
    }

    private void setListeners(){
        postButton.setOnClickListener(view->sendNotification());
    }
    /**
     * This method is called when the activity is created.
     * It initializes the UI components and sets a click listener for the post button.
     *
     * @param savedInstanceState saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_new_post);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize UI components
        jobTitleEditText = findViewById(R.id.jobTitleEditText);
        jobDescriptionEditText = findViewById(R.id.jobDescriptionEditText);
        expectedDurationEditText = findViewById(R.id.expectedDurationEditText);
        placeEditText = findViewById(R.id.placeEditText);
        urgencyEditText = findViewById(R.id.urgencyEditText);
        salaryEditText = findViewById(R.id.salaryEditText);
        init();

        // Set click listener for post button
        postButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the post button is clicked.
             * It gets the job posting details from the UI components, creates a new job posting, and saves it to the database.
             *
             * @param view the view that was clicked
             */
            @Override
            public void onClick(View view) {
                // Get job posting details from UI components
                String jobTitle = jobTitleEditText.getText().toString();
                String jobDescription = jobDescriptionEditText.getText().toString();
                String expectedDuration = expectedDurationEditText.getText().toString();
                String place = placeEditText.getText().toString();
                String urgency = urgencyEditText.getText().toString();
                String salary = salaryEditText.getText().toString();

                // Create a new job posting
                JobPosting jobPosting = new JobPosting(jobTitle, jobDescription, expectedDuration, place, urgency, salary, FirebaseAuth.getInstance().getCurrentUser().getUid());

                // Save the job posting to the database
                saveJobPosting(jobPosting);

                // Clear the UI components
                jobTitleEditText.setText("");
                jobDescriptionEditText.setText("");
                expectedDurationEditText.setText("");
                placeEditText.setText("");
                urgencyEditText.setText("");
                salaryEditText.setText("");
            }
        });
    }

private void sendNotification() {
    try{
        final JSONObject notiText = new JSONObject();
        notiText.put("title", "NEW JOB POSTING AVAILABLE");
        notiText.put("body", "A new job posting has been created. Go check it out!");

        //final JSONObject metadata = new JSONObject();
        //metadata.put("jobId", "HF-111111");
        //.put("jobLocation", "Halifax");

        final JSONObject notification = new JSONObject();
        notification.put("to", "/topics/jobs");
        notification.put("notification", notiText);
        //notification.put("data", metadata);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, PUSH_NOTIFICATION_ENDPOINT,
                notification, (Response.Listener<JSONObject>) response -> Toast.makeText(EmployerNewPostActivity.this, "Notification delivered.", Toast.LENGTH_SHORT).show(),
                Throwable::printStackTrace) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<>();
                headers.put("content-type", "application/json");
                headers.put("authorization", "key=" + BuildConfig.FIREBASE_SERVER_KEY);
                return headers;
            }
        };

        requestQueue.add(request);
    }catch(JSONException e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
}


    /**
     * This method saves a job posting to the Firebase database.
     *
     * @param jobPosting the job posting to save
     */
    private void saveJobPosting(JobPosting jobPosting) {
        String key = mDatabase.child("job_postings").push().getKey();
        mDatabase.child("job_postings").child(key).setValue(jobPosting)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    /**
                     * This method is called when the job posting is saved successfully.
                     *
                     * @param aVoid the result of the operation
                     */
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EmployerNewPostActivity.this, "Job posting created successfully", Toast.LENGTH_SHORT).show();
                        sendNotification();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    /**
                     * This method is called when there is an error saving the job posting to the database.
                     *
                     * @param e the exception that was thrown
                     */
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EmployerNewPostActivity.this, "Error creating job posting: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
