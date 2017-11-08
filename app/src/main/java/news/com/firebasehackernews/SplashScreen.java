package news.com.firebasehackernews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import news.com.firebasehackernews.activities.HackerNewsActivity;

/**
 * Launch Screen
 */

public class SplashScreen extends AppCompatActivity {

  /**
   * Launching HackerNewsActivity in 2.5 seconds
   * @param savedInstanceState
   */

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_screen);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent intent = new Intent(SplashScreen.this, HackerNewsActivity.class);
        startActivity(intent);
        finish();
      }
    }, 2500);
  }
}
