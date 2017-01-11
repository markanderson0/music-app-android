package app.application.artist.topvideos;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.artist.ArtistActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Test class to validate the behaviour of the {@link TopVideosFragment}
 */
@RunWith(AndroidJUnit4.class)
public class TopVideosFragmentTest {
    @Rule
    public final IntentsTestRule<ArtistActivity> main =
            new IntentsTestRule<>(ArtistActivity.class, true, false);

    @Before
    public void setUp() {
        main.launchActivity(createIntentWithName("artist1", "artist1image.jpg"));
        onView(ViewMatchers.withText("Top Videos")).perform(scrollTo()).perform(click());
    }

    @Test
    public void topVideos_DisplayCorrectTopVideos_DisplayResults() {
        //Upload User
        onData(anything())
                .inAdapterView(withId(R.id.gridView))
                .atPosition(0)
                .onChildView(withId(R.id.video_upload_user)).check(matches(withText("Uploaded By testUser1")));

        //Audio Rating
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.video_rating)).check(matches(withText(containsString("1"))));

        //Video Rating
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.video_rating)).check(matches(withText(containsString("1"))));

        //Video Time
        onData(anything())
                .inAdapterView(withId(R.id.gridView))
                .atPosition(0)
                .onChildView(withId(R.id.video_time)).check(matches(withText("20:00")));

        //Video Views
        onData(anything())
                .inAdapterView(withId(R.id.gridView))
                .atPosition(0)
                .onChildView(withId(R.id.video_views)).check(matches(withText(containsString("1"))));

        //Video Songs
        onData(anything())
                .inAdapterView(withId(R.id.gridView))
                .atPosition(0)
                .onChildView(withId(R.id.video_songs)).check(matches(withText("song1")));
    }

    private static Intent createIntentWithName(String name, String image) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, ArtistActivity.class);
        intent.putExtra("artist", name);
        intent.putExtra("image", image);
        return intent;
    }
}
