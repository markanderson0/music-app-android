package app.application.artist.shows;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.artist.ArtistActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Test class to validate the behaviour of the {@link ArtistShowsFragment}
 */
@RunWith(AndroidJUnit4.class)
public class ArtistShowsFragmentTest {
    @Rule
    public final IntentsTestRule<ArtistActivity> main =
            new IntentsTestRule<>(ArtistActivity.class, true, false);

    @Before
    public void setUp() {
        main.launchActivity(createIntentWithName("artist1", "artist1image.jpg"));
        onView(withText("Shows")).perform(scrollTo()).perform(click());
    }

    @Test
    public void artistShows_DisplayCorrectSetlists_DisplayResults() {
        //First Show Setlist
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), withChild(withId(R.id.artist_shows_cardview)), isDisplayed()))
                .atPosition(0)
                .onChildView(withText("Setlist"))
                .perform(click());

        onView(withText("song1")).check(matches(isDisplayed()));
        onView(withText("song2")).check(matches(isDisplayed()));
        onView(withText("song3")).check(matches(isDisplayed()));

        //Close Setlist Dialog
        pressBack();

        //Second Show Setlist
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), withChild(withId(R.id.artist_shows_cardview)), isDisplayed()))
                .atPosition(1)
                .onChildView(withText("Setlist"))
                .perform(click());

        onView(withText("song4")).check(matches(isDisplayed()));
        onView(withText("song5")).check(matches(isDisplayed()));
    }

    private static Intent createIntentWithName(String name, String image) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, ArtistActivity.class);
        intent.putExtra("artist", name);
        intent.putExtra("image", image);
        return intent;
    }
}
