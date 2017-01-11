package app.application.browse;

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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Test class to validate the behaviour of the {@link BrowseGenreActivity}
 */
@RunWith(AndroidJUnit4.class)
public class BrowseGenreActivityTest {
    @Rule
    public final IntentsTestRule<BrowseGenreActivity> main =
            new IntentsTestRule<>(BrowseGenreActivity.class, true, false);

    @Before
    public void setUp() {
        main.launchActivity(createIntentWithName("Genre"));
    }

    @Test
    public void browseGenre_DisplayGenreArtists() {
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText("artist1")));
    }

    @Test
    public void browseGenre_ClickOnArtistTile_OpenArtistActivityWithExtras() {
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).perform(click());

        intended(hasComponent(ArtistActivity.class.getName()));
        intended(hasExtra("artist", "artist1"));
    }

    private static Intent createIntentWithName(String genre) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, BrowseGenreActivity.class);
        intent.putExtra("genre", genre);
        return intent;
    }
}
