package com.group13project;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EmployerHistoryActivityTest {

    @Rule
    public ActivityScenarioRule<EmployerHistoryActivity> activityRule =
            new ActivityScenarioRule<>(EmployerHistoryActivity.class);

    @Test
    public void testUserHistoryActivityTitleDisplayed() {
        Espresso.onView(withId(R.id.user_history_title))
                .check(matches(isDisplayed()))
                .check(matches(withText("Employer's Previously Posted Jobs")));
    }

    @Test
    public void testUserHistoryListDisplayed() {
        Espresso.onView(withId(R.id.user_history_list))
                .check(matches(isDisplayed()));
    }
}
