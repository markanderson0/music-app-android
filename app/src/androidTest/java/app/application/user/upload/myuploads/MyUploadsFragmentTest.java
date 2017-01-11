package app.application.user.upload.myuploads;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.user.upload.UploadActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Test class to validate the behaviour of the {@link MyUploadsFragment}
 */
@RunWith(AndroidJUnit4.class)
public class MyUploadsFragmentTest {
    @Rule
    public final IntentsTestRule<UploadActivity> main =
            new IntentsTestRule<>(UploadActivity.class);

    @Before
    public void init() {
        onView(ViewMatchers.withText("My Uploads")).perform(click());
    }

    @Test
    public void myUploads_DisplayCorrectVideoDetails() {
        //Expand artist1
        onView(withText("artist1")).perform(click());

        //Video Location
        onView(withId(R.id.list_item_artist_name)).check(matches(withText("date | venue | location")));

        //Audio Rating
        onView(withId(R.id.video_rating)).check(matches(withText(containsString("5"))));

        //Video Rating
        onView(withId(R.id.video_rating)).check(matches(withText(containsString("7"))));

        //Video Time
        onView(withId(R.id.video_time)).check(matches(withText("20:00")));

        //Video Songs
        onView(withId(R.id.video_songs)).check(matches(withText("song")));
    }

    @Test
    public void myUploads_ClickOnVideoTile_DisplayDialogWithDeleteButton(){
        //Expand artist1
        onView(withText("artist1")).perform(click());
        onView(allOf(withId(R.id.video_songs), withText("song"))).perform(click());
        onView((withId(R.id.my_uploads_delete_btn))).check(matches(isDisplayed()));
    }
}
