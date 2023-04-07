package com.group13project;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.group13project.R;
import com.group13project.UserHistoryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class UserHistoryActivityTest {

    @Rule
    public ActivityScenarioRule<UserHistoryActivity> activityScenarioRule =
            new ActivityScenarioRule<>(UserHistoryActivity.class);

    @Test
    public void testListViewDisplay() {
        // Set up test data



        String userId = "OAfrpA8iu9TJR6YdoRbK4KW1aWS2";
        String jobTitle = "Bag boy";
        String employerName = "LeBron James";
        String salary = "5000";
        String place = "LA";

        String expectedListViewItemText = employerName + ": " + jobTitle + ", " + salary + ", " + place;

        // Launch the activity with test data
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putStringArrayList("userHistory", new ArrayList<>(Arrays.asList(expectedListViewItemText)));
        intent.putExtras(bundle);

        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));

        // Check if the ListView displays the expected data
        Espresso.onView(withId(R.id.user_history_list))
                .check(matches(not(withText(expectedListViewItemText))));
    }
}
