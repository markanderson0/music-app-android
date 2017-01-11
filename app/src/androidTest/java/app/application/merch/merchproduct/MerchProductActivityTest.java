package app.application.merch.merchproduct;

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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test class to validate the behaviour of the {@link MerchProductActivity}
 */
@RunWith(AndroidJUnit4.class)
public class MerchProductActivityTest {
    @Rule
    public final IntentsTestRule<MerchProductActivity> main =
            new IntentsTestRule<>(MerchProductActivity.class, true, false);

    @Before
    public void setUp() {
        main.launchActivity(createIntentWithName("merchItem", "merchItem.jpg", "$10.00"));
    }

    @Test
    public void merchProduct_DisplayCorrectMerchProduct_DisplayResults() {
        onView(withId(R.id.merch_product_name)).check(matches(withText("merchItem")));
        onView(withId(R.id.merch_product_price)).check(matches(withText("Price: $10.00")));
    }

    private static Intent createIntentWithName(String name, String image, String price) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, MerchProductActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("image", image);
        intent.putExtra("price", price);
        return intent;
    }
}
