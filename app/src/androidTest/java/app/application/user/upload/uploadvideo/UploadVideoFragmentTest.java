package app.application.user.upload.uploadvideo;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.user.upload.UploadActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Test class to validate the behaviour of the {@link UploadVideoFragment}
 */
@RunWith(AndroidJUnit4.class)
public class UploadVideoFragmentTest {
    @Rule
    public final IntentsTestRule<UploadActivity> main =
            new IntentsTestRule<>(UploadActivity.class);

    @Test
    public void uploadVideo_EnterValidValuesIntoAllFields_ShowNoErrorsWhenSubmitted() {
        onView(withId(R.id.upload_artist_input)).perform(typeText("artist1")).check(matches(withText("artist1")));
        onView(withId(R.id.upload_year_input)).perform(typeText("2017")).check(matches(withText("2017")));

        onView(withId(R.id.upload_events)).perform(click());
        onView(allOf(withId(R.id.row_item), withText("01-01-2016, ven1"))).perform(click());
        onView(withId(R.id.upload_events)).check(matches(withText("01-01-2016, ven1")));

        onView(withId(R.id.upload_selected_songs)).perform(click());
        onView(allOf(withId(R.id.row_item), withText("song1"))).perform(click());
        onView(withId(R.id.upload_selected_songs)).check(matches(withText("song1")));

        onView(withId(R.id.upload_btn)).perform(click());

        onView(withId(R.id.upload_artist_input)).check(matches(withText("artist1")));
        onView(withId(R.id.upload_year_input)).check(matches(withText("2017")));
        onView(withId(R.id.upload_events_spinner)).check(matches(withText("01-01-2016, ven1")));
        onView(withId(R.id.upload_selected_songs)).check(matches(withText("song1")));
    }

    @Test
    public void uploadVideo_FillOutFieldsThenClearArtistName_EventsAndSongsViewsAreCleared() {
        onView(withId(R.id.upload_artist_input)).perform(typeText("artist1")).check(matches(withText("artist1")));
        onView(withId(R.id.upload_year_input)).perform(typeText("2017")).check(matches(withText("2017")));

        onView(withId(R.id.upload_events)).perform(click());
        onView(allOf(withId(R.id.row_item), withText("01-01-2016, ven1"))).perform(click());
        onView(withId(R.id.upload_events)).check(matches(withText("01-01-2016, ven1")));

        onView(withId(R.id.upload_selected_songs)).perform(click());
        onView(allOf(withId(R.id.row_item), withText("song1"))).perform(click());
        onView(withId(R.id.upload_selected_songs)).check(matches(withText("song1")));

        onView(withId(R.id.upload_artist_input)).perform(clearText());
        onView(withId(R.id.upload_events)).check(matches(withText("")));
        onView(withId(R.id.upload_selected_songs)).check(matches(withText("")));
    }

    @Test
    public void uploadVideo_FillOutFieldsThenClearTheYear_EventsAndSongsViewsAreCleared() {
        onView(withId(R.id.upload_artist_input)).perform(typeText("artist1")).check(matches(withText("artist1")));
        onView(withId(R.id.upload_year_input)).perform(typeText("2017")).check(matches(withText("2017")));

        onView(withId(R.id.upload_events)).perform(click());
        onView(allOf(withId(R.id.row_item), withText("01-01-2016, ven1"))).perform(click());
        onView(withId(R.id.upload_events)).check(matches(withText("01-01-2016, ven1")));

        onView(withId(R.id.upload_selected_songs)).perform(click());
        onView(allOf(withId(R.id.row_item), withText("song1"))).perform(click());
        onView(withId(R.id.upload_selected_songs)).check(matches(withText("song1")));

        onView(withId(R.id.upload_year_input)).perform(clearText());
        onView(withId(R.id.upload_events)).check(matches(withText("")));
        onView(withId(R.id.upload_selected_songs)).check(matches(withText("")));
    }

    @Test
    public void uploadVideo_FillOutFieldsThenChangeTheEvent_SongsViewsIsCleared() {
        onView(withId(R.id.upload_artist_input)).perform(typeText("artist1")).check(matches(withText("artist1")));
        onView(withId(R.id.upload_year_input)).perform(typeText("2017")).check(matches(withText("2017")));

        onView(withId(R.id.upload_events)).perform(click());
        onView(allOf(withId(R.id.row_item), withText("01-01-2016, ven1"))).perform(click());
        onView(withId(R.id.upload_events)).check(matches(withText("01-01-2016, ven1")));

        onView(withId(R.id.upload_selected_songs)).perform(click());
        onView(allOf(withId(R.id.row_item), withText("song1"))).perform(click());
        onView(withId(R.id.upload_selected_songs)).check(matches(withText("song1")));

        onView(withId(R.id.upload_events)).perform(click());
        onView(allOf(withId(R.id.row_item), withText("02-01-2016, ven2"))).perform(click());

        onView(withId(R.id.upload_selected_songs)).check(matches(withText("")));
    }

    @Test
    public void uploadVideo_SubmitBlankFields_DisplayErrors() {
        onView(withId(R.id.upload_btn)).perform(click());
        onView(withId(R.id.upload_artist_input)).check(matches(hasErrorText("Enter an Artists Name")));
        onView(withId(R.id.upload_year_input)).check(matches(hasErrorText("Enter a Year")));
    }
}
