package com.group13project;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JobPostingTest {

    // Test job posting constructor
    @Test
    public void testConstructor() {
        JobPosting jobPosting = new JobPosting("Software Engineer", "Develop software applications", "6 months", "San Francisco", "Urgent", "$100,000", "E12345");

        assertEquals("Software Engineer", jobPosting.getJobTitle());
        assertEquals("Develop software applications", jobPosting.getJobDescription());
        assertEquals("6 months", jobPosting.getExpectedDuration());
        assertEquals("San Francisco", jobPosting.getPlace());
        assertEquals("Urgent", jobPosting.getUrgency());
        assertEquals("$100,000", jobPosting.getSalary());
        assertEquals("E12345", jobPosting.getEmployerId());
        assertEquals(new ArrayList<>(), jobPosting.getApplicantsIds());
    }

    // Test job posting setters
    @Test
    public void testSetters() {
        JobPosting jobPosting = new JobPosting("Software Engineer", "Develop software applications", "6 months", "San Francisco", "Urgent", "$100,000", "E12345");

        jobPosting.setJobTitle("Software Developer");
        jobPosting.setJobDescription("Build software systems");
        jobPosting.setExpectedDuration("12 months");
        jobPosting.setPlace("New York");
        jobPosting.setUrgency("Moderate");
        jobPosting.setSalary("$120,000");
        jobPosting.setEmployerId("E67890");

        assertEquals("Software Developer", jobPosting.getJobTitle());
        assertEquals("Build software systems", jobPosting.getJobDescription());
        assertEquals("12 months", jobPosting.getExpectedDuration());
        assertEquals("New York", jobPosting.getPlace());
        assertEquals("Moderate", jobPosting.getUrgency());
        assertEquals("$120,000", jobPosting.getSalary());
        assertEquals("E67890", jobPosting.getEmployerId());
    }

    // Test addApplicant() and getApplicantsIds()
    @Test
    public void testAddApplicant() {
        JobPosting jobPosting = new JobPosting("Software Engineer", "Develop software applications", "6 months", "San Francisco", "Urgent", "$100,000", "E12345");

        jobPosting.addApplicant("A123");
        jobPosting.addApplicant("A456");
        ArrayList<String> expectedApplicants = new ArrayList<>();
        expectedApplicants.add("A123");
        expectedApplicants.add("A456");

        assertEquals(expectedApplicants, jobPosting.getApplicantsIds());
    }

    // Test job posting equality
    @Test
    public void testEquality() {
        JobPosting jobPosting1 = new JobPosting("Software Engineer", "Develop software applications", "6 months", "San Francisco", "Urgent", "$100,000", "E12345");
        JobPosting jobPosting2 = new JobPosting("Software Engineer", "Develop software applications", "6 months", "San Francisco", "Urgent", "$100,000", "E12345");
        JobPosting jobPosting3 = new JobPosting("Software Developer", "Build software systems", "12 months", "New York", "Moderate", "$120,000", "E67890");

        assertTrue(jobPosting1.equals(jobPosting2));
        assertFalse(jobPosting1.equals(jobPosting3));
    }
}
