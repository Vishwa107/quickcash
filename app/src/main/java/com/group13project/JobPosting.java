package com.group13project;

import java.util.ArrayList;
import java.util.Objects;

// JobPosting class
public class JobPosting {
    private String jobTitle;
    private String jobDescription;
    private String expectedDuration;
    private String place;
    private String urgency;
    private String salary;
    private String employerId;
    private ArrayList<String> applicantsIds;

    public JobPosting(String jobTitle, String jobDescription, String expectedDuration, String place, String urgency, String salary, String employerId) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.expectedDuration = expectedDuration;
        this.place = place;
        this.urgency = urgency;
        this.salary = salary;
        this.employerId = employerId;
        this.applicantsIds = new ArrayList<String>();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(String expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public ArrayList<String> getApplicantsIds() {
        return applicantsIds;
    }

    public void addApplicant(String newApplicantsId) {
        this.applicantsIds.add(newApplicantsId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JobPosting other = (JobPosting) obj;
        if (!Objects.equals(this.jobTitle, other.jobTitle)) {
            return false;
        }
        if (!Objects.equals(this.jobDescription, other.jobDescription)) {
            return false;
        }
        if (!Objects.equals(this.expectedDuration, other.expectedDuration)) {
            return false;
        }
        if (!Objects.equals(this.place, other.place)) {
            return false;
        }
        if (!Objects.equals(this.urgency, other.urgency)) {
            return false;
        }
        if (!Objects.equals(this.salary, other.salary)) {
            return false;
        }
        if (!Objects.equals(this.employerId, other.employerId)) {
            return false;
        }
        if (!Objects.equals(this.applicantsIds, other.applicantsIds)) {
            return false;
        }
        return true;
    }
}
