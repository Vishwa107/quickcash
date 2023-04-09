package com.group13project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChooseViewActivityTest {

    @Rule
    public ActivityScenarioRule<ChooseViewActivity>activityScenarioRule=new ActivityScenarioRule<>(ChooseViewActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkSwitchingToEmployeeViewOnButtonClick(){
        onView(withId(R.id.employee_view)).perform(click());
        intended(hasComponent(EmployeeHomeActivity.class.getName()));
    }
    @Test
    public void checkSwitchingToEmployerViewOnButtonClick(){
        onView(withId(R.id.employer_view)).perform(click());
        intended(hasComponent(EmployerHomeActivity.class.getName()));
    }

}
