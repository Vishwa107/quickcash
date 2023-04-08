package com.group13project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
public class JobListAdapter extends ArrayAdapter<JobPosting> {
    private Context context;
    private ArrayList<JobPosting> jobInfoList;
    private ArrayList<String> employerIdsList;
    private OnHistoryClickListener historyClickListener;
    private OnJobClickListener jobClickListener;

    public JobListAdapter(Context context, ArrayList<JobPosting> jobInfoList, ArrayList<String> employerIdsList, OnHistoryClickListener historyClickListener, OnJobClickListener jobClickListener) {
        super(context, R.layout.list_item_job, jobInfoList);
        this.context = context;
        this.jobInfoList = jobInfoList;
        this.employerIdsList = employerIdsList;
        this.historyClickListener = historyClickListener;
        this.jobClickListener = jobClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_job, parent, false);
        }

        TextView jobInfoTextView = convertView.findViewById(R.id.jobInfoTextView);
        Button historyButton = convertView.findViewById(R.id.historyButton);

        jobInfoTextView.setText(jobInfoList.get(position).toString());

        jobInfoTextView.setOnClickListener(view -> {
            if (jobClickListener != null) {
                jobClickListener.onJobClick(position);
            }
        });

        historyButton.setOnClickListener(view -> {
            if (historyClickListener != null) {
                historyClickListener.onHistoryClick(employerIdsList.get(position));
            }
        });

        return convertView;
    }

    public interface OnHistoryClickListener {
        void onHistoryClick(String employerId);
    }

    public interface OnJobClickListener {
        void onJobClick(int position);
    }
}
