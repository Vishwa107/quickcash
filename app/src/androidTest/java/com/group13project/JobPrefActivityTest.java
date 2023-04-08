package com.group13project;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JobPrefActivityTest {

    /**
     * comment out the `enableEdit(false)` and `getJobPref()` line
     * from `init()` function before running it. it has to be done because
     * by default, the activity is trying to fetch the already available preference
     * which will override the testing data and ultimately will interrupt the espresso testing process
     */

    public final String DURATION = "2 years";
    public final String PLACE = "Dhaka";
    public final String SALARY = "45000 BDT";

    public JobPref jobPref;

    @Rule
    public ActivityScenarioRule<JobPrefActivity> activityScenarioRule = new ActivityScenarioRule<>(JobPrefActivity.class);

    @Before
    public void setUp() {
        jobPref = new JobPref();
    }

    @Test
    public void testSavePrefBtn() {

        Espresso.onView(ViewMatchers.withId(R.id.jobDuration)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.jobDuration)).perform(ViewActions.typeText(DURATION));

        Espresso.onView(ViewMatchers.withId(R.id.jobLocation)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.jobLocation)).perform(ViewActions.typeText(PLACE));

        Espresso.onView(ViewMatchers.withId(R.id.jobSalary)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.jobSalary)).perform(ViewActions.typeText(SALARY));

        Espresso.closeSoftKeyboard();

        activityScenarioRule.getScenario().onActivity(activity -> {

            String duration = ((EditText) activity.findViewById(R.id.jobDuration)).getText().toString();
            String location = ((EditText) activity.findViewById(R.id.jobLocation)).getText().toString();
            String salary = ((EditText) activity.findViewById(R.id.jobSalary)).getText().toString();

            jobPref.setJobDuration(duration);
            jobPref.setPlace(location);
            jobPref.setSalary(salary);
        });

        // mocking the firebase relative database
        JobPref newlySavedPref = getSavedDataFromFirebase();

        Espresso.onView(ViewMatchers.withId(R.id.jobDuration)).check(matches(withText(newlySavedPref.getJobDuration())));
        Espresso.onView(ViewMatchers.withId(R.id.jobLocation)).check(matches(withText(newlySavedPref.getPlace())));
        Espresso.onView(ViewMatchers.withId(R.id.jobSalary)).check(matches(withText(newlySavedPref.getSalary())));

    }

    @After
    public void cleanUp() {
        Espresso.onView(ViewMatchers.withId(R.id.jobDuration)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.jobLocation)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.jobSalary)).perform(ViewActions.clearText());
    }

    public JobPref getSavedDataFromFirebase() {
        return new JobPref(DURATION, PLACE, SALARY);
    }

}
