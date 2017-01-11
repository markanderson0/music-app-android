package app.application.tickets.toptickets;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.tickets.TicketsActivity;
import app.application.tickets.ticketssearch.TicketsSearchActivity;

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
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Test class to validate the behaviour of the {@link TopTicketsFragment}
 */
@RunWith(AndroidJUnit4.class)
public class TopTicketsFragmentTest {
    @Rule
    public final IntentsTestRule<TicketsActivity> main =
            new IntentsTestRule<>(TicketsActivity.class);

    @Before
    public void setUp() {
        onView(ViewMatchers.withText("Top")).perform(click());
    }

    @Test
    public void topTickets_DisplayCorrectTickets_DisplayResults() {
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.grid_item_title)).check(matches(withText("artist1")));
    }

    @Test
    public void topTickets_ClickArtistInGrid_OpenTicketsSearchActivityWithExtras(){
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0).perform(click());
        intended(hasComponent(TicketsSearchActivity.class.getName()));
        intended(hasExtra("search", "artist1"));
    }
}
