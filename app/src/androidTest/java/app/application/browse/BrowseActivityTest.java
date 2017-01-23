package app.application.browse;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;

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
 * Test class to validate the behaviour of the {@link BrowseActivity}
 */
@RunWith(AndroidJUnit4.class)
public class BrowseActivityTest {
    @Rule
    public final IntentsTestRule<BrowseActivity> main =
            new IntentsTestRule<>(BrowseActivity.class);

    @Test
    public void browse_DisplayBrowseNavigationGenres() {
        onData(anything())
                .inAdapterView(allOf(withId(R.id.grid_view), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText("Genre")));
    }

    @Test
    public void browse_ClickOnGenreTile_OpenBrowseGenreActivityWithExtras() {
        onData(anything())
                .inAdapterView(allOf(withId(R.id.grid_view), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).perform(click());

        intended(hasComponent(BrowseGenreActivity.class.getName()));
        intended(hasExtra("genre", "genre"));
    }
}
