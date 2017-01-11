package app.application.artist.videos;

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
import app.application.videoplayer.VideoPlayerActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Test class to validate the behaviour of the {@link ArtistVideosFragment}
 */
@RunWith(AndroidJUnit4.class)
public class ArtistVideosFragmentTest {
    @Rule
    public final IntentsTestRule<ArtistActivity> main =
            new IntentsTestRule<>(ArtistActivity.class, true, false);

    @Before
    public void setUp() {
        main.launchActivity(createIntentWithName("artist1", "artist1image.jpg"));
        onView(ViewMatchers.withText("Videos")).perform(scrollTo()).perform(click());
    }

    @Test
    public void artistVideos_DisplayCorrectVideos_DisplayResults() {
        //Expand show
        onView(withText("city1, country1")).perform(click());

        //Video Uploader
        onView(allOf(withId(R.id.list_item_artist_name), withText("Uploaded By testUser1"))).check(matches(isDisplayed()));

        //Audio Rating
        onView(allOf(withId(R.id.video_rating), withText(containsString("2")))).check(matches(isDisplayed()));

        //Video Rating
        onView(allOf(withId(R.id.video_rating), withText(containsString("2")))).check(matches(isDisplayed()));

        //Video Views
        onView(allOf(withId(R.id.video_views), withText(containsString("2")))).check(matches(isDisplayed()));

        //Video Time
        onView(allOf(withId(R.id.video_time), withText("21:00"))).check(matches(isDisplayed()));

        //Video Songs
        onView(allOf(withId(R.id.video_songs), withText("song2"))).check(matches(isDisplayed()));
    }

    @Test
    public void artistVideos_ClickVideoGridItem_OpenVideoPlayerWithExtras() {
        //Expand show
        onView(withText("city1, country1")).perform(click());

        onView(allOf(withId(R.id.video_songs), withText("song2"))).perform(click());
        intended(hasComponent(VideoPlayerActivity.class.getName()));
        intended(hasExtra("videoId", "id"));
        intended(hasExtra("video", "file"));
        intended(hasExtra("title", "song2"));
        intended(hasExtra("views", "2"));
        intended(hasExtra("audioRating", "2"));
        intended(hasExtra("videoRating", "2"));
    }

    private static Intent createIntentWithName(String name, String image) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, ArtistActivity.class);
        intent.putExtra("artist", name);
        intent.putExtra("image", image);
        return intent;
    }
}
