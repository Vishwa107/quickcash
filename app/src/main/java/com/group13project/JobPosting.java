package com.group13project;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The `JobPosting` class represents a job posting with its attributes such as job title, description, expected duration, location, urgency, salary, employer ID, and list of applicants' IDs.
 */
public class JobPosting {
    private String jobTitle;
    private String jobDescription;
    private String expectedDuration;
    private String place;
    private String urgency;
    private String salary;
    private String employerId;
    private ArrayList<String> applicantsIds;


    /**
     * Initializes an empty `JobPosting` instance with default attribute values.
     */
    public  JobPosting(){}


    /**
     * Initializes a `JobPosting` instance with the given attribute values.
     *
     * @param jobTitle The title of the job.
     * @param jobDescription The description of the job.
     * @param expectedDuration The expected duration of the job.
     * @param place The location of the job.
     * @param urgency The urgency level of the job.
     * @param salary The salary of the job.
     * @param employerId The ID of the employer who posted the job.
     */
    public JobPosting(String jobTitle, String jobDescription, String expectedDuration, String place, String urgency, String salary, String employerId) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.expectedDuration = expectedDuration;
        this.place = place;
        this.urgency = urgency;
        this.salary = salary;
        this.employerId = employerId;
        this.applicantsIds = new ArrayList<>();
    }

    /**
     * Returns the title of the job.
     *
     * @return The title of the job.
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the title of the job.
     *
     * @param jobTitle The title of the job.
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Returns the description of the job.
     *
     * @return The description of the job.
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     * Sets the description of the job.
     *
     * @param jobDescription The description of the job.
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    /**
     * Returns the expected duration of the job.
     *
     * @return The expected duration of the job.
     */
    public String getExpectedDuration() {
        return expectedDuration;
    }

    /**
     * Sets the expected duration of the job.
     *
     * @param expectedDuration The expected duration of the job.
     */
    public void setExpectedDuration(String expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    /**
     * Returns the location of the job.
     *
     * @return The location of the job.
     */
    public String getPlace() {
        return place;
    }

    /**
     * Sets the location of the job.
     *
     * @param place The location of the job.
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Returns the urgency level of the job.
     *
     * @return The urgency level of the job.
     */
    public String getUrgency() {
        return urgency;
    }

    /**
     * Sets the urgency level of the job.
     *
     * @param urgency The urgency level of the job.
     */
    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    /**
     * Returns the salary of the job.
     *
     * @return The salary of the job.
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the job.
     *
     * @param salary The salary of the job.
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     * Returns the ID of the employer who posted the job.
     *
     * @return The ID of the employer who posted the job.
     */
    public String getEmployerId() {
        return employerId;
    }

    /**
     * Sets the ID of the employer who posted the job.
     *
     * @param employerId The ID of the employer who posted the job.
     */
    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    /**
     * Returns the list of applicant IDs for the job.
     *
     * @return The list of applicant IDs for the job.
     */
    public ArrayList<String> getApplicantsIds() {
        return applicantsIds;
    }

    /**
     * Adds a new applicant ID to the list of applicants for the job.
     *
     * @param newApplicantsId The ID of the new applicant.
     */
    public void addApplicant(String newApplicantsId) {
        this.applicantsIds.add(newApplicantsId);
    }


    /**
     * Returns `true` if this `JobPosting` instance is equal to the given object, `false` otherwise.
     *
     * @param obj The object to compare to.
     * @return `true` if this `JobPosting` instance is equal to the given object, `false` otherwise.
     */
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
