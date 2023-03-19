package com.group13project;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.group13project.EmployerNewPostActivity;
import com.group13project.JobPosting;
import com.group13project.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class EmployerNewPostActivityTest {

    @Rule
    public ActivityScenarioRule<EmployerNewPostActivity> activityScenarioRule =
            new ActivityScenarioRule<>(EmployerNewPostActivity.class);

    @Test
    public void testPostButton() {
        // Set up test data
        String jobTitle = "Software Engineer";
        String jobDescription = "Develop software applications";
        String expectedDuration = "6 months";
        String place = "San Francisco";
        String urgency = "Urgent";
        String salary = "$100,000";
        String employerId = "E12345";

        // Enter job posting details in the UI components
        Espresso.onView(ViewMatchers.withId(R.id.jobTitleEditText)).perform(ViewActions.typeText(jobTitle));
        Espresso.onView(ViewMatchers.withId(R.id.jobDescriptionEditText)).perform(ViewActions.typeText(jobDescription));
        Espresso.onView(ViewMatchers.withId(R.id.expectedDurationEditText)).perform(ViewActions.typeText(expectedDuration));
        Espresso.onView(ViewMatchers.withId(R.id.placeEditText)).perform(ViewActions.typeText(place));
        Espresso.onView(ViewMatchers.withId(R.id.urgencyEditText)).perform(ViewActions.typeText(urgency));
        Espresso.onView(ViewMatchers.withId(R.id.salaryEditText)).perform(ViewActions.typeText(salary));

        // Click the post button
        Espresso.onView(ViewMatchers.withId(R.id.postButton)).perform(ViewActions.click());

        // Verify that the job posting was created successfully
        Espresso.onView(withId(android.R.id.message))
                .check(matches(withText("Job posting created successfully")));

        // Verify that a JobPosting object was created with the correct properties
        JobPosting jobPosting = new JobPosting(jobTitle, jobDescription, expectedDuration, place, urgency, salary, employerId);
        // TODO: Retrieve the job posting from the database or server and compare with jobPosting using assertTrue

        // Clear the UI components
        Espresso.onView(ViewMatchers.withId(R.id.jobTitleEditText)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.jobDescriptionEditText)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.expectedDurationEditText)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.placeEditText)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.urgencyEditText)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.salaryEditText)).perform(ViewActions.clearText());
    }
}
