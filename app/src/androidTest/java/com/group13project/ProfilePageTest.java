package com.group13project;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProfilePageTest {

    @Rule
    public ActivityScenarioRule<ProfilePage> activityScenarioRule =
            new ActivityScenarioRule<>(ProfilePage.class);

    @Test
    public void testNewJobPostingButton() {
        Intents.init();
        onView(withId(R.id.newJob)).perform(click());
        intended(hasComponent(EmployerNewPostActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testPostedJobsButton() {
        Intents.init();
        onView(withId(R.id.postedJobs)).perform(click());
        intended(hasComponent(EmployerPostedJobs.class.getName()));
        Intents.release();
    }
}
