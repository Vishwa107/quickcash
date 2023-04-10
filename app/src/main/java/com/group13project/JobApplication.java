package com.group13project;

public class JobApplication {
    private String jobPostingID;
    private String employeeID;
    private String employerID;


    /**
     * empty contractor
     */
    public JobApplication() {
    }

    /**
     * The constructor for the job application class.
     * @param jobPostingID the job posting's ID
     * @param employeeID the employee's ID
     * @param employerID the employer's ID
     */
    public JobApplication(String jobPostingID, String employeeID, String employerID) {
        this.jobPostingID = jobPostingID;
        this.employeeID = employeeID;
        this.employerID = employerID;
    }

    public String getJobPostingID() {
        return jobPostingID;
    }

    public void setJobPostingID(String jobPostingID) {
        this.jobPostingID = jobPostingID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }
}
