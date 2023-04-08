package com.group13project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class JobListAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> jobInfoList;
    private ArrayList<String> employerIdsList;
    private OnHistoryClickListener historyClickListener;

    public JobListAdapter(Context context, ArrayList<String> jobInfoList, ArrayList<String> employerIdsList, OnHistoryClickListener historyClickListener) {
        super(context, R.layout.list_item_job, jobInfoList);
        this.context = context;
        this.jobInfoList = jobInfoList;
        this.employerIdsList = employerIdsList;
        this.historyClickListener = historyClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_job, parent, false);
        }

        TextView jobInfoTextView = convertView.findViewById(R.id.jobInfoTextView);
        Button historyButton = convertView.findViewById(R.id.historyButton);

        jobInfoTextView.setText(jobInfoList.get(position));

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
}
