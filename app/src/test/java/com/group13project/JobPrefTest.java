package com.group13project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class JobPrefTest {

    private final String DURATION = "1 year";
    private final String PLACE = "London";
    private final String SALARY = "5000";

    @Test
    public void testConstructor() {
        JobPref jobPref = new JobPref(DURATION, PLACE, SALARY);

        assertEquals(DURATION, jobPref.getJobDuration());
        assertEquals(PLACE, jobPref.getPlace());
        assertEquals(SALARY, jobPref.getSalary());
    }

    @Test
    public void testSetters() {
        JobPref jobPref = new JobPref();
        jobPref.setJobDuration(DURATION);
        jobPref.setPlace(PLACE);
        jobPref.setSalary(SALARY);

        assertEquals(DURATION, jobPref.getJobDuration());
        assertEquals(PLACE, jobPref.getPlace());
        assertEquals(SALARY, jobPref.getSalary());
    }

    @Test
    public void testEquality() {
        JobPref jobPref1 = new JobPref(DURATION, PLACE, SALARY);
        JobPref jobPref2 = new JobPref(DURATION, PLACE, SALARY);
        JobPref jobPref3 = new JobPref("6 months", "Dhaka", "45000 BDT");

        assertEquals(jobPref1.toString(), jobPref2.toString());
        assertNotEquals(jobPref1.toString(), jobPref3.toString());
        assertNotEquals(jobPref2.toString(), jobPref3.toString());
    }

}