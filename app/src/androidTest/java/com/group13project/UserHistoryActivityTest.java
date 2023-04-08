package com.group13project;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
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
public class UserHistoryActivityTest {

    @Rule
    public ActivityScenarioRule<UserHistoryActivity> activityRule =
            new ActivityScenarioRule<>(UserHistoryActivity.class);

    @Test
    public void testUserHistoryActivityTitleDisplayed() {
        Espresso.onView(withId(R.id.user_history_title))
                .check(matches(isDisplayed()))
                .check(matches(withText("User Job History")));
    }

    @Test
    public void testUserHistoryListDisplayed() {
        Espresso.onView(withId(R.id.user_history_list))
                .check(matches(isDisplayed()));
    }
}
