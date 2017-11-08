package news.com.firebasehackernews.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Content Provider over our Database
 */

public class NewsProvider extends ContentProvider {

  private NewsDbHelper mOpenHelper;

  /**
   * Triggered as provider is mentioned in manifest file
   * @return
   */
  @Override
  public boolean onCreate() {
    mOpenHelper = new NewsDbHelper(getContext());
    return true;
  }

  /**
   * Query Content Provider
   * @param uri Uri of Content Provider
   * @param projection choosing which columns
   * @param selection choosing which rows
   * @param selectionArgs criteria for selection
   * @param sortOrder Sorting Order (Will be passed as score)
   * @return
   */

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    Cursor retCursor = mOpenHelper.getReadableDatabase().query(
        NewsContract.TABLE_STORIES_NAME,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    );

    retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return retCursor;
  }

  /**
   * Get Provider Type
   * @param uri
   * @return
   */

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return NewsContract.CONTENT_STORY_TYPE;
  }

  /**
   * Insert Query
   * @param uri Provider Url
   * @param values Values to be inserted
   * @return
   */

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    Uri returnUri;
    Cursor exists = db.query(NewsContract.TABLE_STORIES_NAME,
        new String[]{NewsContract.NewsStory.ITEM_ID},
        NewsContract.NewsStory.ITEM_ID + " = ?",
        new String[]{values.getAsString(NewsContract.NewsStory.ITEM_ID)},
        null,
        null,
        null);
    if (exists.moveToLast()) {
      long _id = db.update(NewsContract.TABLE_STORIES_NAME, values, NewsContract.NewsStory.ITEM_ID + " = ?",
          new String[]{values.getAsString(NewsContract.NewsStory.ITEM_ID)});
      if (_id > 0) {
        returnUri = NewsContract.NewsStory.buildStoryUriWith(_id);
      } else {
        throw new android.database.SQLException("Failed to insert row into " + uri);
      }
    } else {
      long _id = db.insert(NewsContract.TABLE_STORIES_NAME, null, values);
      if (_id > 0) {
        returnUri = NewsContract.NewsStory.buildStoryUriWith(_id);
      } else {
        throw new android.database.SQLException("Failed to insert row into " + uri);
      }
    }
    exists.close();
    getContext().getContentResolver().notifyChange(uri, null);
    return returnUri;
  }

  /**
   * Delete Query
   * @param uri
   * @param selection
   * @param selectionArgs
   * @return
   */

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    int rowsDeleted = db.delete(
        NewsContract.TABLE_STORIES_NAME, selection, selectionArgs);
    if (selection == null || rowsDeleted != 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return rowsDeleted;
  }

  /**
   * Update Query
   * @param uri
   * @param values
   * @param selection
   * @param selectionArgs
   * @return
   */

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    int rowsUpdated = db.update(NewsContract.TABLE_STORIES_NAME, values, selection,
        selectionArgs);
    if (rowsUpdated != 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return rowsUpdated;
  }

  /**
   * Bulk Insert
   * @param uri
   * @param values
   * @return
   */


  @Override
  public int bulkInsert(Uri uri, ContentValues[] values) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    int returnCount;


    final String updateRows = "UPDATE " + NewsContract.TABLE_STORIES_NAME +
        " SET " + NewsContract.NewsStory.RANK +
        " = 1000" +
        " WHERE " + NewsContract.NewsStory.TYPE +
        " = '" + values[0].get(NewsContract.NewsStory.TYPE) + "'";
    db.execSQL(updateRows);

    db.beginTransaction();
    returnCount = 0;
    try {
      for (ContentValues value : values) {

        Cursor exists = db.query(NewsContract.TABLE_STORIES_NAME,
            new String[]{NewsContract.NewsStory.ITEM_ID},
            NewsContract.NewsStory.ITEM_ID + " = ?",
            new String[]{value.getAsString(NewsContract.NewsStory.ITEM_ID)},
            null,
            null,
            null);

        if (exists.moveToLast()) {
          long _id = db.update(NewsContract.TABLE_STORIES_NAME, value, NewsContract.NewsStory.ITEM_ID + " = ?",
              new String[]{value.getAsString(NewsContract.NewsStory.ITEM_ID)});

          if (_id != -1) {
            returnCount++;
          }
        } else {
          long _id = db.insert(NewsContract.TABLE_STORIES_NAME, null, value);
          if (_id != -1) {
            returnCount++;
          }
        }
        exists.close();
      }
      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return returnCount;
  }
}
