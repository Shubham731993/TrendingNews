package news.com.firebasehackernews.dependencies;

import android.content.Context;

import news.com.firebasehackernews.database.NewsPersistor;
import news.com.firebasehackernews.database.ProviderWrapper;
import news.com.firebasehackernews.listeners.DependencyFactory;

/**
 * Dependenices Required
 */

public class DefaultDependencies implements DependencyFactory {

  private final Context context;

  public DefaultDependencies(Context context) {
    this.context=context;
  }

  @Override
  public NewsPersistor createNewsPersistor() {
    return new NewsPersistor(context.getContentResolver());
  }

  @Override
  public ProviderWrapper createProviderWrapper(NewsPersistor newsPersistor) {
    return new ProviderWrapper(newsPersistor);
  }
}
