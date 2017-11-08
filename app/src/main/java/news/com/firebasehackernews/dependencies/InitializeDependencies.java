package news.com.firebasehackernews.dependencies;

import news.com.firebasehackernews.database.NewsPersistor;
import news.com.firebasehackernews.database.ProviderWrapper;
import news.com.firebasehackernews.listeners.DependencyFactory;

/**
 * Intiliaze Dependencies required.Triggered from Application Class
 */

public class InitializeDependencies {

  private static InitializeDependencies INSTANCE;
  private final ProviderWrapper provider;
  private final NewsPersistor persister;

  private InitializeDependencies(ProviderWrapper provider,NewsPersistor persister) {
    this.provider = provider;
    this.persister = persister;
  }

  public static void newInstance(DependencyFactory factory) {
    NewsPersistor newsPersister = factory.createNewsPersistor();
    ProviderWrapper provider = factory.createProviderWrapper(newsPersister);
    INSTANCE = new InitializeDependencies(provider, newsPersister);
  }

  private static InitializeDependencies instance() {
    if (INSTANCE == null) {
      throw new CustomError("Dependency Instance not created");
    }
    return INSTANCE;
  }

  public static ProviderWrapper getProviderWrapper() {
    return instance().provider;
  }


  public static NewsPersistor getNewsPersistor() {
    return instance().persister;
  }




}
