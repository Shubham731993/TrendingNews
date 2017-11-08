package news.com.firebasehackernews.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import news.com.firebasehackernews.R;
import news.com.firebasehackernews.adapters.NewsAdapter;
import news.com.firebasehackernews.common.Constants;
import news.com.firebasehackernews.common.LoaderItemDecorater;
import news.com.firebasehackernews.common.Utility;
import news.com.firebasehackernews.database.NewsContract;
import news.com.firebasehackernews.database.NewsPersistor;
import news.com.firebasehackernews.database.ProviderWrapper;
import news.com.firebasehackernews.dependencies.InitializeDependencies;
import news.com.firebasehackernews.listeners.NewsListener;
import news.com.firebasehackernews.model.NewsModel;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 *
 * Main Activity
 *
 */

public class HackerNewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, NewsListener, LoaderManager.LoaderCallbacks {

  protected Subscription subscription;
  private SwipeRefreshLayout refreshLayout;
  private RecyclerView newList;
  private NewsAdapter newsAdapter;
  private RelativeLayout progressLayout;
  private CoordinatorLayout coordinatorLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    refreshLayout = findViewById(R.id.feed_refresh);
    progressLayout = findViewById(R.id.progress_layout);
    coordinatorLayout = findViewById(R.id.coordinatorLayout);
    newList = findViewById(R.id.news_view);
    showProgress();
    setUpToolbar();
    setUpRefreshLayout();
    setUpRecyclerViewAdapter();
    if (Utility.isInternetConnected()) {
      fetchStories();
    } else {
      hideProgress();
      showSnackBar("Please connect to internet to fetch latest news");
    }
    getSupportLoaderManager().initLoader(Constants.NEWS_LOADER, null, this);
  }

  /**
   * Snack Bar
   * @param message To be displayed on Snack Bar
   */

  private void showSnackBar(String message) {
    Snackbar snackbar = Snackbar
        .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);

    snackbar.show();
  }

  /**
   * Show Progress till cursor loader fetches data
   */

  private void showProgress() {
    newList.setVisibility(View.GONE);
    progressLayout.setVisibility(View.VISIBLE);
  }

  /**
   * Hide Progress after data has been fetched
   */

  private void hideProgress() {
    newList.setVisibility(View.VISIBLE);
    progressLayout.setVisibility(View.GONE);
  }

  /**
   * Recycler View Setup with ItemDecorator and LayoutManager
   */

  private void setUpRecyclerViewAdapter() {
    newList.setHasFixedSize(true);
    LinearLayoutManager newsLayoutManger = new LinearLayoutManager(this);
    newList.setLayoutManager(newsLayoutManger);
    final RecyclerView.ItemDecoration itemDecoration =
        new LoaderItemDecorater(this, LinearLayoutManager.VERTICAL);
    newList.addItemDecoration(itemDecoration);
    newsAdapter = new NewsAdapter(null, this, this);
    newList.setAdapter(newsAdapter);
  }

  /**
   *  Swipe Refresh Layout SetUp
   */

  private void setUpRefreshLayout() {
    refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
    refreshLayout.setOnRefreshListener(this);
  }

  /**
   * Toolbar Setup
   */

  private void setUpToolbar() {
    final Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setIcon(R.drawable.ic_trending_up_white_36dp);
    getSupportActionBar().setTitle("Trending News");
  }

  /**
   * Called on Pull to Refresh
   */

  @Override
  public void onRefresh() {
    if (Utility.isInternetConnected()) {
      fetchStories();
    } else {
      showSnackBar("Please connect to internet to fetch latest news");
      stopRefreshing();
    }
  }

  /**
   * Fetching Data from Firebase
   */

  private void fetchStories() {
    if (Utility.isInternetConnected()) {
      ProviderWrapper provider = InitializeDependencies.getProviderWrapper();
      subscription = provider
          .getStories()
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
              if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
              }
            }

            @Override
            public void onError(Throwable e) {
              e.printStackTrace();
            }

            @Override
            public void onNext(Integer total) {

            }
          });
    } else {
      stopRefreshing();
    }
  }

  /**
   * Stop Refreshing
   */

  private void stopRefreshing() {
    refreshLayout.setRefreshing(false);
  }

  /**
   * Called when an item of recycler view clicked
   * @param newsModel Model data associated with the item clicked
   */


  @Override
  public void onViewItemClicked(NewsModel newsModel) {
    if(Utility.isInternetConnected()) {
      NewsPersistor persister = InitializeDependencies.getNewsPersistor();
      persister.markStoryAsRead(newsModel);
      Intent intent = new Intent(this, WebViewActivity.class);
      intent.putExtra(Constants.Intent.TITLE, newsModel.getTitle());
      intent.putExtra(Constants.Intent.URL, newsModel.getUrl());
      startActivity(intent);
    }else {
      showSnackBar("Please connect to internet to view this post");
    }
  }

  /**
   * Instantiate and return a new Loader for the given ID.
   * @param id
   * @param args
   * @return
   */

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    Uri storyNewsUri = NewsContract.NewsStory.buildStoriesUri();
    return new CursorLoader(
        this,
        storyNewsUri,
        NewsContract.NewsStory.STORY_COLUMNS,
        NewsContract.NewsStory.TYPE + " = ?",
        new String[]{NewsModel.TYPE.story.name()},
        getOrder());
  }

  /**
   * Called when a previously created loader has finished its load.
   * @param loader
   * @param data
   */

  @Override
  public void onLoadFinished(Loader loader, Object data) {
    newsAdapter.swapCursor((Cursor) data);
    if (((Cursor) data).getCount() > 0) {
      hideProgress();
    }
    stopRefreshing();
  }

  /**
   * Get order for query . Used to sort elements according to the score
   * @return
   */

  protected String getOrder() {
    return NewsContract.NewsStory.SCORE + " DESC";
  }

  @Override
  public void onLoaderReset(Loader loader) {

  }


}
