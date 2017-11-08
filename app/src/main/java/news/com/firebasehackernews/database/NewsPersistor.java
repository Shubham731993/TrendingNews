package news.com.firebasehackernews.database;

import android.content.ContentResolver;
import android.content.ContentValues;

import java.util.Calendar;
import java.util.List;

import news.com.firebasehackernews.common.Constants;
import news.com.firebasehackernews.model.NewsModel;

/**
 * Wrapper for calls made on Content Provider
 */

public class NewsPersistor {

  private final ContentResolver contentResolver;
  private static final int MILLIS_IN_TWO_DAYS =  1000 * 60 * 60 * 24 * 2;

  public NewsPersistor(ContentResolver contentResolver) {
    this.contentResolver = contentResolver;
  }

  /**
   * Content Provider insert query called
   * @param topStories
   * @return
   */
  public int persistStories(List<ContentValues> topStories) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(calendar.getTimeInMillis() - MILLIS_IN_TWO_DAYS);
    String timestampTwoDaysAgo = String.valueOf(calendar.getTimeInMillis());
    contentResolver.delete(NewsContract.NewsStory.CONTENT_STORY_URI,
        NewsContract.NewsStory.TIMESTAMP + " <= ?",
        new String[]{timestampTwoDaysAgo});

    ContentValues[] cvArray = new ContentValues[topStories.size()];
    topStories.toArray(cvArray);

    return contentResolver.bulkInsert(NewsContract.NewsStory.CONTENT_STORY_URI, cvArray);
  }

  /**
   * Handling Read Functionality
   * @param newsModel
   */

  public void markStoryAsRead(NewsModel newsModel) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(NewsContract.NewsStory.READ, Constants.TRUE);
      contentResolver.update(NewsContract.NewsStory.CONTENT_STORY_URI,
          contentValues,
          NewsContract.NewsStory.ITEM_ID + " = ?",
          new String[]{String.valueOf(newsModel.getId())});

  }
}
