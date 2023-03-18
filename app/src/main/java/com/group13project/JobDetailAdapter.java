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

public class JobDetailAdapter extends ArrayAdapter<JobPosting> {

    ArrayList<JobPosting> jobArray = new ArrayList<>();
    Context context;
    int resource;

    public JobDetailAdapter(@NonNull Context context, int resource, ArrayList<JobPosting> jobArray) {
        super(context, resource, jobArray);
        this.context = context;
        this.resource = resource;
        this.jobArray = jobArray;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        JobPosting jobPosting = jobArray.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_for_job_search, parent, false);

        }
        TextView jobTitle = (TextView)convertView.findViewById(R.id.jobTitleTextView);
        TextView jobLocation = (TextView)convertView.findViewById(R.id.jobLocationTextView);
        TextView jobSalary = (TextView)convertView.findViewById(R.id.jobSalaryTextView);

        jobTitle.setText(jobPosting.getJobTitle());
        jobLocation.setText(jobPosting.getPlace());
        jobSalary.setText(jobPosting.getSalary());

        return convertView;
    }
}
