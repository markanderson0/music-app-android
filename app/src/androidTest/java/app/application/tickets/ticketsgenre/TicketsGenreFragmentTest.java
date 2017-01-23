package app.application.tickets.ticketsgenre;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.tickets.TicketsActivity;
import app.application.tickets.ticketssearch.TicketsSearchActivity;

import static android.support.test.espresso.Espresso.onData;
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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Test class to validate the behaviour of the {@link TicketsGenreFragment}
 */
@RunWith(AndroidJUnit4.class)
public class TicketsGenreFragmentTest {

    private static final String ARTIST1 = "artist1";
    private static final String ARTIST3 = "artist3";
    private static final String ARTIST_FESTIVAL = "artist festival";
    private static final String ALT_INDIE_TAB = "Alt / Indie";
    private static final String FESTIVAL_TAB = "Festivals";

    @Rule
    public final IntentsTestRule<TicketsActivity> main =
            new IntentsTestRule<>(TicketsActivity.class);

    @Test
    public void ticketsGenre_DisplayCorrectGenreTickets_DisplayResults() {
        onView(withText(ALT_INDIE_TAB)).perform(click());

        //Artist in grid
        onData(anything())
                .inAdapterView(allOf(withId(R.id.grid_view), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText(ARTIST1)));

        //Artist in table
        onView(allOf(withId(R.id.artist_name), isDisplayed())).check(matches(withText(ARTIST3)));
    }

    @Test
    public void ticketsGenre_DisplayCorrectFestivalTickets_DisplayResults() {
        onView(withText(FESTIVAL_TAB)).perform(scrollTo()).perform(click());
        onView(withText(ARTIST_FESTIVAL)).check(matches(isDisplayed()));
    }

    @Test
    public void ticketsGenre_ClickArtistInGrid_OpenTicketsSearchActivityWithExtras(){
        onView(withText(ALT_INDIE_TAB)).perform(click());
        onData(anything())
                .inAdapterView(allOf(withId(R.id.grid_view), isDisplayed()))
                .atPosition(0).perform(click());
        intended(hasComponent(TicketsSearchActivity.class.getName()));
        intended(hasExtra("search", ARTIST1));
    }

    @Test
    public void ticketsGenre_ClickFindTicketsButton_OpenTicketsSearchActivityWithExtras(){
        onView(withText(ALT_INDIE_TAB)).perform(click());
        onView(allOf(withId(R.id.find_tickets_btn), isDisplayed())).perform(click());
        intended(hasComponent(TicketsSearchActivity.class.getName()));
        intended(hasExtra("search", ARTIST3));
    }
}
