package news.com.firebasehackernews.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import news.com.firebasehackernews.HackerNewsApplication;

/**
 * Created by shubham.srivastava on 07/11/17.
 */

public class Utility {

  /**
   * Check whether internet connected or not
   * @return boolean
   */
  public static boolean isInternetConnected() {
    final ConnectivityManager connectivity = (ConnectivityManager) HackerNewsApplication
        .getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    boolean internetConnected = false;
    if (connectivity != null) {
      final NetworkInfo[] info = connectivity.getAllNetworkInfo();
      if (info != null) {
        for (final NetworkInfo anInfo : info) {
          if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
            internetConnected = true;
          }
        }
      }
    }
    return internetConnected;
  }
}
