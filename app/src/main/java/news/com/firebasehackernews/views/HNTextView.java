package news.com.firebasehackernews.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import news.com.firebasehackernews.R;

/**
 * Custom TextView for application
 */

public class HNTextView extends android.support.v7.widget.AppCompatTextView {

  /**
   * @param context
   * @param attrs
   * @param defStyle
   */
  public HNTextView(final Context context, final AttributeSet attrs, final int defStyle) {
    super(context, attrs, defStyle);
    if (!isInEditMode()) {
      init();
    }
  }

  /**
   * @param context
   * @param attrs
   */
  public HNTextView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    if (!isInEditMode()) {
      init();
    }
  }

  /**
   * @param context
   */
  public HNTextView(final Context context) {
    super(context);
    if (!isInEditMode()) {
      init();
    }

  }

  private void init() {
    if (getTag() != null) {
      if (getTag().equals(getResources().getString(R.string.regular))) {
        final Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "CrimsonText.ttf");
        setTypeface(tf);
      }
    } else {
      final Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans.ttf");
      setTypeface(tf);
    }
  }



}

