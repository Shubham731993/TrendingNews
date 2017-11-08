package news.com.firebasehackernews;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import news.com.firebasehackernews.activities.WebViewActivity;
import news.com.firebasehackernews.common.Constants;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by shubham.srivastava on 08/11/17.
 */
public class WebViewActivityTest {
  @Rule
  public ActivityTestRule<WebViewActivity> mActivityTestRule = new ActivityTestRule<WebViewActivity>(WebViewActivity.class){
    @Override
    protected Intent getActivityIntent() {
      Intent intent =new Intent();
      intent.putExtra(Constants.Intent.TITLE,"Google Search Page");
      intent.putExtra(Constants.Intent.URL, "www.google.com");
      return intent;
    }
  };

  private WebViewActivity mActivity;
  @Before
  public void setUp() throws Exception {
    mActivity =mActivityTestRule.getActivity();
  }

  @Test
  public void should_have_views(){
    View webview =mActivity.findViewById(R.id.webViewPage);
    assertNotNull(webview);
  }


  @After
  public void tearDown() throws Exception {
    mActivity=null;
  }

}