package news.com.firebasehackernews.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract for db
 */

public class NewsContract {

  public static final String CONTENT_AUTHORITY = "news.com.firebasehackernews";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
  public static final String PATH_ITEM = "news";
  public static final String CONTENT_STORY_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;
  public static final String TABLE_STORIES_NAME = "stories";

  /**
   * News Story Item Columns for Table
   */

  public static final class NewsStory implements BaseColumns {
    public static final Uri CONTENT_STORY_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEM).build();
    public static final String ITEM_ID = "item_id";
    public static final String TYPE = "type";
    public static final String BY = "by";
    public static final String COMMENTS = "comments";
    public static final String URL = "url";
    public static final String SCORE = "score";
    public static final String TITLE = "title";
    public static final String TIME_AGO = "time_ago";
    public static final String RANK = "rank";
    public static final String TIMESTAMP = "timestamp";
    public static final String BOOKMARK = "bookmark";
    public static final String READ = "read";
    public static final String VOTED = "voted";

    public static final int COLUMN_ID = 0;
    public static final int COLUMN_ITEM_ID = 1;
    public static final int COLUMN_TYPE = 2;
    public static final int COLUMN_BY = 3;
    public static final int COLUMN_COMMENTS = 4;
    public static final int COLUMN_URL = 5;
    public static final int COLUMN_SCORE = 6;
    public static final int COLUMN_TITLE = 7;
    public static final int COLUMN_TIME_AGO = 8;
    public static final int COLUMN_RANK = 9;
    public static final int COLUMN_TIMESTAMP = 10;
    public static final int COLUMN_BOOKMARK = 11;
    public static final int COLUMN_READ = 12;
    public static final int COLUMN_VOTED = 13;

    public static final String[] STORY_COLUMNS = {
        NewsContract.NewsStory._ID,
        NewsContract.NewsStory.ITEM_ID,
        NewsContract.NewsStory.TYPE,
        NewsContract.NewsStory.BY,
        NewsContract.NewsStory.COMMENTS,
        NewsContract.NewsStory.URL,
        NewsContract.NewsStory.SCORE,
        NewsContract.NewsStory.TITLE,
        NewsContract.NewsStory.TIME_AGO,
        NewsContract.NewsStory.RANK,
        NewsContract.NewsStory.TIMESTAMP,
        NewsContract.NewsStory.BOOKMARK,
        NewsContract.NewsStory.READ,
        NewsContract.NewsStory.VOTED,
    };


    public static Uri buildStoryUriWith(long id) {
      return ContentUris.withAppendedId(CONTENT_STORY_URI, id);
    }

    public static Uri buildStoriesUri() {
      return CONTENT_STORY_URI.buildUpon().build();
    }
  }
}
