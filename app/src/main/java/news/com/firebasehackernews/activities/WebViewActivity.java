package news.com.firebasehackernews.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import news.com.firebasehackernews.R;
import news.com.firebasehackernews.common.Constants;

/**
 * For displaying the article
 */

public class WebViewActivity extends AppCompatActivity {


  private ProgressDialog progress;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.web_view_holder);
    setUpToolbar();
    setUpWebView();
  }

  /**
   * Setting up the webview and loading the url passed
   */

  private void setUpWebView() {
    String url = "";
    WebView webView = findViewById(R.id.webViewPage);
    showProgress();
    if (getIntent().hasExtra(Constants.Intent.URL)) {
      url = getIntent().getStringExtra(Constants.Intent.URL);
    } else {
      finish();
    }
    webView.clearHistory();
    webView.setVerticalScrollBarEnabled(true);
    webView.setHorizontalScrollBarEnabled(true);
    webView.requestFocusFromTouch();
    webView.getSettings().setUseWideViewPort(true);
    webView.setInitialScale(1);
    webView.getSettings().setBuiltInZoomControls(true);
    webView.getSettings().supportZoom();
    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    final String finalUrl = url;
    webView.setWebViewClient(new WebViewClient(){
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return true;
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        hideProgress();
        super.onPageFinished(view, url);
      }
    });
    webView.loadUrl(url);

  }

  /**
   * Hiding Progress when url has been loaded
   */


  private void hideProgress() {
    if ((progress != null) && progress.isShowing() && !isFinishing()) {
      progress.dismiss();
      progress = null;
    }
  }

  /**
   * Showing Progress when url is being loaded
   */

  private void showProgress() {
    if ((progress == null) && !isFinishing()) {
      progress = new ProgressDialog(this);
      final View v = getLayoutInflater().inflate(R.layout.progress, null);
      progress.show();
      progress.setCancelable(true);
      progress.setContentView(v);
    }
  }

  /**
   * Setting up the toolbar with title passed from previous screen
   */

  private void setUpToolbar() {
    String title = "";
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (getIntent().hasExtra(Constants.Intent.TITLE))
      title = getIntent().getStringExtra(Constants.Intent.TITLE);
    getSupportActionBar().setTitle(title);
  }
}

