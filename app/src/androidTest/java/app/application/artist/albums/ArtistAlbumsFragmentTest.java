package app.application.artist.albums;

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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Test class to validate the behaviour of the {@link ArtistAlbumsFragment}
 */
@RunWith(AndroidJUnit4.class)
public class ArtistAlbumsFragmentTest {
    @Rule
    public final IntentsTestRule<ArtistActivity> main =
            new IntentsTestRule<>(ArtistActivity.class, true, false);

    @Before
    public void setUp() {
        main.launchActivity(createIntentWithName("albumName", "albumImage.jpg"));
        onView(ViewMatchers.withText("Albums")).perform(scrollTo()).perform(click());
    }

    @Test
    public void artistAlbums_DisplayCorrectAlbums_DisplayResults() {
        onView(allOf(withId(R.id.album_name), withText("albumName"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.album_release_date), withText("2017"))).check(matches(isDisplayed()));
        onView(withText("1. song")).check(matches(isDisplayed()));
    }

    private static Intent createIntentWithName(String name, String image) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, ArtistActivity.class);
        intent.putExtra("artist", name);
        intent.putExtra("image", image);
        return intent;
    }
}
