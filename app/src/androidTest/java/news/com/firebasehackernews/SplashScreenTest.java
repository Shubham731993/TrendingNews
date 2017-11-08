package news.com.firebasehackernews;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by shubham.srivastava on 08/11/17.
 */
public class SplashScreenTest {

  @Rule
  public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);

  private SplashScreen mActivity;
  @Before
  public void setUp() throws Exception {
    mActivity =mActivityTestRule.getActivity();
  }

  @Test
  public void should_have_views(){
    View icon =mActivity.findViewById(R.id.trending_icon);
    View title = mActivity.findViewById(R.id.title);

    assertNotNull(icon);
    assertNotNull(title);
  }


  @After
  public void tearDown() throws Exception {
    mActivity=null;
  }

}