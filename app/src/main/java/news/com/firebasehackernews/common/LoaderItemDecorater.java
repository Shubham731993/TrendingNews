package news.com.firebasehackernews.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Item Decorator
 */

public class LoaderItemDecorater extends RecyclerView.ItemDecoration  {
  public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
  public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
  private static final int[] ATTRS = {android.R.attr.listDivider};
  private final Drawable mDivider;
  private int mOrientation;

  /**
   *
   * @param context
   * @param orientation Orientation of recycler view
   */

  public LoaderItemDecorater(final Context context, final int orientation) {
    final TypedArray a = context.obtainStyledAttributes(ATTRS);
    mDivider = a.getDrawable(0);
    a.recycle();
    setOrientation(orientation);
  }


  /**
   * Draw on canvas
   * @param c
   * @param parent
   */

  @Override
  public void onDraw(final Canvas c, final RecyclerView parent) {
    if (mOrientation == VERTICAL_LIST) {
      drawVertical(c, parent);
    }
  }

  /**
   * Set orientation
   * @param orientation
   */

  public void setOrientation(final int orientation) {
    if ((orientation != HORIZONTAL_LIST) && (orientation != VERTICAL_LIST)) {
      throw new IllegalArgumentException("invalid orientation");
    }
    mOrientation = orientation;
  }

  /**
   * Draw a line
   * @param c
   * @param parent
   */


  public void drawVertical(final Canvas c, final RecyclerView parent) {
    final int left = parent.getPaddingLeft();
    final int right = parent.getWidth() - parent.getPaddingRight();
    final int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);
      final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      final int top = child.getBottom() + params.bottomMargin;
      final int bottom = top + mDivider.getIntrinsicHeight();
      mDivider.setBounds(left, top, right, bottom);
      int childPosition = parent.getChildAdapterPosition(child);
      if (childPosition == childCount - 1)
        continue;
      mDivider.draw(c);
    }
  }

/**
 * Get Item offsets
 * @param outRect
 * @param itemPosition
 * @param parent
 */

  @Override
  public void getItemOffsets(final Rect outRect, final int itemPosition, final RecyclerView parent) {
    if (mOrientation == VERTICAL_LIST) {
      outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
    } else {
      outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
    }
  }
}