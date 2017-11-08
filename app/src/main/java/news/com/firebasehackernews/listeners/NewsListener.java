package news.com.firebasehackernews.listeners;

import news.com.firebasehackernews.model.NewsModel;

/**
 * Interface Used for capturing clicks on recycler view items
 */

public interface NewsListener {

  void onViewItemClicked(NewsModel newsModel);

}
