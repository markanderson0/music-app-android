package app.application.artist.merch;

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
import app.application.merch.merchproduct.MerchProductActivity;

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
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Test class to validate the behaviour of the {@link ArtistMerchFragment}
 */
@RunWith(AndroidJUnit4.class)
public class ArtistMerchFragmentTest {

    private static final String MERCH_NAME = "merchItem";
    private static final String MERCH_PRICE = "$10.00";

    @Rule
    public final IntentsTestRule<ArtistActivity> main =
            new IntentsTestRule<>(ArtistActivity.class, true, false);

    @Before
    public void setUp() {
        main.launchActivity(createIntentWithName("artist1", "artist1image.jpg"));
        onView(allOf(withText("Merch"), isDisplayed())).perform(scrollTo()).perform(click());
    }

    @Test
    public void artistMerch_DisplayCorrectMerch_DisplayResults() {
        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.merch_grid_item_name)).check(matches(withText(MERCH_NAME)));

        onData(anything())
                .inAdapterView(allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0)
                .onChildView(withId(R.id.merch_grid_item_price)).check(matches(withText(MERCH_PRICE)));
    }

    @Test
    public void artistMerch_ClickMerchItem_OpenMerchProductActivityWithExtras() {
        onView(withId(R.id.merch_grid_item_image)).perform(click());
        intended(hasComponent(MerchProductActivity.class.getName()));
        intended(hasExtra("name", MERCH_NAME));
        intended(hasExtra("price", MERCH_PRICE));
    }

    private static Intent createIntentWithName(String name, String image) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, ArtistActivity.class);
        intent.putExtra("artist", name);
        intent.putExtra("image", image);
        return intent;
    }
}
