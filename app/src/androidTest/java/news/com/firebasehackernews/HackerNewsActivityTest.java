package news.com.firebasehackernews;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import news.com.firebasehackernews.activities.HackerNewsActivity;
import news.com.firebasehackernews.activities.WebViewActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HackerNewsActivityTest {

  @Rule
  public IntentsTestRule<HackerNewsActivity> mActivityTestRule = new IntentsTestRule<>(HackerNewsActivity.class);

  @Test
  public void hackerNewsActivityTest() {

    onView(withId(R.id.news_view)).check(matches(isDisplayed()));

    onView(withId(R.id.feed_refresh)).check(matches(isDisplayed()));

    onView(allOf(isDisplayed(),withId(R.id.news_view)))
        .perform(RecyclerViewActions.scrollToPosition(10));


    onView(withId(R.id.news_view))
        .perform(RecyclerViewActions.actionOnItemAtPosition(10,
            click()));

    intended(hasComponent(WebViewActivity.class.getName()));

  }
}
