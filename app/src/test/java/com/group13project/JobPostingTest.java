package com.group13project;

import org.junit.Test;

import static org.junit.Assert.*;

public class JobPostingTest {

    // Test job posting constructor
    @Test
    public void testConstructor() {
        JobPosting jobPosting = new JobPosting("Software Engineer", "Develop software applications", "6 months", "San Francisco", "Urgent", "$100,000");

        assertEquals("Software Engineer", jobPosting.getJobTitle());
        assertEquals("Develop software applications", jobPosting.getJobDescription());
        assertEquals("6 months", jobPosting.getExpectedDuration());
        assertEquals("San Francisco", jobPosting.getPlace());
        assertEquals("Urgent", jobPosting.getUrgency());
        assertEquals("$100,000", jobPosting.getSalary());
    }

    // Test job posting setters
    @Test
    public void testSetters() {
        JobPosting jobPosting = new JobPosting("Software Engineer", "Develop software applications", "6 months", "San Francisco", "Urgent", "$100,000");

        jobPosting.setJobTitle("Software Developer");
        jobPosting.setJobDescription("Build software systems");
        jobPosting.setExpectedDuration("12 months");
        jobPosting.setPlace("New York");
        jobPosting.setUrgency("Moderate");
        jobPosting.setSalary("$120,000");

        assertEquals("Software Developer", jobPosting.getJobTitle());
        assertEquals("Build software systems", jobPosting.getJobDescription());
        assertEquals("12 months", jobPosting.getExpectedDuration());
        assertEquals("New York", jobPosting.getPlace());
        assertEquals("Moderate", jobPosting.getUrgency());
        assertEquals("$120,000", jobPosting.getSalary());
    }

    // Test job posting equality
    @Test
    public void testEquality() {
        JobPosting jobPosting1 = new JobPosting("Software Engineer", "Develop software applications", "6 months", "San Francisco", "Urgent", "$100,000");
        JobPosting jobPosting2 = new JobPosting("Software Engineer", "Develop software applications", "6 months", "San Francisco", "Urgent", "$100,000");
        JobPosting jobPosting3 = new JobPosting("Software Developer", "Build software systems", "12 months", "New York", "Moderate", "$120,000");

        assertTrue(jobPosting1.equals(jobPosting2));
        assertFalse(jobPosting1.equals(jobPosting3));
    }
}
