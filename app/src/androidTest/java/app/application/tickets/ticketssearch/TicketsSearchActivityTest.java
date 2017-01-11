package app.application.tickets.ticketssearch;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;

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
import static org.hamcrest.core.IsNot.not;

/**
 * Test class to validate the behaviour of the {@link TicketsSearchActivity}
 */
@RunWith(AndroidJUnit4.class)
public class TicketsSearchActivityTest {
    @Rule
    public final IntentsTestRule<TicketsSearchActivity> main =
            new IntentsTestRule<>(TicketsSearchActivity.class, true, false);

    @Test
    public void ticketsSearch_DisplayCorrectTickets_DisplaysResults() {
        main.launchActivity(createIntentWithName("artist1"));

        //Artist Name
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.artist_name)).check(matches(withText("artist1")));

        //Venue
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.ticket_venue)).check(matches(withText("venue1")));

        //Date
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.ticket_date)).check(matches(withText("2017-01-01")));

        //Location
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.ticket_location)).check(matches(withText("city1,s1 US")));

        onView(withId(R.id.no_tickets)).check(matches(not(isDisplayed())));
    }

    @Test
    public void ticketsSearch_ClickMapIcon_OpensTicketsMapActivity(){
        main.launchActivity(createIntentWithName("artist1"));
        onView(withId(R.id.map_icon)).perform(click());
        intended(hasComponent(TicketsMapActivity.class.getName()));
        intended(hasExtra("search", "artist1 Tickets"));
    }

    @Test
    public void ticketsSearch_InvalidTicketSearch_DisplaysError(){
        main.launchActivity(createIntentWithName(null));
        onView(withId(R.id.no_tickets)).check(matches(isDisplayed()));
        onView(withId(R.id.gridView)).check(matches(not(isDisplayed())));
    }

    private static Intent createIntentWithName(String name) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, TicketsSearchActivity.class);
        intent.putExtra("search", name);
        return intent;
    }
}
