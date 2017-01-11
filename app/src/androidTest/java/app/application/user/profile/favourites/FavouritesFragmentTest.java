package app.application.user.profile.favourites;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.user.profile.ProfileActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

/**
 * Test class to validate the behaviour of the {@link FavouritesFragment}
 */
@RunWith(AndroidJUnit4.class)
public class FavouritesFragmentTest {
    @Rule
    public final IntentsTestRule<ProfileActivity> main =
            new IntentsTestRule<>(ProfileActivity.class);

    @Before
    public void init() {
        onView(ViewMatchers.withText("Favourites")).perform(click());
    }

    @Test
    public void favourites_DisplayCorrectFavourites() {
        onView(ViewMatchers.withText("artist5")).perform(click());

        //Upload User
        onView(withId(R.id.video_upload_user)).check(matches(withText("Uploaded By testUser1")));

        //Audio Rating
        onView(withId(R.id.video_rating)).check(matches(withText(containsString("5"))));

        //Video Rating
        onView(withId(R.id.video_rating)).check(matches(withText(containsString("7"))));

        //Video Time
        onView(withId(R.id.video_time)).check(matches(withText("20:00")));

        //Video Views
        onView(withId(R.id.video_views)).check(matches(withText(containsString("100"))));

        //Video Songs
        onView(withId(R.id.video_songs)).check(matches(withText("song1")));
    }
}
