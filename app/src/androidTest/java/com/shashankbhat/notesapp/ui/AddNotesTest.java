package com.shashankbhat.notesapp.ui;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import com.shashankbhat.notesapp.MainActivity;
import com.shashankbhat.notesapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by SHASHANK BHAT on 12-Sep-20.
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class AddNotesTest {

    @Rule
    public ActivityScenarioRule<MainActivity> addNotes = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void performTest(){

        onView(withId(R.id.floating_action_button)).perform(click());

        onView(withId(R.id.title_ed))
                .perform(typeText("Add short note with description"))
                .check(matches(withText("Add short note with description")));

        onView(withId(R.id.description_et))
                .perform(typeText("Reminds everyday notes which has highest priority.\nNotes can be removed by swiping it to left or right"))
                .check(matches(withText("Reminds everyday notes which has highest priority.\nNotes can be removed by swiping it to left or right")));

        onView(withId(R.id.priority1))
                .perform(scrollTo())
                .perform(click())
                .check(matches(isChecked()));

        onView(withId(R.id.save))
                .perform(scrollTo())
                .perform(click());
    }

}