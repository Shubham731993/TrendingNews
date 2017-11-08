package news.com.firebasehackernews.database;

import android.content.ContentValues;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Wrapper over Provider
 */

public class ProviderWrapper {

  private final NewsStoryCall storyCall;
  private final NewsPersistor newsPersistor;

  /**
   * Constructor
   * @param newsPersistor
   */

  public ProviderWrapper(NewsPersistor newsPersistor) {
    this.newsPersistor = newsPersistor;
    this.storyCall = new NewsStoryCall();
  }

  /**
   * Api call made and call for data to be persisted made
   * @return
   */

  public Observable<Integer> getStories() {
    return storyCall.getStories()
        .flatMap(new Func1<List<ContentValues>, Observable<Integer>>() {
          @Override
          public Observable<Integer> call(final List<ContentValues> stories) {
            return Observable.create(new Observable.OnSubscribe<Integer>() {
              @Override
              public void call(Subscriber<? super Integer> subscriber) {
                newsPersistor.persistStories(stories);
                subscriber.onNext(stories.size());
                subscriber.onCompleted();
              }
            });
          }
        });
  }
}
