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
 * Test class to validate the behaviour of the {@link ChangePasswordFragment}
 */
@RunWith(AndroidJUnit4.class)
public class ChangePasswordFragmentTest {
    @Rule
    public ActivityTestRule<SettingsActivity> signupActivityTestRule =
            new ActivityTestRule<>(SettingsActivity.class);

    @Before
    public void init(){
        onView(ViewMatchers.withText("Change Password")).perform(click());
    }

    @Test
    public void noErrorsWithValidPassword_showErrorsWhenPasswordsAreDeleted() throws Exception {
        onView(withId(R.id.password_old_input)).perform(typeText("Password"));
        onView(withId(R.id.password_new_input)).perform(typeText("Password123"));
        onView(withId(R.id.password_confirm_input)).perform(typeText("Password123"));

        onView(withId(R.id.password_submit_btn)).perform(click());

        onView(withId(R.id.password_old_input)).perform(clearText());
        onView(withId(R.id.password_old_input)).check(matches(hasErrorText("Enter a Password")));
        onView(withId(R.id.password_new_input)).perform(clearText());
        onView(withId(R.id.password_new_input)).check(matches(hasErrorText("Enter a Password")));
        onView(withId(R.id.password_confirm_input)).perform(clearText());
//        onView(withId(R.id.password_confirm_input)).check(matches(hasErrorText("Enter a Password")));
    }

    @Test
    public void showErrorsWhenNewandConfirmPaswordsDontMatch_RemoveErrorsWhenTheyDo() throws Exception {
        onView(withId(R.id.password_old_input)).perform(typeText("Password"));
        onView(withId(R.id.password_new_input)).perform(typeText("Password123"));
        onView(withId(R.id.password_confirm_input)).perform(typeText("Password12"));

        onView(withId(R.id.password_submit_btn)).perform(click());

        onView(withId(R.id.password_confirm_input)).check(matches(hasErrorText("Passwords dont match")));

        onView(withId(R.id.password_confirm_input)).perform(clearText());
        onView(withId(R.id.password_confirm_input)).perform(typeText("Password123"));
    }

    @Test
    public void showErrorsWhenPasswordsAreTooShort() throws Exception {
        onView(withId(R.id.password_old_input)).perform(typeText("Passw"));
        onView(withId(R.id.password_new_input)).perform(typeText("Passw"));
        onView(withId(R.id.password_confirm_input)).perform(typeText("Passw"));

        onView(withId(R.id.password_submit_btn)).perform(click());

        onView(withId(R.id.password_old_input)).check(matches(hasErrorText("Password too short")));
        onView(withId(R.id.password_new_input)).check(matches(hasErrorText("Password too short")));
    }

    @Test
    public void showErrorsWhenPasswordsAreTooLong() throws Exception {
        onView(withId(R.id.password_old_input)).perform(typeText("PasswordPasswordPassW"));
        onView(withId(R.id.password_new_input)).perform(typeText("PasswordPasswordPassW"));
        onView(withId(R.id.password_confirm_input)).perform(typeText("PasswordPasswordPassW"));

        onView(withId(R.id.password_submit_btn)).perform(click());

        onView(withId(R.id.password_old_input)).check(matches(hasErrorText("Password too long")));
        onView(withId(R.id.password_new_input)).check(matches(hasErrorText("Password too long")));
    }
}
