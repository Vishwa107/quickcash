package com.group13project;

import static androidx.test.espresso.Espresso.onView;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;


public class EspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);


    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfSwitchToSignUpPage(){
        onView(withId(R.id.signUpButton)).perform(click());
        intended(hasComponent(SignupPageActivity.class.getName()));
    }

    @Test
    public void checkIfSwitchToLoginPage(){
        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(LoginPageActivity.class.getName()));
    }
}
