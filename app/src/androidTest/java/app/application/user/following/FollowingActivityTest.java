package app.application.user.following;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Test class to validate the behaviour of the {@link FollowingActivity}
 */
@RunWith(AndroidJUnit4.class)
public class FollowingActivityTest {

    @Rule
    public final IntentsTestRule<FollowingActivity> main =
            new IntentsTestRule<>(FollowingActivity.class, true, true);

    @Test
    public void following_DisplayCorrectFollows() {
        onData(anything())
                .inAdapterView(withId(R.id.gridView))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText("artist3")));

        onData(anything())
                .inAdapterView(withId(R.id.gridView))
                .atPosition(1)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText("artist4")));
    }

    @Test
    public void following_ClickFollowingTile_OpenArtistActivityWithExtras(){
        onData(anything())
                .inAdapterView(withId(R.id.gridView))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).perform(click());
        intended(hasComponent(ArtistActivity.class.getName()));
        intended(hasExtra("artist", "artist3"));
    }
}
