package app.application.account;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import app.application.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test class to validate the behaviour of the {@link LoginActivity}
 */
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testInputs() throws Exception {
        onView(withId(R.id.login_username_input)).perform(typeText("Username")).check(matches(withText("Username")));
        onView(withId(R.id.login_password_input)).perform(typeText("Password")).check(matches(withText("Password")));
    }

    @Test
    public void validateUsername() throws Exception {
        onView(withId(R.id.login_submit_btn)).perform(click());
        onView(withId(R.id.login_username_input)).check(matches(hasErrorText("Enter a Username")));
        onView(withId(R.id.login_username_input)).perform(typeText("UserN")).check(matches(hasErrorText("Username too short")));
        onView(withId(R.id.login_username_input)).perform(clearText());
        onView(withId(R.id.login_username_input)).perform(typeText("UserNameUserNameUserN")).check(matches(hasErrorText("Username too long")));
        onView(withId(R.id.login_username_input)).perform(clearText());
        onView(withId(R.id.login_username_input)).check(matches(hasErrorText("Enter a Username")));
    }

    @Test
    public void validatePassword() throws Exception {
        onView(withId(R.id.login_submit_btn)).perform(click());
        onView(withId(R.id.login_password_input)).check(matches(hasErrorText("Enter a Password")));
        onView(withId(R.id.login_password_input)).perform(typeText("Passw")).check(matches(hasErrorText("Password too short")));
        onView(withId(R.id.login_password_input)).perform(clearText());
        onView(withId(R.id.login_password_input)).perform(typeText("PasswordPasswordPassW")).check(matches(hasErrorText("Password too long")));
        onView(withId(R.id.login_password_input)).perform(clearText());
        onView(withId(R.id.login_password_input)).check(matches(hasErrorText("Enter a Password")));
    }
}