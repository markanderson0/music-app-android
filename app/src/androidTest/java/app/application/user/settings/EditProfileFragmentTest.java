package app.application.user.settings;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test class to validate the behaviour of the {@link EditProfileFragment}
 */
@RunWith(AndroidJUnit4.class)
public class EditProfileFragmentTest {
    @Rule
    public ActivityTestRule<SettingsActivity> signupActivityTestRule =
            new ActivityTestRule<>(SettingsActivity.class);

    @Before
    public void init(){
        onView(ViewMatchers.withText("Edit Profile")).perform(click());
    }

    @Test
    public void validateEmail() throws Exception {
        onView(withId(R.id.profile_submit_btn)).perform(click());
        onView(withId(R.id.profile_email_input)).check(matches(hasErrorText("Enter an Email")));
        onView(withId(R.id.profile_email_input)).perform(typeText("email")).check(matches(hasErrorText("Invalid Email")));
        onView(withId(R.id.profile_email_input)).perform(clearText());
        onView(withId(R.id.profile_email_input)).check(matches(hasErrorText("Enter an Email")));
    }

    @Test
    public void validateDOB() throws Exception {
        onView(withId(R.id.profile_submit_btn)).perform(click());
        onView(withId(R.id.profile_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_day_input)).perform(typeText("1"));
        onView(withId(R.id.profile_month_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_month_input)).perform(typeText("1"));
        onView(withId(R.id.profile_year_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_year_input)).perform(typeText("2000"));
    }

    @Test
    public void validateLeapYear() throws Exception {
        onView(withId(R.id.profile_day_input)).perform(typeText("29"));
        onView(withId(R.id.profile_month_input)).perform(typeText("2"));
        onView(withId(R.id.profile_year_input)).perform(typeText("2001"));
        onView(withId(R.id.profile_submit_btn)).perform(click());
        onView(withId(R.id.profile_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_month_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_year_input)).check(matches(hasErrorText("Invalid Date")));
    }

    @Test
    public void validateInvalidFebuary() throws Exception {
        onView(withId(R.id.profile_day_input)).perform(typeText("30"));
        onView(withId(R.id.profile_month_input)).perform(typeText("2"));
        onView(withId(R.id.profile_year_input)).perform(typeText("2000"));
        onView(withId(R.id.profile_submit_btn)).perform(click());
        onView(withId(R.id.profile_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_month_input)).check(matches(hasErrorText("Invalid Date")));
    }

    @Test
    public void validateInvalidThirtyFirst() throws Exception {
        onView(withId(R.id.profile_day_input)).perform(typeText("31"));
        onView(withId(R.id.profile_month_input)).perform(typeText("4"));
        onView(withId(R.id.profile_year_input)).perform(typeText("2000"));
        onView(withId(R.id.profile_submit_btn)).perform(click());
        onView(withId(R.id.profile_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_month_input)).check(matches(hasErrorText("Invalid Date")));

        onView(withId(R.id.profile_month_input)).perform(clearText());
        onView(withId(R.id.profile_month_input)).perform(typeText("6"));
        onView(withId(R.id.profile_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_month_input)).check(matches(hasErrorText("Invalid Date")));

        onView(withId(R.id.profile_month_input)).perform(clearText());
        onView(withId(R.id.profile_month_input)).perform(typeText("9"));
        onView(withId(R.id.profile_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_month_input)).check(matches(hasErrorText("Invalid Date")));

        onView(withId(R.id.profile_month_input)).perform(clearText());
        onView(withId(R.id.profile_month_input)).perform(typeText("11"));
        onView(withId(R.id.profile_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.profile_month_input)).check(matches(hasErrorText("Invalid Date")));
    }
}
