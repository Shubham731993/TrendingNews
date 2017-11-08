package news.com.firebasehackernews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Sqlite Database
 */

public class NewsDbHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "news.db";
  private static final int DATABASE_VERSION = 1;

  /**
   * Intializing Db
   * @param context
   */
  public NewsDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * Creating Table
   * @param db
   */
  @Override
  public void onCreate(SQLiteDatabase db) {

    final String SQL_CREATE_STORIES_TABLE = "CREATE TABLE " + NewsContract.TABLE_STORIES_NAME + " (" +
        NewsContract.NewsStory._ID + " INTEGER PRIMARY KEY," +
        NewsContract.NewsStory.ITEM_ID + " INTEGER UNIQUE NOT NULL," +
        NewsContract.NewsStory.TYPE + " TEXT," +
        NewsContract.NewsStory.BY + " TEXT," +
        NewsContract.NewsStory.COMMENTS + " INTEGER," +
        NewsContract.NewsStory.URL + " TEXT," +
        NewsContract.NewsStory.SCORE + " INTEGER," +
        NewsContract.NewsStory.TITLE + " TEXT," +
        NewsContract.NewsStory.TIME_AGO + " INTEGER," +
        NewsContract.NewsStory.RANK + " INTEGER," +
        NewsContract.NewsStory.TIMESTAMP + " INTEGER, " +
        NewsContract.NewsStory.BOOKMARK + " INTEGER DEFAULT 0, " +
        NewsContract.NewsStory.READ + " INTEGER DEFAULT 0, " +
        NewsContract.NewsStory.VOTED + " INTEGER DEFAULT 0" +
        " );";

    db.execSQL(SQL_CREATE_STORIES_TABLE);
  }

  /**
   * Triggered on changing version
   * @param db
   * @param oldVersion
   * @param newVersion
   */

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + NewsContract.TABLE_STORIES_NAME);
    onCreate(db);
  }
}
