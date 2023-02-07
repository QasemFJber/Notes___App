package com.shashankbhat.notesapp.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.shashankbhat.notesapp.MainActivity;
import com.shashankbhat.notesapp.R;
import com.shashankbhat.notesapp.room.Note;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by SHASHANK BHAT on 12-Sep-20.
 */
@RunWith(AndroidJUnit4.class)
public class ShowAllNotesTest {

    @Rule
    public ActivityScenarioRule<MainActivity> main = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void performTest(){


//        AboutApp aboutApp = new AboutApp();
//        main.getScenario()
//                .onActivity(activity ->
//                    activity
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_host_container, aboutApp)
//                        .commit()
//                );
//
//        ShowAllNotes showAllNotes = new ShowAllNotes();
//
//        main.getScenario()
//                .onActivity(activity ->
//                        activity
//                                .getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.nav_host_container, showAllNotes)
//                                .commit()
//                );


        onView(withId(R.id.floating_action_button)).perform(click());


    }

}