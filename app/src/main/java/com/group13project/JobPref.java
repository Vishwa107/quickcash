package com.group13project;

import androidx.annotation.NonNull;

/**
 * `JobPref` class represents the currently logged in users job preference
 */

public class JobPref {
    private String jobDuration;
    private String place;
    private String salary;

    /**
     * Initializes an empty `JobPref` instance with default attribute values.
     */
    public JobPref() {
    }


    /**
     * Initializes a `JobPref` instance with the given attribute values.
     *
     * @param jobDuration the expected job duration of the user
     * @param place       the desired job place of the user
     * @param salary      the expected job salary of the user
     */
    public JobPref(String jobDuration, String place, String salary) {
        this.jobDuration = jobDuration;
        this.place = place;
        this.salary = salary;
    }

    /**
     * returns the duration of the job
     *
     * @return job duration
     */
    public String getJobDuration() {
        return jobDuration;
    }

    /**
     * sets the duration of the job
     *
     * @param jobDuration duration of the job
     */
    public void setJobDuration(String jobDuration) {
        this.jobDuration = jobDuration;
    }

    /**
     * returns the place of the job
     *
     * @return job place
     */
    public String getPlace() {
        return place;
    }

    /**
     * sets the place of the job
     *
     * @param place place of the job
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * returns the salary of the job
     *
     * @return job salary
     */
    public String getSalary() {
        return salary;
    }

    /**
     * sets the salary of the job
     *
     * @param salary salary of the job
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     * convert the whole `JobPref` object in string
     * concatenating all the attributes separated by a semicolon
     *
     * @return a string
     */
    @NonNull
    @Override
    public String toString() {
        return getJobDuration() + ";" + getPlace() + ";" + getSalary();
    }
}
