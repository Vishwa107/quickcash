package com.group13project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * An adapter for displaying job postings in a ListView.
 */
public class JobDetailAdapter extends ArrayAdapter<JobPosting> {

    ArrayList<JobPosting> jobArray = new ArrayList<>();
    Context context;
    int resource;

    /**
     * Constructs a new JobDetailAdapter with the specified context, resource ID, and job postings.
     *
     * @param context  The context of the application.
     * @param resource The resource ID of the layout for each item in the ListView.
     * @param jobArray The list of job postings to display.
     */
    public JobDetailAdapter(@NonNull Context context, int resource, ArrayList<JobPosting> jobArray) {
        super(context, resource, jobArray);
        this.context = context;
        this.resource = resource;
        this.jobArray = jobArray;
    }

    /**
     * Gets the View for each item in the ListView.
     *
     * @param position    The position of the item in the ListView.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent ViewGroup that this view will eventually be attached to.
     * @return The View for the specified position in the ListView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        JobPosting jobPosting = jobArray.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_for_job_search, parent, false);

        }
        TextView jobTitle = (TextView) convertView.findViewById(R.id.jobTitleTextView);
        TextView jobLocation = (TextView) convertView.findViewById(R.id.jobLocationTextView);
        TextView jobSalary = (TextView) convertView.findViewById(R.id.jobSalaryTextView);

        jobTitle.setText(jobPosting.getJobTitle());
        jobLocation.setText(jobPosting.getPlace());
        jobSalary.setText(jobPosting.getSalary());

        return convertView;
    }
}
