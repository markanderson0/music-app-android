package app.application.search;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;
import static org.mockito.Matchers.anyString;

/**
 * Test class to validate the behaviour of the {@link SearchActivity}
 */
@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {

    @Rule
    public final IntentsTestRule<SearchActivity> main =
            new IntentsTestRule<>(SearchActivity.class, true, false);

    @Before
    public void setUp() {
        main.launchActivity(createIntentWithName(anyString()));
    }

    @Test
    public void search_DisplayCorrectSearchResults_DisplayResults() {
        onView(ViewMatchers.withId(R.id.grid_view)).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.grid_view))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText("artist1")));

        onData(anything())
                .inAdapterView(withId(R.id.grid_view))
                .atPosition(1)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText("artist2")));
    }

    @Test
    public void search_ClickArtistGridItem_OpenArtistActivityWithExtras() {
        onView(withText("artist1")).perform(click());
        intended(hasComponent(ArtistActivity.class.getName()));
        intended(hasExtra("artist", "artist1"));
    }

    private static Intent createIntentWithName(String name) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, SearchActivity.class);
        intent.putExtra("query", name);
        return intent;
    }
}
