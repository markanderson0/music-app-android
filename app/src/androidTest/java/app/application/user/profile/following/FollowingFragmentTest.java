package app.application.user.profile.following;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.artist.ArtistActivity;
import app.application.user.profile.ProfileActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Test class to validate the behaviour of the {@link FollowingFragment}
 */
@RunWith(AndroidJUnit4.class)
public class FollowingFragmentTest {
    @Rule
    public final IntentsTestRule<ProfileActivity> main =
            new IntentsTestRule<>(ProfileActivity.class, true, true);

    @Before
    public void init() {
        onView(ViewMatchers.withText("Following")).perform(click());
    }

    @Test
    public void displayCorrectSearchResults() {
        onData(anything())
                .inAdapterView(withId(R.id.grid_view))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText("artist3")));

        onData(anything())
                .inAdapterView(withId(R.id.grid_view))
                .atPosition(1)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText("artist4")));
    }

    @Test
    public void following_ClickFollowingTile_OpenArtistActivityWithExtras(){
        onData(anything())
                .inAdapterView(withId(R.id.grid_view))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).perform(click());
        intended(hasComponent(ArtistActivity.class.getName()));
        intended(hasExtra("artist", "artist3"));
    }
}
