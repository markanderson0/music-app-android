package app.application.account;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test class to validate the behaviour of the {@link SignupActivity}
 */
@RunWith(AndroidJUnit4.class)
public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> signupActivityTestRule =
            new ActivityTestRule<>(SignupActivity.class);

    @Test
    public void validateUsername() throws Exception {
        onView(withId(R.id.signup_submit_btn)).perform(scrollTo()).perform(click());
        onView(withId(R.id.signup_username_input)).check(matches(hasErrorText("Enter a Username")));
        onView(withId(R.id.signup_username_input)).perform(typeText("UserN")).check(matches(hasErrorText("Username too short")));
        onView(withId(R.id.signup_username_input)).perform(clearText());
        onView(withId(R.id.signup_username_input)).perform(typeText("UserNameUserNameUserN")).check(matches(hasErrorText("Username too long")));
        onView(withId(R.id.signup_username_input)).perform(clearText());
        onView(withId(R.id.signup_username_input)).check(matches(hasErrorText("Enter a Username")));
    }

    @Test
    public void validateEmail() throws Exception {
        onView(withId(R.id.signup_submit_btn)).perform(scrollTo()).perform(click());
        onView(withId(R.id.signup_email_input)).check(matches(hasErrorText("Enter an Email")));
        onView(withId(R.id.signup_email_input)).perform(typeText("email")).check(matches(hasErrorText("Invalid Email")));
        onView(withId(R.id.signup_email_input)).perform(clearText());
        onView(withId(R.id.signup_email_input)).check(matches(hasErrorText("Enter an Email")));
    }

    @Test
    public void validatePassword() throws Exception {
        onView(withId(R.id.signup_submit_btn)).perform(scrollTo()).perform(click());
        onView(withId(R.id.signup_password_input)).check(matches(hasErrorText("Enter a Password")));
        onView(withId(R.id.signup_password_input)).perform(typeText("Passw")).check(matches(hasErrorText("Password too short")));
        onView(withId(R.id.signup_password_input)).perform(clearText());
        onView(withId(R.id.signup_password_input)).perform(typeText("PasswordPasswordPassW")).check(matches(hasErrorText("Password too long")));
        onView(withId(R.id.signup_password_input)).perform(clearText());
        onView(withId(R.id.signup_password_input)).check(matches(hasErrorText("Enter a Password")));
    }

    @Test
    public void validateGender() throws Exception {
        onView(withId(R.id.signup_submit_btn)).perform(scrollTo()).perform(click());
        onView(withId(R.id.signup_male_input)).perform(click());
        onView(withId(R.id.signup_male_input)).check(matches(isChecked()));
        onView(withId(R.id.signup_female_input)).perform(click());
        onView(withId(R.id.signup_female_input)).check(matches(isChecked()));
    }

    @Test
    public void validateDOB() throws Exception {
        onView(withId(R.id.signup_submit_btn)).perform(scrollTo()).perform(click());
        onView(withId(R.id.signup_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_day_input)).perform(typeText("1"));
        onView(withId(R.id.signup_month_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_month_input)).perform(typeText("1"));
        onView(withId(R.id.signup_year_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_year_input)).perform(typeText("2000"));
    }

    @Test
    public void validateLeapYear() throws Exception {
        onView(withId(R.id.signup_day_input)).perform(typeText("29"));
        onView(withId(R.id.signup_month_input)).perform(typeText("2"));
        onView(withId(R.id.signup_year_input)).perform(typeText("2001"));
        onView(withId(R.id.signup_submit_btn)).perform(scrollTo()).perform(click());
        onView(withId(R.id.signup_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_month_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_year_input)).check(matches(hasErrorText("Invalid Date")));
    }

    @Test
    public void validateInvalidFebuary() throws Exception {
        onView(withId(R.id.signup_day_input)).perform(typeText("30"));
        onView(withId(R.id.signup_month_input)).perform(typeText("2"));
        onView(withId(R.id.signup_year_input)).perform(typeText("2000"));
        onView(withId(R.id.signup_submit_btn)).perform(scrollTo()).perform(click());
        onView(withId(R.id.signup_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_month_input)).check(matches(hasErrorText("Invalid Date")));
    }

    @Test
    public void validateInvalidThirtyFirst() throws Exception {
        onView(withId(R.id.signup_day_input)).perform(typeText("31"));
        onView(withId(R.id.signup_month_input)).perform(typeText("4"));
        onView(withId(R.id.signup_year_input)).perform(typeText("2000"));
        onView(withId(R.id.signup_submit_btn)).perform(scrollTo()).perform(click());
        onView(withId(R.id.signup_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_month_input)).check(matches(hasErrorText("Invalid Date")));

        onView(withId(R.id.signup_month_input)).perform(clearText());
        onView(withId(R.id.signup_month_input)).perform(typeText("6"));
        onView(withId(R.id.signup_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_month_input)).check(matches(hasErrorText("Invalid Date")));

        onView(withId(R.id.signup_month_input)).perform(clearText());
        onView(withId(R.id.signup_month_input)).perform(typeText("9"));
        onView(withId(R.id.signup_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_month_input)).check(matches(hasErrorText("Invalid Date")));

        onView(withId(R.id.signup_month_input)).perform(clearText());
        onView(withId(R.id.signup_month_input)).perform(typeText("11"));
        onView(withId(R.id.signup_day_input)).check(matches(hasErrorText("Invalid Date")));
        onView(withId(R.id.signup_month_input)).check(matches(hasErrorText("Invalid Date")));
    }

    @Test
    public void validateTerms() throws Exception {
        onView(withId(R.id.signup_submit_btn)).perform(scrollTo()).perform(click());
        onView(withId(R.id.signup_terms_cb)).perform(click());
        onView(withId(R.id.signup_terms_cb)).check(matches(isChecked()));
    }

}