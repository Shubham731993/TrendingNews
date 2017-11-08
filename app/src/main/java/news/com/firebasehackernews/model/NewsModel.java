package news.com.firebasehackernews.model;

import android.database.Cursor;
import android.text.TextUtils;

import news.com.firebasehackernews.common.Constants;
import news.com.firebasehackernews.database.NewsContract;

/**
 * Model used for mapping cursor to data
 */

public class NewsModel {

  private  Long internalId;
  private String by;
  private  Long id;
  private  Long timeAgo;
  private  int score;
  private  String title;
  private  String url;
  private  int comments;
  private  String type;
  private  Long timestamp;
  private  int rank;
  private  int read;
  private int bookmark;
  private int voted;

  /**
   * Constructor
   * @param internalId
   * @param by
   * @param id
   * @param type
   * @param timeAgo
   * @param score
   * @param title
   * @param url
   * @param comments
   * @param timestamp
   * @param rank
   * @param bookmark
   * @param read
   * @param voted
   */


  public NewsModel(Long internalId, String by, Long id, String type, Long timeAgo, int score, String title, String url, int comments, Long timestamp, int rank, int bookmark, int read, int voted) {
    this.internalId = internalId;
    this.by = by;
    this.id = id;
    this.timeAgo = timeAgo;
    this.type = type;
    this.score = score;
    this.title = title;
    this.url = url;
    this.comments = comments;
    this.timestamp = timestamp;
    this.rank = rank;
    this.bookmark = bookmark;
    this.read = read;
    this.voted = voted;
  }

  /**
   * Mapping cursor to data
   * @param cursor
   * @return
   */

  public static NewsModel from(Cursor cursor) {
    Long internalId = cursor.getLong(NewsContract.NewsStory.COLUMN_ID);
    Long id = cursor.getLong(NewsContract.NewsStory.COLUMN_ITEM_ID);
    String type = cursor.getString(NewsContract.NewsStory.COLUMN_TYPE);
    String by = cursor.getString(NewsContract.NewsStory.COLUMN_BY);
    int comments = cursor.getInt(NewsContract.NewsStory.COLUMN_COMMENTS);
    String url = cursor.getString(NewsContract.NewsStory.COLUMN_URL);
    int score = cursor.getInt(NewsContract.NewsStory.COLUMN_SCORE);
    String title = cursor.getString(NewsContract.NewsStory.COLUMN_TITLE);
    Long time = cursor.getLong(NewsContract.NewsStory.COLUMN_TIME_AGO);
    Long timestamp = cursor.getLong(NewsContract.NewsStory.COLUMN_TIMESTAMP);
    int rank = cursor.getInt(NewsContract.NewsStory.COLUMN_RANK);
    int bookmark = cursor.getInt(NewsContract.NewsStory.COLUMN_BOOKMARK);
    int read = cursor.getInt(NewsContract.NewsStory.COLUMN_READ);
    int voted = cursor.getInt(NewsContract.NewsStory.COLUMN_VOTED);

    return new NewsModel(internalId, by, id, type, time, score, title, url, comments, timestamp, rank, bookmark, read, voted);
  }

  public String getSubmitter() {
    return by;
  }

  private boolean isSubmitterEmpty() {
    return TextUtils.isEmpty(by);
  }

  public boolean isStoryAJob() {
    return isSubmitterEmpty();
  }

  public Long getId() {
    return id;
  }

  public Long getTimeAgo() {
    return timeAgo;
  }

  public int getScore() {
    return score;
  }

  public String getTitle() {
    return title;
  }

  public String getUrl() {
    return url;
  }

  public String getType() {
    return type;
  }

  public int getComments() {
    return comments;
  }

  public Long getTimestamp() {
    return timestamp;
  }


  public int getRank() {
    return rank;
  }

  public boolean isRead() {
    return read == Constants.TRUE;
  }

  /**
   * Used for querying cursor loader
   */

  public enum TYPE {
    story
  }
}
