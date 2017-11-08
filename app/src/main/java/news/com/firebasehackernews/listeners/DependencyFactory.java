package news.com.firebasehackernews.listeners;

import news.com.firebasehackernews.database.NewsPersistor;
import news.com.firebasehackernews.database.ProviderWrapper;

/**
 *  Interface used for creating dependencies
 */

public interface DependencyFactory {

  NewsPersistor createNewsPersistor();

  ProviderWrapper createProviderWrapper(NewsPersistor newsPersistor);

}
