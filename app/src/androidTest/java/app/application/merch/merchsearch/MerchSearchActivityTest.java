package app.application.merch.merchsearch;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.merch.merchproduct.MerchProductActivity;

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
import static org.mockito.Matchers.anyString;

/**
 * Test class to validate the behaviour of the {@link MerchSearchActivity}
 */
@RunWith(AndroidJUnit4.class)
public class MerchSearchActivityTest {

    private static final String MERCH_NAME = "merchItem";
    private static final String MERCH_PRICE = "$10.00";

    @Rule
    public final IntentsTestRule<MerchSearchActivity> main =
            new IntentsTestRule<>(MerchSearchActivity.class, true, false);

    @Before
    public void setUp() {
        main.launchActivity(createIntentWithName(anyString()));
    }

    @Test
    public void merchSearch_DisplayCorrectMerch_DisplayResults() {
        onData(anything())
                .inAdapterView(allOf(withId(R.id.grid_view), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.merch_grid_item_name)).check(matches(withText(MERCH_NAME)));

        onData(anything())
                .inAdapterView(allOf(withId(R.id.grid_view), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.merch_grid_item_price)).check(matches(withText(MERCH_PRICE)));
    }

    @Test
    public void merchSearch_ClickMerchItem_OpenMerchProductActivityWithExtras() {
        onData(Matchers.anything())
                .inAdapterView(Matchers.allOf(withId(R.id.grid_view), isDisplayed()))
                .atPosition(0).perform(click());
        intended(hasComponent(MerchProductActivity.class.getName()));
        intended(hasExtra("name", MERCH_NAME));
        intended(hasExtra("price", MERCH_PRICE));
    }

    private static Intent createIntentWithName(String search) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MerchSearchActivity.class);
        intent.putExtra("search", search);
        return intent;
    }
}
