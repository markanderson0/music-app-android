package app.application.merch.merchcategory;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.application.R;
import app.application.merch.MerchActivity;
import app.application.merch.merchproduct.MerchProductActivity;

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
 * Test class to validate the behaviour of the {@link MerchCategoryFragment}
 */
@RunWith(AndroidJUnit4.class)
public class MerchCategoryFragmentTest {

    private static final String MERCH_NAME = "merchItem";
    private static final String MERCH_PRICE = "$10.00";

    @Rule
    public final IntentsTestRule<MerchActivity> main =
            new IntentsTestRule<>(MerchActivity.class);

    @Before
    public void setUp() {
        onView(ViewMatchers.withText("Apparel")).perform(click());
    }

    @Test
    public void merchCategory_DisplayCorrectMerch_DisplayResults() {
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
    public void merchCategory_ClickMerchItem_OpenMerchProductActivityWithExtras() {
        onData(Matchers.anything())
                .inAdapterView(Matchers.allOf(withId(R.id.gridView), isDisplayed()))
                .atPosition(0).perform(click());
        intended(hasComponent(MerchProductActivity.class.getName()));
        intended(hasExtra("name", MERCH_NAME));
        intended(hasExtra("price", MERCH_PRICE));

    }
}
