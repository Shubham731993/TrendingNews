package news.com.firebasehackernews.database;

import android.content.ContentValues;
import android.util.Log;
import android.util.Pair;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by shubham.srivastava on 06/11/17.
 */

public class NewsStoryCall {

  /**
   * Firebase Call for fetching data
   * @return
   */
  public Observable<List<ContentValues>> getStories() {

    return Observable.create(new Observable.OnSubscribe<DataSnapshot>() {
      @Override
      public void call(final Subscriber<? super DataSnapshot> subscriber) {
        Firebase topStories = getStoryFirebase();
        topStories.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot != null) {
              subscriber.onNext(dataSnapshot);
            } else {
              //Something went wrong
            }
            subscriber.onCompleted();
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
            Log.i("FirebaseCancelled",String.valueOf(firebaseError.getCode()));
          }
        });
      }
    }).flatMap(new Func1<DataSnapshot, Observable<Pair<Integer, Long>>>() {
      @Override
      public Observable<Pair<Integer, Long>> call(final DataSnapshot dataSnapshot) {
        return Observable.create(new Observable.OnSubscribe<Pair<Integer, Long>>() {
          @Override
          public void call(Subscriber<? super Pair<Integer, Long>> subscriber) {
            for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
              Long id = (Long) dataSnapshot.child(String.valueOf(i)).getValue();
              Integer rank = Integer.valueOf(dataSnapshot.child(String.valueOf(i)).getKey());
              Pair<Integer, Long> storyRoot = new Pair<>(rank, id);
              subscriber.onNext(storyRoot);
            }
            subscriber.onCompleted();
          }
        });
      }
    }).flatMap(new Func1<Pair<Integer, Long>, Observable<ContentValues>>() {
      @Override
      public Observable<ContentValues> call(final Pair<Integer, Long> storyRoot) {
        return Observable.create(new Observable.OnSubscribe<ContentValues>() {
          @Override
          public void call(final Subscriber<? super ContentValues> subscriber) {
            final Firebase story = new Firebase("https://hacker-news.firebaseio.com/v0/item/" + storyRoot.second);
            story.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newItem = (Map<String, Object>) dataSnapshot.getValue();
                if (newItem != null) {
                  ContentValues story = mapStory(newItem, storyRoot.first);
                  if (story != null) {
                    subscriber.onNext(story);
                  } else {
                    subscriber.onNext(new ContentValues());
                    Log.i("onDataChange empty", String.valueOf(storyRoot.second));
                  }
                }
                subscriber.onCompleted();
              }

              @Override
              public void onCancelled(FirebaseError firebaseError) {
                Log.i("FirebaseCancelled",String.valueOf(firebaseError.getCode()));
                subscriber.onCompleted();
              }
            });
          }
        });
      }
    })
        .toList();
  }

  /**
   * Map data to content values. Required to update Content Provider
   * @param map
   * @param rank
   * @return
   */

  private ContentValues mapStory(Map<String, Object> map, Integer rank) {

    ContentValues storyValues = new ContentValues();

    try {
      String by = (String) map.get("by");
      Long id = (Long) map.get("id");
      String type = (String) map.get("type");
      Long time = (Long) map.get("time");
      Long score = (Long) map.get("score");
      String title = (String) map.get("title");
      String url = (String) map.get("url");
      Long descendants = Long.valueOf(0);
      if (map.get("descendants") != null) {
        descendants = (Long) map.get("descendants");
      }

      storyValues.put(NewsContract.NewsStory.ITEM_ID, id);
      storyValues.put(NewsContract.NewsStory.BY, by);
      storyValues.put(NewsContract.NewsStory.TYPE, type);
      storyValues.put(NewsContract.NewsStory.TIME_AGO, time * 1000);
      storyValues.put(NewsContract.NewsStory.SCORE, score);
      storyValues.put(NewsContract.NewsStory.TITLE, title);
      storyValues.put(NewsContract.NewsStory.COMMENTS, descendants);
      storyValues.put(NewsContract.NewsStory.URL, url);
      storyValues.put(NewsContract.NewsStory.RANK, rank);
      storyValues.put(NewsContract.NewsStory.TIMESTAMP, System.currentTimeMillis());
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return storyValues;
  }

  /**
   * Firebase url to fetch top stories
   * @return
   */

  private Firebase getStoryFirebase() {
    return new Firebase("https://hacker-news.firebaseio.com/v0/topstories");
  }
}
