package com.group13project;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

public class EmployeeHomepageEspresso {

    @Rule
    public ActivityScenarioRule<EmployeeHomeActivity> myRule = new ActivityScenarioRule<>(EmployeeHomeActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }


    @Test
    public void checkIfSwitchToEmployerPage(){


        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.employerPage)).perform(click());
        intended(hasComponent(EmployerHomeActivity.class.getName()));
    }

    @Test
    public void checkIfSwitchToLoginPage(){
        onView(withId(R.id.logout)).perform(click());
        intended(hasComponent(LoginPageActivity.class.getName()));
    }

}
