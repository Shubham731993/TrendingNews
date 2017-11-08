package news.com.firebasehackernews;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;

import news.com.firebasehackernews.dependencies.DefaultDependencies;
import news.com.firebasehackernews.dependencies.InitializeDependencies;

/**
 * Created by shubham.srivastava on 06/11/17.
 */

public class HackerNewsApplication extends Application {


  private static Context context;

  public static Context getAppContext() {
    return context;
  }

  /**
   * Initializing firebase and default dependenices
   */

  @Override
  public void onCreate() {
    super.onCreate();
    context = this;
    Firebase.setAndroidContext(this);
    InitializeDependencies.newInstance(new DefaultDependencies(this));
  }
}