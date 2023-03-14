package com.group13project;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.group13project.JobSearchPageActivity;

public class jobSearchPageEspressoTest {

    @Rule
    public ActivityScenarioRule<JobSearchPageActivity> myRule = new ActivityScenarioRule<>(JobSearchPageActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfSwitchToEmployeePage(){
        onView(withId(R.id.employeePage)).perform(click());
        intended(hasComponent(EmployeeHomeActivity.class.getName()));
    }

    @Test
    public void checkIfSwitchToEmployerPage(){
        onView(withId(R.id.employerPage)).perform(click());
        intended(hasComponent(EmployerHomeActivity.class.getName()));
    }

    @Test
    public void checkIfSwitchToLoginPage(){
        onView(withId(R.id.logout)).perform(click());
        intended(hasComponent(LoginPageActivity.class.getName()));
    }

}
